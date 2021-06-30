package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Cliente;
import db.Reserva;
import com.google.gson.Gson;


import logic.*;
//import logic.Logic;



@WebServlet("/GetReservaProxima")
public class GetReservaProxima extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try 
		{ 
			String idCliente = request.getParameter("idCliente");
			
			ArrayList<String> values = Logic.getReservaProxima(Integer.parseInt(idCliente));

			//String jsonReservaProxima = new Gson().toJson(values);
			String respuesta = "";
			Iterator<String> iterator = values.iterator();
			while (iterator.hasNext()) {
				respuesta += iterator.next() + "#";
			}

			out.println(respuesta);
           

		} catch (Exception e) {

		} finally {
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}