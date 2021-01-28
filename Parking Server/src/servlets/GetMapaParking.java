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

import db.Parking;
import logic.*;
//import logic.Logic;

@WebServlet("/GetMapaParking")
public class GetMapaParking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Log.log.info("-- Obteniendo mapa--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			ArrayList<Parking> values = Logic.getParkingFromDB();

			String jsonParkings = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonParkings);
			out.println(jsonParkings);
		} catch (NumberFormatException nfe) {
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) {
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) {
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}