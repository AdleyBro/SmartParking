package mqtt;

public class MQTTBroker{

    private static int qos = 2; // Quality of Service
    private static String broker = "tcp://ip:puerto" ;
    private static String clientId = "" ; 


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