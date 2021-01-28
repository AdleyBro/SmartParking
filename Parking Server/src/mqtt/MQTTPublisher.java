package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

// import logic.Log;

public class MQTTPublisher{

    public static void publish(MQTTBroker broker, String topic, String content){
       
        MemoryPersistence persistence = new MemoryPersistence();		
		try {
		    MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
		    MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
		    // Log.logmqtt.info("Conectando al broker r: " + MQTTBroker.getBroker());
		    sampleClient.connect(connOpts);
		    // Log.logmqtt.info("Broker connectado. ");
		    MqttMessage message = new MqttMessage(content.getBytes());
		    message.setQos(MQTTBroker.getQos());
		    sampleClient.publish(topic, message);
		    // Log.logmqtt.info("Message published");
		    sampleClient.disconnect();
		    // Log.logmqtt.info("Disconnected");
		    
		} catch(MqttException me) {
			// Log.logmqtt.error("Error on publishing value: {}" ,  me);
		} catch(Exception e) {
			// Log.logmqtt.error("Error on publishing value: {}" ,  e);
		}	

    }

}

