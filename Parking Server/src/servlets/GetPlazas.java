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

import db.Plaza;
import logic.*;
//import logic.Logic;



@WebServlet("/GetPlazas")
public class GetPlazas extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Obteniendo plazas de la DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{ 
           // String nombre = request.getParameter("nombreU");
           // String pass = request.getParameter("pass");
           // String email = request.getParameter("email");
			
		   int parkingid = Integer.parseInt(request.getParameter("parkingId"));

		   //ArrayList<Plaza> values =Logic.getPlazaFromDB(parkingId);
		  // String jsonStations = new Gson().toJson(values);
		   //Log.log.info("JSON Values=> {}", jsonStations);
		   //out.println(jsonStations);
            
            
                      
           
            
           //out.println("nombre del abonado : "+nombre+" email: "+email+" pass: "+pass);
           
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