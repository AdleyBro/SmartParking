package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import logic.*;
//import logic.Logic;



@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;


  
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Log.log.info("-- Creando usuario en la DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{ 
            String nombreU = request.getParameter("nombreU");
            String pass = request.getParameter("pass");
			String email = request.getParameter("email");
			String nombre = request.getParameter("nombre");
			int telefono = Integer.parseInt(request.getParameter("tlf"));
			int Idcliente=0;
			String ok2;
			
			
			Timestamp fechaDeHoy = new Timestamp(System.currentTimeMillis());


		
			
		

			

			
			ok2=Logic.storeNewCliente(nombreU,nombre,email,telefono,pass,fechaDeHoy);
            
			
			
			out.println(ok2);
			
            
                      
           
            
           //out.println("nombre del abonado : "+nombre+" nombre de usuario : "+ nombreU+" telefono: "+telefono+" email: "+email+" pass: "+pass);
           
		} catch (NumberFormatException nfe) 
		{
			out.println("NumberFormatException");
			//Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("IndexOutOfBoundsException");
			//Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("Exception");
			//Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}