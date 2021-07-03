package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.*;
import mqtt.MQTTPublisher;

import mqtt.MQTTBroker;

@WebServlet("/AbrirPuerta")
public class AbrirPuerta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static MQTTBroker broker = ProjectInitializer.getActualBroker();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter(); 
		try {
            
            String nombreU = request.getParameter("nombreU");
            int idparking = Integer.parseInt(request.getParameter("idparking"));
            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
			if(Logic.getEstadoCliente(nombreU)){
 				Logic.storeNewHistorialEntrada(fechaActual, nombreU,idparking);
				Logic.updateEstadoCliente(false, nombreU);
			}
			else{
				Logic.storeNewHistorialSalida(fechaActual, nombreU,idparking);
				Logic.updateEstadoCliente(true, nombreU);
			}
           
			
            MQTTPublisher.publish(broker, "/servo", "on");
            wait(10000);
            MQTTPublisher.publish(broker, "/servo", "off");

		} catch (Exception e) {
			out.println("-1");
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
