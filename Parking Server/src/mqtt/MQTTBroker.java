package mqtt;

public class MQTTBroker{

    private static int qos = 2; // Quality of Service
    private static String broker = "tcp://3.140.225.4:1883" ;
    private static String clientId = "sensor" ; 


    public static int getQos(){
        return qos;
    }

    public static String getBroker() {
        return broker;
    }

    public static String getClientId(){
        return clientId; 
    }

}