package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.ConectionDB;
import db.Plaza;

public class Logic {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean storeNewUser(String nombre, String pass, Timestamp ts) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		FileWriter fw = null;
		File fd;
		try {

			fd = new File("D:\\Uni\\Computacion ubicua\\p2\\logs\\log.txt");

			fw = new FileWriter(fd);
			con = conector.obtainConnection();
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.setUsuario(con);
			ps.setString(1, nombre);
			ps.setString(2, pass);
			ps.setString(3, sdf.format(ts));

			// Log.log.info("Query para registrar usuario=> {}", ps.toString());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {

			try {

				fw.write("Error en la query "+e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            //Log.log.error("Error: {}", e);
            return false;
		} catch (NullPointerException e)
		{
            try {
				fw.write("Error de null pointer "+e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return false;
		} catch (Exception e)
		{
			try {
				fw.write("Error excepcion generica "+e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return false;
		} finally
		{
			try {
				fw.close();;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				
			}
			conector.closeConnection(con);
		}
	}
    public static boolean storeNewCliente(int IdC,String nombre ,String email,int telefono,Timestamp fechaRegistro,String nombreU)
	{
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection();
			//Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDB.setCliente(con);
			ps.setInt(1,IdC);
            ps.setString(2,nombre);
            ps.setString(3,email);
            ps.setInt(4,telefono);
			ps.setString(5, sdf.format(fechaRegistro));
            ps.setString(6,nombreU);
		
		
			//Log.log.info("Query para registrar cliente=> {}", ps.toString());
            ps.executeUpdate();
            return true;
		} catch (SQLException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            return false;
		} catch (NullPointerException e)
		{
            e.getMessage();
           // Log.log.error("Error: {}", e);
            return false;
		} catch (Exception e)
		{
            e.getMessage();
            //Log.log.error("Error:{}", e);
            return false;
		} finally
		{
			conector.closeConnection(con);
		}
    }
    
    public static int getCountIdCliente(int IdC)
	{
		ConectionDB conector = new ConectionDB();
        Connection con = null;
        int conteo = 0;
		try
		{
           
			con = conector.obtainConnection();
			//Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDB.getCountIdCliente(con);
			
            
		
		
			//Log.log.info("Query para contar numero de clientes con un determinado Idcliente=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
			
			rs.next();
            conteo =rs.getInt("recuento");
			
        

            return conteo;
		} catch (SQLException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (NullPointerException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (Exception e)
		{
            e.getMessage();
            ///Log.log.error("Error:{}", e);
            
		} finally
		{
            
            conector.closeConnection(con);
            
        }
        return conteo;
	}


	public static boolean validPassUsuario(String nombreUsuario,String contraseña)
	{
		ConectionDB conector = new ConectionDB();
        Connection con = null;
        boolean pValid=false;
		try
		{
           
			con = conector.obtainConnection();
			//Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDB.getPassUsuario(con);
			ps.setString(1,nombreUsuario);
            
		
		
			//Log.log.info("Query para contar numero de clientes con un determinado Idcliente=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
			
			rs.next();
			String pass = rs.getString("pass");
			
			pValid = pass.equals(contraseña);
			
			

           
		} catch (SQLException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (NullPointerException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (Exception e)
		{
            e.getMessage();
            ///Log.log.error("Error:{}", e);
            
		} finally
		{
            
            conector.closeConnection(con);
            
        }
        return pValid;
	}
	public static boolean validNombreDeUsuario(String nombreUsuario)
	{
		ConectionDB conector = new ConectionDB();
        Connection con = null;
        int conteo=0;
		try
		{
           
			con = conector.obtainConnection();
			//Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDB.getCountUsuario(con);
			ps.setString(1,nombreUsuario);
            
		
		
			//Log.log.info("Query para contar numero de clientes con un determinado Idcliente=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
			
			rs.next();
            conteo = rs.getInt("recuento");
			
			

           
		} catch (SQLException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (NullPointerException e)
		{
            e.getMessage();
            //Log.log.error("Error: {}", e);
            
		} catch (Exception e)
		{
            e.getMessage();
            ///Log.log.error("Error:{}", e);
            
		} finally
		{
            
            conector.closeConnection(con);
            
        }
        return conteo>0;
	}
	public static ArrayList<Plaza> getPlazaFromDB(int IdParking)
	{
		ArrayList<Plaza> plazas = new ArrayList<Plaza>();
		
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection();
			//Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDB.getPlazasParking(con);
			ps.setInt(1, IdParking);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Plaza plaza = new Plaza();
				plaza.setId_parking(rs.getInt("IdParking"));
				plaza.setId_plaza(rs.getInt("IdPlaza"));
				plaza.setEsta_ocupado(rs.getBoolean("EstaOcupado"));
				plaza.setEs_reservable(rs.getBoolean("EsReservable"));
				plazas.add(plaza);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			plazas = new ArrayList<Plaza>();
		} finally
		{
			conector.closeConnection(con);
		}
		return plazas;
	}

}


