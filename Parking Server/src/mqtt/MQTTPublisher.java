package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MQTTPublisher{

	private static final String USERNAME = "user";
    private static final char[] PASSWORD = "pass".toCharArray();

    public static void publish(MQTTBroker broker, String topic, String content){
        MemoryPersistence persistence = new MemoryPersistence();		
		try {
		    MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
		    MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(USERNAME);
			connOpts.setPassword(PASSWORD);
		    connOpts.setCleanSession(true);	// permite el uso seguro de MemoryPersistence
		    sampleClient.connect(connOpts);
		    MqttMessage message = new MqttMessage(content.getBytes());
		    message.setQos(MQTTBroker.getQos());
		    sampleClient.publish(topic, message);
		    //sampleClient.disconnect();
		   
		} catch(MqttException me) {
			
		} catch(Exception e) {
			
		}	

    }

}

