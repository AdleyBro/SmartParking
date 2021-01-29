package logic;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;

/**
 *	ES: Clase encargada de inicializar el sistema y de lanzar el hilo de previsión meteorológica
 *	EN: Class in charge of initializing the thread of weather forecast
 */
@WebListener
public class ProjectInitializer implements ServletContextListener 
{
	public static MQTTBroker myBroker;
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	/**
	 *	ES: Metodo empleado para detectar la inicializacion del servidor	<br>
	 * 	EN: Method used to detect server initialization
	 * 	@param sce <br>
	 * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
	 * 	EN: Context event created during server launch
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		MQTTBroker broker = new MQTTBroker();
		myBroker = broker;
		MQTTSuscriber subscriber = new MQTTSuscriber();
		subscriber.searchTopicsToSuscribe(broker); // suscribirse a todos los topics.
	}	 
	public static MQTTBroker getActualBroker(){
		return myBroker;
	}
}