package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;


import logic.*;
//import logic.Logic;



@WebServlet("/InicioSesion")
public class InicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Iniciando sesión--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try 
		{ 
            String nombreU = request.getParameter("nombreU");
            String pass = request.getParameter("pass");
            String respuesta;
			if(Logic.validNombreDeUsuario(nombreU)){
				if(Logic.validPassUsuario(nombreU,pass)){
					respuesta = "Logg-in correcto";
					
				}else{
					respuesta = "Las credenciales no coinciden";
				}
			}
			else{
				respuesta = "Usuario no registrado";
			}
            
                      
           
            out.println(respuesta);
           //out.println("Has iniciado sesión como :\n nombre_usuario : "+nombreU+ " pass: "+pass);
           
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
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