package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import db.ConectionDB;
import db.Plaza;
import db.Parking;
import logic.*;

public class MQTTSuscriber implements MqttCallback {
    
    public void searchTopicsToSuscribe(MQTTBroker broker) {
        //TODO: sirve para buscar los topics para suscribirse con formato /ParkingIdParking/PlazaIdPlaza
        //TODO: LOS TOPICS NO SE INICIAN SOLOS, PARA CREAR UN TOPIC LO HACES DESDE EL CMD( mosquitto_pub -h localhost -p 1883 -t 'topic que quieres crear' -m "mensaje a mandar "  -d)
        //TODO: Aunq ahoora estemos creando topics desde el cmd, en un futuro querremos crearlos desde el arduino, con: client.pusblish("/topicName", "aqui vienen los valores que retransmiten. ")
        //TODO: aqui solo se busca si existen esos topics  
        ConectionDB conector = new ConectionDB();
        Connection connection = null;
        ArrayList<String> topics = new ArrayList<>();
        try {
            connection = conector.obtainConnection();

            // obtener parkings:
            PreparedStatement psParking = ConectionDB.getParking(connection);
            ResultSet rsParking = psParking.executeQuery();
            while (rsParking.next()) {
                String topicParking = "/Parking" + rsParking.getInt("IdParking");

                // obtener plazas del parking /Parking300/Plaza2
                PreparedStatement psPlaza = ConectionDB.getPlazasParkingMosquitto(connection); 
                psPlaza.setInt(1, rsParking.getInt("IdParking"));
                ResultSet rsPlaza = psPlaza.executeQuery();
                while (rsPlaza.next()) {
                    String topicPlaza = topicParking + "/Plaza" + rsPlaza.getInt("IdPlaza");
                    topics.add(topicPlaza);
                } 

            }
            subscribeTopic(broker, topics);
        } catch (Exception ex) {

        } finally {
            conector.closeConnection(connection);
        }
    }
    

    // se crea un cliente y este se suscribe a todos los topicos
    public void subscribeTopic(MQTTBroker broker, ArrayList<String> topics){
        //TODO: esta función sirve para suscribirte a la lista de topics
        MemoryPersistence persistence = new MemoryPersistence();
        try{
            MqttClient client = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);	// permite el uso seguro de MemoryPersistence
            client.connect(connOpts);
            client.setCallback(this); // Configura al listener para el uso de eventos asincronos.

            for (int i = 0; i < topics.size(); i++){
                client.subscribe(topics.get(i));
            }
        } catch (Exception e){

        }
    }


    // public void subscribimePuto(MQTTBroker broker){
    //     //TODO: aquí hemos hecho una suscripción al tópic /willirex/
    //     MemoryPersistence persistence = new MemoryPersistence();
    //     try{
    //         MqttClient client = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
    //         MqttConnectOptions connOpts = new MqttConnectOptions();
    //         connOpts.setCleanSession(true);	// permite el uso seguro de MemoryPersistence
    //         client.connect(connOpts);
    //         client.setCallback(this); // Configura al listener para el uso de eventos asincronos.

    //         client.subscribe("/Willyrex/");
    //     } catch (Exception e){

    //     }
    // }

    @Override
    public void connectionLost(Throwable cause) {
    }

 
    /**
     * Cuando recibe un mensaje de un sensor de proximidad, lo lee y actualiza el valor dependiendo de si 
     * el mensaje indica si una plaza ha sido desocupada u ocupada, actualizando el valor en la bbdd
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception{

        String strMessage = message.toString();
        int nSensor, nParking;
        String auxNSensor = "" , auxNParking = "";
        String[] topics = topic.split("/");
        
        // obteniendo numero parking
        for (char ch : topics[0].toCharArray() ) {
            if (ch == 0 | ch == 1 | ch == 2 |
                ch == 3 | ch == 4 | ch == 5 |
                ch == 6 | ch == 7 | ch == 8 | ch == 9){
                    auxNParking += ch;
                }
        }

        // obteniendo el numero del sensor/plaza
        for (char ch : topics[2].toCharArray() ) {
            if (ch == 0 | ch == 1 | ch == 2 |
                ch == 3 | ch == 4 | ch == 5 |
                ch == 6 | ch == 7 | ch == 8 | ch == 9){
                    auxNSensor += ch;
                }
        }

        nParking = Integer.parseInt(auxNParking);
        nSensor = Integer.parseInt(auxNSensor);

        if (strMessage.contains("Libre")) {

            Logic.updateEstadoPlaza(true, nParking, nSensor);
            boolean reservable =Logic.getEsReservable(nSensor);
            String fechaE = Logic.getFechaEHPlaza(nSensor);
            Timestamp fechaS = new Timestamp(System.currentTimeMillis());
            Logic.updateHistorialPlaza( fechaE, fechaS,nSensor);
            
        } else if (strMessage.contains("Ocupado")){ 
            Logic.updateEstadoPlaza(false, nParking, nSensor);
            boolean reservable =Logic.getEsReservable(nSensor);
            Timestamp fechaE = new Timestamp(System.currentTimeMillis());
            Logic.storeNewHistorialPlaza(fechaE, nSensor, nParking,reservable);
        }
      
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
