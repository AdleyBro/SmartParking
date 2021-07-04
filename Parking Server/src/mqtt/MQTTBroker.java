package mqtt;

public class MQTTBroker{

    private static int qos = 2; // Quality of Service
    //private static String broker = "tcp://3.140.225.4:1883" ;
    //private static String clientId = "sensor" ; 
    private static String broker = "tcp://192.168.0.167:1883";//protocolo dirección y puerto para la conexión de mosquitto
    private static String clientId = "ServerClient"; //Esto sirve solo para los logs es solo para identificar los movimentos de mosquitto

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