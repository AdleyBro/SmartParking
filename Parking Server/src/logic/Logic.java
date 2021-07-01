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

import db.ConectionDB;
import db.Parking;
import db.Plaza;
import db.Cliente;

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
			
			ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();

			
			
		} catch (NullPointerException e) {

			e.printStackTrace();

			
		} catch (Exception e) {

			e.printStackTrace();

			
		} finally {

			conector.closeConnection(con);
		}
	}
	public static boolean storeNewUser(String nombre, String nombreU, int telefono, String email, Timestamp ts) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;

		try {

			con = conector.obtainConnection();

			PreparedStatement ps = ConectionDB.setUsuario(con);
			ps.setString(1, nombre);
			ps.setString(2, nombreU);
			ps.setInt(3, telefono);
			ps.setString(4, email);
			ps.setString(5, sdf.format(ts));
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			
			return false;
		} catch (NullPointerException e) {

			e.printStackTrace();

			return false;
		} catch (Exception e) {

			e.printStackTrace();

			return false;
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

	public static int getCountIdCliente(int IdC) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int conteo = 0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getCountIdCliente(con);

			
			ResultSet rs = ps.executeQuery();

			rs.next();
			conteo = rs.getInt("recuento");

			return conteo;
		} catch (SQLException e) {
			e.getMessage();
			

		} catch (NullPointerException e) {
			e.getMessage();
			

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return conteo;
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

	public static ArrayList<Plaza> getPlazaFromDB(int IdParking, String fechaInicio, String fechaFin) {
		ArrayList<Plaza> plazas = new ArrayList<Plaza>();
		
		ConectionDB conector = new ConectionDB();
		Connection con = null;
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
			while (rs.next()) {
				Plaza plaza = new Plaza();
				plaza.setId_parking(rs.getInt("IdParking"));
				plaza.setId_plaza(rs.getInt("IdPlaza"));
				plaza.setEsta_ocupado(rs.getBoolean("EstaOcupado"));
				plaza.setEs_reservable(rs.getBoolean("EsReservable"));
				plazas.add(plaza);
			}
		} catch (

		SQLException e) {
			
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			
		} finally {
			conector.closeConnection(con);
		}
		return plazas;
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
	public static int getIdCliente(String nombreUsuario) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		int Idc = 0;
		try {

			con = conector.obtainConnection();
			

			PreparedStatement ps = ConectionDB.getIdCliente(con);

			ps.setString(1, nombreUsuario);
			ResultSet rs = ps.executeQuery();

			rs.next();
			Idc = rs.getInt("IdCliente");

			
		} catch (SQLException e) {
			e.getMessage();
		

		} catch (NullPointerException e) {
			e.getMessage();
		

		} catch (Exception e) {
			e.getMessage();
			

		} finally {

			conector.closeConnection(con);

		}
		return Idc;
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
	public static boolean storeNewReserva(String fechaI, String fechaF, int idcliente, int idparking, int idplaza, double preciopagado) {
		 ConectionDB conector = new ConectionDB();
		 Connection con = null;
		try {
			con = conector.obtainConnection();
		

			PreparedStatement ps = ConectionDB.setReserva(con);
			ps.setString(1, fechaF);
			ps.setString(2, fechaI);
			ps.setInt(3, idcliente);
			ps.setInt(4, idparking);
			ps.setInt(5, idplaza);
			ps.setDouble(6, preciopagado);

		
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.getMessage();
		
			return false;
		} catch (NullPointerException e) {
			e.getMessage();
		
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
			datos.add(0, rs.getString("IdCliente"));
			datos.add(1, rs.getString("Nombre"));
			datos.add(2, rs.getString("Email"));
			datos.add(3, rs.getString("Tlf"));
			datos.add(4, rs.getString("Password"));
			datos.add(5, rs.getString("FechaDeRegistro"));			
			datos.add(6, rs.getString("NombreDeUsuario"));
			
			
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

	public static  ArrayList<String> getReservaProxima(int idCliente) { 
		ArrayList<String> datos = new ArrayList<>();
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {

			con = conector.obtainConnection();
			
			PreparedStatement ps = ConectionDB.getReservaProxima(con);
			ps.setInt(1, idCliente);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			datos.add(rs.getString("IdCliente"));
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
}
