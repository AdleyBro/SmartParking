package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import logic.*;
import db.Plaza;
//import logic.Logic;

@WebServlet("/ReservarPlaza")
public class ReservarPlaza extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String respuesta;
		Boolean ok = false;
		try 
		{ 
			int idparking = Integer.parseInt(request.getParameter("idpark"));
			String nombreUsuario = request.getParameter("nombreU"); 
            String fechaI = request.getParameter("fechaI");
			String fechaF = request.getParameter("fechaF");
			double diferenciaEnHoras = Math.abs((float)((float)(Timestamp.valueOf(fechaF).getTime() - Timestamp.valueOf(fechaI).getTime())/3600000.00)); //2.0-> 2:30 2.0
			String hoursI = fechaI.substring(10, 18);// yyyy-mm-dd 20:30:40
			double precio = 1.1 * Logic.getHoraParking(idparking,hoursI);
			int idPlaza = Logic.getPlazaFromDB(idparking, fechaI, fechaF);

			ok=Logic.storeNewReserva(fechaI, fechaF, nombreUsuario, idparking, idPlaza, precio);
			if(ok){respuesta = "Plaza reservada. El número de la plaza es " + idPlaza;}
			else{respuesta = "No hay plazas disponibles para el horario indicado";}
			
    
            out.println(respuesta);

		} catch (Exception e) {
			respuesta = e.toString();
			//respuesta = "Formato incorrecto. Debe ser: YYYY/MM/dd hh:mm:ss";
			out.println(respuesta);
		} finally {
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}