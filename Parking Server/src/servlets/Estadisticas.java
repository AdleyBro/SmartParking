package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.File;

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
		    
		try {
            int idparking = Integer.parseInt(request.getParameter("idparking"));
            HashMap<Integer, Integer> datos = Logic.getPlazasMasUsadas(idparking);
           
            DefaultCategoryDataset vDatos = new DefaultCategoryDataset();

            for (Integer idP : datos.keySet())
                vDatos.setValue(datos.get(idP), "", idP.toString());
            
            JFreeChart chart = ChartFactory.createBarChart("Plazas mas ocupadas del parking nº " + idparking, "Plaza",
                    "Cantidad", vDatos);

            String folderPath = "D:/1CarpetasVarias/_Universidad_/Tercer Curso/Cuatrimestre 1/Repositorio/SmartParking/Parking Server/src/main/webapp/estadisticas/";
            ChartUtils.saveChartAsPNG(new File(folderPath + "res.png"), chart, 800, 800);

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

