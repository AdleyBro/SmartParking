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

@WebServlet("/GetPlazas")
public class GetPlazas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		   PrintWriter out = response.getWriter();
		try {
			int parkingid = Integer.parseInt(request.getParameter("parkingId"));
			String fechaI = request.getParameter("fechaI");
			String fechaF = request.getParameter("fechaF");
			out.println("Fecha inicio que has puesto: " + fechaI + "\n");
			out.println("Fecha fin que has puesto: " + fechaF + "\n");

		} catch (NumberFormatException nfe) {
			out.println("-1");
		} catch (IndexOutOfBoundsException iobe) {
		} catch (Exception e) {
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}