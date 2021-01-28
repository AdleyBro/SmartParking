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
//import logic.Logic;

@WebServlet("/ReservarPlaza")
public class ReservarPlaza extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Reservar plaza en la DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int idcliente;
		String respuesta;
		try 
		{ 
			int idplaza =Integer.parseInt(request.getParameter("idplaza"));
			int idparking =Integer.parseInt(request.getParameter("idpark"));
			String nombreUsuario = request.getParameter("nombreU");
            String fechaI = request.getParameter("fechaI");
			String fechaF = request.getParameter("fechaF");
			idcliente =Logic.getIdCliente(nombreUsuario);
			double diferenciaEnHoras= Math.abs((float)((float)(Timestamp.valueOf(fechaF).getTime() - Timestamp.valueOf(fechaI).getTime())/3600000.00)); //2.0-> 2:30 2.0
			String hoursI = fechaI.substring(10, 18);
			double precio = diferenciaEnHoras * Logic.getHoraParking(idparking,hoursI) ;

			boolean ok= Logic.storeNewReserva(fechaI, fechaF,idcliente, idparking, idplaza, precio);
			
			
			
			
			if(ok){
				respuesta="La reserva ha salido bien";
			}else{
				respuesta="La reserva ha salido mal";
			}
                      
           
            out.println(respuesta);
                      
           
            
           
           
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
			respuesta="Plaza no disponible ";
			out.println(respuesta);
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