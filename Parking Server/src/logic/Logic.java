package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import db.ConectionDB;
import db.Parking;
import db.Plaza;
import db.Cliente;
import mqtt.*;

public class Logic {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void updateEstadoPlaza(boolean estaOcupado, int idparking, int idplaza) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		
		try {	

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.updateEstadoPlazas(con);
			ps.setBoolean(1, estaOcupado);
			ps.setInt(2, idparking);
			ps.setInt(3, idplaza);
			
			int numeroDeFilasActualizadas = ps.executeUpdate();
			
			MQTTPublisher.publish(ProjectInitializer.getActualBroker(), "Numero de filas actualizadas", String.valueOf(numeroDeFilasActualizadas));
			
		} catch (SQLException e) {

			MQTTPublisher.publish(ProjectInitializer.getActualBroker(), "SqlException", e.toString());
			
		} catch (NullPointerException e) {

			e.printStackTrace();
			MQTTPublisher.publish(ProjectInitializer.getActualBroker(), "NullPexception", e.toString());

			
		} catch (Exception e) {

			MQTTPublisher.publish(ProjectInitializer.getActualBroker(), "Exception", e.toString());
			e.printStackTrace();

			
		} finally {

			conector.closeConnection(con);
		}
	}
	

	public static String storeNewCliente(String nombreU, String nombre, String email, int telefono, String password, Timestamp fechaRegistro) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.setCliente(con);
			ps.setString(1, nombreU);
			ps.setString(2, nombre);
			ps.setString(3, email);
			ps.setInt(4, telefono);
			ps.setString(5, sdf.format(fechaRegistro));
			ps.setString(6, password);
			ps.setBoolean(7, false);
			

			
			ps.executeUpdate();
			return "Todo ha salido bien";
		} catch (SQLException sqle) {
			sqle.getMessage();
			
			return sqle.getMessage();
		} catch (NullPointerException nulle) {
			nulle.getMessage();
			
			return nulle.getMessage();
		} catch (Exception e) {
			e.getMessage();
			
			return e.getMessage();
		} finally {
			conector.closeConnection(con);
		}
	}


	public static boolean validPassUsuario(String nombreUsuario, String contra) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		boolean pValid = false;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getPassUsuario(con);
			ps.setString(1, nombreUsuario);

			
			ResultSet rs = ps.executeQuery();

			rs.next();
			String pass = rs.getString("Password");

			pValid = pass.equals(contra);

		} catch (SQLException e) {
			e.getMessage();
			

		} catch (NullPointerException e) {
			e.getMessage();
			

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return pValid;
	}

	public static boolean validNombreDeUsuario(String nombreUsuario) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int conteo = 0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getCountUsuario(con);
			ps.setString(1, nombreUsuario);

			
			ResultSet rs = ps.executeQuery();

			rs.next();
			conteo = rs.getInt("recuento");
			
		} catch (SQLException e) {
			e.getMessage();
			

		} catch (NullPointerException e) {
			e.getMessage();
			

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);
			return conteo > 0;
		}
		
	}

	public static int getPlazaFromDB(int IdParking, String fechaInicio, String fechaFin) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int idPlaza = -1;
		try {
			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.getPlazasParking(con);
			ps.setInt(1, IdParking);
			ps.setInt(2, IdParking);
			ps.setString(3, fechaInicio);
			ps.setString(4, fechaFin);
			ps.setString(5, fechaInicio);
			ps.setString(6, fechaFin);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			//plaza.setId_parking(rs.getInt("IdParking"));
			//plaza.setId_plaza(rs.getInt("IdPlaza"));
			//plaza.setEsta_ocupado(rs.getBoolean("EstaOcupado"));
			//plaza.setEs_reservable(rs.getBoolean("EsReservable"));
			idPlaza = rs.getInt("IdPlaza");


		} catch (

		SQLException e) {
			
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			
		} finally {
			conector.closeConnection(con);
		}
		return idPlaza;
	}

	public static ArrayList<Parking> getParkingFromDB() {
		ArrayList<Parking> parkings = new ArrayList<Parking>();

		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.getParking(con);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Parking parking = new Parking();
				rs.getFetchDirection();
				parking.setId_parking(rs.getInt("IdParking"));
				parking.setDireccion(rs.getString("Direccion"));
				parking.setCiudad(rs.getString("Ciudad"));
				parking.setLatitud(rs.getDouble("Latitud"));
				parking.setLongitud(rs.getDouble("Longitud"));
				parkings.add(parking);
			}
		} catch (SQLException e) {

		} catch (NullPointerException e) {

		} catch (Exception e) {

		} finally {
			conector.closeConnection(con);
		}
		return parkings;
	}
	
	

	public static double getHoraParking(int idparking,String hora) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		double php = 0.0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getPrecioHora(con);
			ps.setInt(1, idparking);
			ps.setInt(2, idparking);
			ps.setString(3, hora);
			ResultSet rs = ps.executeQuery();

			rs.next();
			php = rs.getDouble("Precio");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return php;
	}
	
	public static Boolean storeNewReserva(String fechaI, String fechaF, String nombreUsuario, int idparking, int idplaza, double preciopagado) {
		 ConectionDB conector = new ConectionDB();
		 Connection con = null;
		try {
			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.setReserva(con);
			ps.setString(1, fechaI);
			ps.setString(2, fechaF);
			ps.setDouble(3, preciopagado);
			ps.setString(4, nombreUsuario);
			ps.setInt(5, idplaza);
			ps.setInt(6, idparking);
			
			
		
			ps.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			
			sqle.getMessage();
			return false;
		} catch (NullPointerException nulle) {
			nulle.getMessage();
		
			return false;
		} catch (Exception e) {
			e.getMessage();

			return false;
		} finally {
			conector.closeConnection(con);
		}
	}

	public static  ArrayList<String> getDatosCliente(String nombreUsuario) { 
		ArrayList<String> datos = new ArrayList<>();
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {

			con = conector.obtainConnection();
		

			PreparedStatement ps = ConectionDB.getCliente(con);

			ps.setString(1, nombreUsuario);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			datos.add(0, rs.getString("NombreDeUsuario"));
			datos.add(1, rs.getString("Nombre"));
			datos.add(2, rs.getString("Email"));
			datos.add(3, rs.getString("Tlf"));
			datos.add(4, rs.getString("FechaDeRegistro"));
			datos.add(5, rs.getString("Password"));
			
			
		} catch (SQLException e) {
			e.getMessage();
			

		} catch (NullPointerException e) {
			e.getMessage();
			

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}

		return datos;
	}

	public static  ArrayList<String> getReservaProxima(String nombreU) { 
		ArrayList<String> datos = new ArrayList<>();
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.getReservaProxima(con);
			ps.setString(1, nombreU);

			ResultSet rs = ps.executeQuery();
			rs.next();
			
			datos.add(rs.getString("IdPlaza"));
			datos.add(rs.getString("FechaHoraInicio"));
			datos.add(rs.getString("FechaHoraFin"));
			datos.add(rs.getString("PrecioPagado"));

			String idParking = rs.getString("IdParking");
			datos.add(idParking);

			ps = ConectionDB.getLatLongParking(con);
			ps.setInt(1, Integer.parseInt(idParking));

			rs = ps.executeQuery();
			rs.next();
			datos.add(rs.getString("Latitud"));
			datos.add(rs.getString("Longitud"));

		} catch (SQLException e) {
			datos.add(e.toString());
			e.getMessage();
			

		} catch (NullPointerException e) {

			datos.add(e.toString());
			e.getMessage();
			

		} catch (Exception e) {
			datos.add(e.toString());
			e.getMessage();
		

		} finally {

			conector.closeConnection(con);

		}

		return datos;
	}

	public static void storeNewHistorialEntrada(Timestamp fechaE, String nombre_usuario, int idparking) {
    	ConectionDB conector = new ConectionDB();
    	Connection con = null;
   		try {
       	con = conector.obtainConnection();
   

    	PreparedStatement ps = ConectionDB.setHistorialEntrada(con);
       
       	ps.setString(1, sdf.format(fechaE));
       	ps.setString(2, nombre_usuario);
       	ps.setInt(3, idparking);

   
       	ps.executeUpdate();
       
   		} catch (SQLException e) {
       		e.getMessage();
   
     
   		} catch (NullPointerException e) {
      		e.getMessage();
   
       
   		} catch (Exception e) {
       		e.getMessage();

       
   		} finally {
       		conector.closeConnection(con);
   		}
	}

	public static void storeNewHistorialSalida(Timestamp fechaS, String nombre_usuario, int idparking) {
    	ConectionDB conector = new ConectionDB();
    	Connection con = null;
   		try {
       		con = conector.obtainConnection();
   

       		PreparedStatement ps = ConectionDB.setHistorialSalida(con);
       		ps.setString(1, sdf.format(fechaS));
       		ps.setString(2, nombre_usuario);
       		ps.setInt(3, idparking);

   
       		ps.executeUpdate();
       
   		} catch (SQLException e) {
       		e.getMessage();
   
       		
   		} catch (NullPointerException e) {
       		e.getMessage();
   
       
   		} catch (Exception e) {
       		e.getMessage();

       
   		} finally {
       		conector.closeConnection(con);
   		}
	}
	public static void storeNewHistorialPlaza(Timestamp fechaE, int idplaza, int idparking,boolean esReservable) {
    	ConectionDB conector = new ConectionDB();
    	Connection con = null;
   		try {
       		con = conector.obtainConnection();
   

       		PreparedStatement ps = ConectionDB.setHistorialPlaza(con);
       		ps.setString(1, sdf.format(fechaE));
       		ps.setBoolean(2, esReservable);
			ps.setString(3, null);
			ps.setInt(4, idplaza);
			ps.setInt(5, idparking);

       		ps.executeUpdate();
       
   		} catch (SQLException e) {
       		e.getMessage();
   
       		
   		} catch (NullPointerException e) {
       		e.getMessage();
   
       
   		} catch (Exception e) {
       		e.getMessage();

       
   		} finally {
       		conector.closeConnection(con);
   		}
	}
	public static void updateHistorialPlaza(String fechaE,Timestamp fechaS, int idplaza) {
    	ConectionDB conector = new ConectionDB();
    	Connection con = null;
   		try {
       		con = conector.obtainConnection();

       		PreparedStatement ps = ConectionDB.updateHistorialPlaza(con);
			ps.setString(1, sdf.format(fechaS));
       		ps.setString(2, fechaE);
       		ps.setInt(3, idplaza);
       		
       		ps.executeUpdate();
       
   		} catch (SQLException e) {
       		e.getMessage();
   
       		
   		} catch (NullPointerException e) {
       		e.getMessage();
   
       
   		} catch (Exception e) {
       		e.getMessage();

       
   		} finally {
       		conector.closeConnection(con);
   		}
	}

	public static boolean getEsReservable(int idplaza) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		boolean eR = false;
		try {

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.getEsReservable(con);
			ps.setInt(1, idplaza);
			
			ResultSet rs = ps.executeQuery();

			rs.next();
			eR = rs.getBoolean("EsReservable");

			return eR;
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return eR;
	}

	public static String getFechaEHPlaza(int idplaza) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		String fechaE = " ";
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getFechaE(con);
			ps.setInt(1, idplaza);
			
			ResultSet rs = ps.executeQuery();

			rs.next();
			fechaE = rs.getString("FechaHoraEntrada");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return fechaE;
	}
	
	public static boolean getEstadoCliente(String nombre_usuario) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		boolean estaDentro = false;
		try {

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.getEstadoCliente(con);
			ps.setString(1, nombre_usuario);
			
			ResultSet rs = ps.executeQuery();

			rs.next();
			estaDentro = rs.getBoolean("EstaDentro");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return estaDentro;
	}
	public static void updateEstadoCliente(boolean estadoCliente,String nombre_usuario) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.updateEstadoCliente(con);
			ps.setBoolean(1, estadoCliente);
			ps.setString(2, nombre_usuario);
			
			ResultSet rs = ps.executeQuery();

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);
		}
		
	}

	/**
		Función estadística, enseña al usuario las plazas más comunes
	 */
	public static HashMap<Integer,Integer> getPlazasMasUsadas(int parking) {
		HashMap<Integer,Integer> plazas = new HashMap<Integer,Integer>();

		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.getPlazasComunes(con);
			ps.setInt(1,parking);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				rs.getFetchDirection();
				plazas.put(rs.getInt("IdPlaza"), rs.getInt("nOcupaciones"));
			}
		} catch (SQLException e) {

		} catch (NullPointerException e) {

		} catch (Exception e) {

		} finally {
			conector.closeConnection(con);
		}
		return plazas;
	}

	public static int getNUserMonth() {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int nUser=0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getNUserMonth(con);
			
			ResultSet rs = ps.executeQuery();

			rs.next();
			nUser = rs.getInt("nClientesM");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return nUser;
	}
	public static int getNUserParking(int idparking, String fechaHora) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int nUser=0;
		try {

			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.getNUserParking(con);
			ps.setInt(1, idparking);
			ps.setString(2, fechaHora);
			ps.setString(3, fechaHora);
			ResultSet rs = ps.executeQuery();

			rs.next();
			nUser = rs.getInt("nClientesH");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return nUser;
	}
	public static String getDiaMasReservado(int idparking) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		String dia="";
		int dayOw=0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getNUserParking(con);
			ps.setInt(1, idparking);
			ResultSet rs = ps.executeQuery();

			rs.next();
			dayOw = rs.getInt("dia");
			switch (dayOw) {
				case 1:
					dia= "Domingo";
					break;
				case 2:
					dia= "Lunes";
					break;
				case 3:
					dia= "Martes";
					break;
				case 4:
					dia= "Miercoles";
					break;
				case 5:
					dia= "Jueves";
					break;
				case 6:
					dia= "Viernes";
					break;
				case 7:
					dia= "Sabado";
					break;
				
			}
			
			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return dia;
	}




}
