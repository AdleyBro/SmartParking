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
import logic.Logic;

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

            }//Parking300/Plaza2 -> activar
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
    public void subscribimePuto(MQTTBroker broker){
        //TODO: aquí hemos hecho una suscripción al tópic /willirex/
        MemoryPersistence persistence = new MemoryPersistence();
        try{
            MqttClient client = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);	// permite el uso seguro de MemoryPersistence
            client.connect(connOpts);
            client.setCallback(this); // Configura al listener para el uso de eventos asincronos.

            client.subscribe("/Willyrex/");
        } catch (Exception e){

        }
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        // metodo que salta cuando un mensaje es enviado.
       
        //TODO: aquí se ejecuta el código que se quiera hacer cuando llegue un mensaje (mirar código de la profesora)
        //      en nuestro caso, querremos ocupar o liberar una plaza de parking. 
        boolean ok = Logic.storeBasura(topic, topic);

      

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
