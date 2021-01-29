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

import com.google.gson.Gson;

import db.Parking;
import logic.*;

@WebServlet("/GetMapaParking")
public class GetMapaParking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String respuesta = "";
			ArrayList<Parking> values = Logic.getParkingFromDB();

			Iterator<Parking> iterator = values.iterator();
			while (iterator.hasNext()) {
				Parking temp = iterator.next();
				respuesta += temp.getId_parking() + "#" + temp.getCiudad() + "#" + temp.getDireccion()
							 + "#" + temp.getLatitud() + "#" + temp.getLongitud();
				respuesta += "_";
			}
			respuesta = respuesta.substring(0, respuesta.length() - 1); 

			out.println(respuesta);
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