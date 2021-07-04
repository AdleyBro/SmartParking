package servlets;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.ComparableObjectSeries;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.*;

@WebServlet("/SEstadisticas")
public class Estadisticas extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter(); 
			FileWriter html = null;
		    
		try {
			
            int idparking = Integer.parseInt(request.getParameter("idparking"));

			// Estadísticas
            HashMap<Integer, Integer> datosPlazasMasUsadas = Logic.getPlazasMasUsadas(idparking);
			int nuevosUsuariosUltimoMes = Logic.getNUserMonth();
			int numUsuariosAccedidoParking = Logic.getNUserParking(idparking, request.getParameter("fechaHora"));
			HashMap<String, Integer> diaYReservas = Logic.getDiaMasReservado(idparking);
           
            DefaultCategoryDataset vPlazasMasUsadas = new DefaultCategoryDataset();
			DefaultCategoryDataset vDiasYReservas = new DefaultCategoryDataset();

            for (Integer idP : datosPlazasMasUsadas.keySet())
                vPlazasMasUsadas.setValue(datosPlazasMasUsadas.get(idP), "", idP.toString());
            
			for (String dr : diaYReservas.keySet())
				vDiasYReservas.setValue(diaYReservas.get(dr), "", dr);

            JFreeChart chartPlazasMasUsadas = ChartFactory.createBarChart("Plazas mas ocupadas del parking nº " + idparking, "Plaza",
                    "Cantidad", vPlazasMasUsadas);

			JFreeChart chartDiasYReservas = ChartFactory.createBarChart("Reservas por cada día en parking nº " + idparking, "Día",
			"Cantidad", vDiasYReservas);

            String folderPath = "./";
			File plazas = new File(folderPath + "resPlazas.png");
			html = new FileWriter(folderPath + "estadisticas.html");
			
			html.write("<p>Usuarios registrados en el ultimo mes: " + nuevosUsuariosUltimoMes + "</p>");
			html.write("<p>Cantidad de usuarios que han accedido al parking nro " + idparking + ": " + nuevosUsuariosUltimoMes + "</p>");

			if (datosPlazasMasUsadas.size() > 0) {
            	ChartUtils.saveChartAsPNG(plazas, chartPlazasMasUsadas, 200 * datosPlazasMasUsadas.size(), 800);
				html.write("<p><img src='./resPlazas.png' alt='image' /></p>");
			}
			if (diaYReservas.size() > 0) {
				ChartUtils.saveChartAsPNG(new File(folderPath + "resReservasDia.png"), chartDiasYReservas, 200 * diaYReservas.size(), 800);
				html.write("<p><img src='./resReservasDia.png' alt='image' /></p>");
			}
			
			out.println("Estadisticas guardadas con exito en la ruta " + new File(folderPath + "estadisticas.html").getAbsolutePath());

			html.close();

		} catch (Exception e) {
			out.println(e.toString());
		} finally {
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

