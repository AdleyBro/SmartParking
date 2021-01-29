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

import db.Plaza;
import db.Cliente;
import logic.*;
//import logic.Logic;

@WebServlet("/GetDatosCliente")
public class GetDatosCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
            
            String nombreU = request.getParameter("nombreU");
			
			ArrayList<String> values = Logic.getDatosCliente(nombreU);

			//String jsonDatosClientes = new Gson().toJson(values);
			//Log.log.info("JSON Values=> {}", jsonDatosClientes);

			String respuesta = "";
			Iterator<String> iterator = values.iterator();
			while (iterator.hasNext()) {
				respuesta += iterator.next() + "#";
			}

			out.println(respuesta);

		} catch (NumberFormatException nfe) {
			out.println("-1");
		} catch (IndexOutOfBoundsException iobe) {
			out.println("-1");
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