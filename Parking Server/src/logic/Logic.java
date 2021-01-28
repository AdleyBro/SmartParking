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

public class Logic {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean storeNewUser(String nombre, String pass, Timestamp ts) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;

		try {

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

			e.printStackTrace();

			// Log.log.error("Error: {}", e);
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

	public static boolean storeNewCliente(int IdC, String nombre, String email, int telefono, Timestamp fechaRegistro,
			String nombreU) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.setCliente(con);
			ps.setInt(1, IdC);
			ps.setString(2, nombre);
			ps.setString(3, email);
			ps.setInt(4, telefono);
			ps.setString(5, sdf.format(fechaRegistro));
			ps.setString(6, nombreU);

			// Log.log.info("Query para registrar cliente=> {}", ps.toString());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);
			return false;
		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);
			return false;
		} catch (Exception e) {
			e.getMessage();
			// Log.log.error("Error:{}", e);
			return false;
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
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getCountIdCliente(con);

			// Log.log.info("Query para contar numero de clientes con un determinado
			// Idcliente=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();

			rs.next();
			conteo = rs.getInt("recuento");

			return conteo;
		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (Exception e) {
			e.getMessage();
			/// Log.log.error("Error:{}", e);

		} finally {

			conector.closeConnection(con);

		}
		return conteo;
	}

	public static boolean validPassUsuario(String nombreUsuario, String contraseña) {
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		boolean pValid = false;
		try {

			con = conector.obtainConnection();
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getPassUsuario(con);
			ps.setString(1, nombreUsuario);

			// Log.log.info("Query para contar numero de clientes con un determinado
			// Idcliente=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();

			rs.next();
			String pass = rs.getString("pass");

			pValid = pass.equals(contraseña);

		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (Exception e) {
			e.getMessage();
			/// Log.log.error("Error:{}", e);

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
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getCountUsuario(con);
			ps.setString(1, nombreUsuario);

			// Log.log.info("Query para contar numero de clientes con un determinado
			// Idcliente=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();

			rs.next();
			conteo = rs.getInt("recuento");

		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (Exception e) {
			e.getMessage();
			/// Log.log.error("Error:{}", e);

		} finally {

			conector.closeConnection(con);

		}
		return conteo > 0;
	}

	public static ArrayList<Plaza> getPlazaFromDB(int IdParking, String fechaInicio, String fechaFin) {
		ArrayList<Plaza> plazas = new ArrayList<Plaza>();
		// String resultado = "Error al crear el statement";
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getPlazasParking(con);
			ps.setInt(1, IdParking);
			ps.setInt(2, IdParking);
			ps.setString(3, fechaInicio);
			ps.setString(4, fechaFin);
			ps.setString(5, fechaInicio);
			ps.setString(6, fechaFin);
			// resultado = ps.toString();
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
			// resultado = "Error al ejecutar la query";
			Log.log.error("Error: {}", e);
			// plazas = new ArrayList<Plaza>();
		} catch (NullPointerException e) {
			Log.log.error("Error: {}", e);
			// plazas = new ArrayList<Plaza>();
		} catch (Exception e) {
			// resultado = "Error excepction generica";
			Log.log.error("Error:{}", e);
			// plazas = new ArrayList<Plaza>();
		} finally {
			conector.closeConnection(con);
		}
		return plazas;
	}

	public static ArrayList<Parking> getParkingFromDB() {
		ArrayList<Parking> parkings = new ArrayList<Parking>();
		// String resultado = "Error al crear el statement";
		ConectionDB conector = new ConectionDB();
		Connection con = null;
		try {
			con = conector.obtainConnection();
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getParking(con);

			// resultado = ps.toString();
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
			// resultado = "Error al ejecutar la query";
			Log.log.error("Error: {}", e);
			// plazas = new ArrayList<Plaza>();
		} catch (NullPointerException e) {
			Log.log.error("Error: {}", e);
			// plazas = new ArrayList<Plaza>();
		} catch (Exception e) {
			// resultado = "Error excepction generica";
			Log.log.error("Error:{}", e);
			// plazas = new ArrayList<Plaza>();
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
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getIdCliente(con);

			ps.setString(1, nombreUsuario);
			ResultSet rs = ps.executeQuery();

			rs.next();
			Idc = rs.getInt("IdCliente");

			
		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (Exception e) {
			e.getMessage();
			/// Log.log.error("Error:{}", e);

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
			// Log.log.debug("Database Connected");

			PreparedStatement ps = ConectionDB.getPrecioHora(con);
			ps.setInt(1, idparking);
			ps.setString(2, hora);
			ResultSet rs = ps.executeQuery();

			rs.next();
			php = rs.getDouble("precio");

			
		} catch (SQLException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (NullPointerException e) {
			e.getMessage();
			// Log.log.error("Error: {}", e);

		} catch (Exception e) {
			e.getMessage();
			/// Log.log.error("Error:{}", e);

		} finally {

			conector.closeConnection(con);

		}
		return php;
	}
}
