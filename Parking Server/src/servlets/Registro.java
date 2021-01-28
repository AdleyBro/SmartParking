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
			boolean ok,ok2;
			
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			Timestamp fechaDeHoy = new Timestamp(System.currentTimeMillis());


		
			
			Idcliente = Logic.getCountIdCliente(Idcliente);

			

			ok=Logic.storeNewUser(nombreU,pass,ts);
			ok2=Logic.storeNewCliente(Idcliente,nombre,email,telefono,fechaDeHoy,nombreU);
            
			if(ok)
			{
				out.println(" La creación del nuevo usuario ha salido bien");

			}
			else{

				out.println(" El usuario ya esta registrado\n");
			}
			
            
                      
           
            
           //out.println("nombre del abonado : "+nombre+" nombre de usuario : "+ nombreU+" telefono: "+telefono+" email: "+email+" pass: "+pass);
           
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			//Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			//Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
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