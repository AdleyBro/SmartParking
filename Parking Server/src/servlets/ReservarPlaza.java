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
		int idcliente;
		String respuesta;
		try 
		{ 
			int idplaza = Integer.parseInt(request.getParameter("idplaza"));
			int idparking = Integer.parseInt(request.getParameter("idpark"));
			String nombreUsuario = request.getParameter("nombreU"); 
            String fechaI = request.getParameter("fechaI");
			String fechaF = request.getParameter("fechaF");
			//idcliente = Logic.getIdCliente(nombreUsuario);
			double diferenciaEnHoras = Math.abs((float)((float)(Timestamp.valueOf(fechaF).getTime() - Timestamp.valueOf(fechaI).getTime())/3600000.00)); //2.0-> 2:30 2.0
			String hoursI = fechaI.substring(10, 18);
			double precio = diferenciaEnHoras * Logic.getHoraParking(idparking,hoursI);
			ArrayList<Plaza> values = Logic.getPlazaFromDB(idparking, fechaI, fechaF);
			idplaza = values.get(0).getId_plaza();

			Logic.storeNewReserva(fechaI, fechaF, nombreUsuario, idparking, idplaza, precio);

			respuesta = "Plaza reservada";
    
            out.println(respuesta);

		} catch (Exception e) {
			respuesta = "Plaza no disponible ";
			out.println(respuesta);
		} finally {
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}