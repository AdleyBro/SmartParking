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

@WebServlet("/Hola")
public class Hola extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static MQTTBroker broker = ProjectInitializer.getActualBroker();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter(); 
		try {
            
			
            MQTTPublisher.publish(broker, "/prueba", "on");
            

		} catch (Exception e) {
			out.print(e.toString());
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
