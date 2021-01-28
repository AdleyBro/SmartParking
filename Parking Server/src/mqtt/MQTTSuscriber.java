package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ConectionDB conector = new ConectionDB();
        Connection connection = null;
        ArrayList<String> topics = new ArrayList<>();
        try {
            connection = conector.obtainConnection();

            // obtener parkings:
            PreparedStatement psParking = ConectionDB.getParking(connection);
            ResultSet rsParking = psParking.executeQuery();
            while (rsParking.next()) {
                String topicParking = "Parking" + rsParking.getInt("IdParking");

                // obtener plazas del parking
                PreparedStatement psPlaza = ConectionDB.getPlazasParking(connection);
                ResultSet rsPlaza = psPlaza.executeQuery();
                while (rsPlaza.next()) {
                    String topicPlaza = topicParking + "/Plaza" + rsPlaza.getInt("IdPlaza");
                    topics.add(topicPlaza);
                }

            }
            // subscribeTopic(broker, topics);
        } catch (Exception ex) {

        } finally {
            conector.closeConnection(connection);
        }
    }

    // se crea un cliente y este se suscribe a todos los topicos
    public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics){

    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {

        String[] topics = topic.split("/"); 
        
        if(topic.contains("Sensor")){
            Logic.setPlazaOcupada(){

            } 
        } 

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
