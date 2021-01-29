package db;

import javax.sql.*;
import java.sql.*;
import java.util.Calendar;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.Log;
import javax.naming.NamingException;

public class ConectionDB {

  public Connection obtainConnection() throws NullPointerException, ClassNotFoundException, NamingException {
    String usuario = "root";
    String password = "";
    String nombreBBDD = "parking";
    Connection con = null;
    // Calendar cal = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBBDD, usuario, password);
      Calendar calendar = Calendar.getInstance();
      Date date = new Date(calendar.getTime().getTime());
      Log.logdb.debug("Conexión creada, DB Id: %i fecha de obtención: %s ", con.toString(), date.toString());
    } catch (SQLException sqlException) {
      Log.logdb.error("ERROR sql obteniendo la conexión: " + sqlException);
    }
    return con;
  }

  public void closeConnection(Connection con) {

    try {
      Log.logdb.info("Cerrando la conexión. ");
      if (con != null) {
        con.close();
      }

    } catch (SQLException sqlException) {
      Log.logdb.error("ERROR sql cerrando la conexión: " + sqlException);
    }

  }

  public void closeTransaction(Connection con) {

    try {
      con.commit();
      Log.logdb.debug("Transaction closed");
    } catch (SQLException sqlException) {
      Log.logdb.error("Error closing the transaction: " + sqlException);
    }

  }

  public void cancelTransaction(Connection con) {

    try {
      con.rollback();
      Log.logdb.debug("Transaction canceled");
    } catch (SQLException sqlException) {
      Log.logdb.error("ERROR sql when canceling the transation: " + sqlException);
    }

  }

  public static PreparedStatement getStatement(Connection con, String sql) {
    PreparedStatement ps = null;
    try {
      if (con != null) {
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      }
    } catch (SQLException sqlException) {
      Log.logdb.warn("ERROR sql creating PreparedStatement:{} " + sqlException);
    }

    return ps;
  }

  // Llamadas a la Base de Datos
  public static PreparedStatement setFoo(Connection con) {
    return getStatement(con, "INSERT INTO foo (basura,nada) VALUES (?,?);");
  }
  public static PreparedStatement getParking(Connection con) {

    return getStatement(con, "SELECT * FROM parking ");
  }

  public static PreparedStatement getPlazasParking(Connection con) {

    return getStatement(con,
        "SELECT * FROM (SELECT * FROM plaza where IdParking=? AND EsReservable=1) as PlazaBien WHERE plazabien.IdPlaza NOT IN (Select Idplaza FROM reserva WHERE IdParking=? AND ((FechaHoraInicio BETWEEN ?  AND ? ) OR (FechaHoraFin BETWEEN ?  AND ?)))");
  }
  public static PreparedStatement getPlazasParkingMosquitto(Connection con) {

    return getStatement(con,
        "SELECT * FROM (SELECT * FROM plaza WHERE IdParking=?))");
  }
  public static PreparedStatement getCountPlazasParking(Connection con) {

    return getStatement(con,
        "SELECT COUNT(IdPlaza) AS nPlazasOcupadas FROM plaza WHERE IdParking=? AND EstaOcupado like '0'");
  }

  public static PreparedStatement updatePlazas(Connection con) {

    return getStatement(con, "UPDATE usuario SET UltimoAccesoParking = ? WHERE NombreDeUsuario = ?;");
  }

  public static PreparedStatement getCountUsuario(Connection con) {

    return getStatement(con, "SELECT count(NombreDeUsuario) as recuento FROM usuario WHERE NombreDeUsuario=? ");
  }

  public static PreparedStatement setUsuario(Connection con) {
    return getStatement(con, "INSERT INTO usuario (NombreDeUsuario,Password,UltimoAccesoParking) VALUES (?,?,?);");
  }

  public static PreparedStatement setCliente(Connection con) {
    return getStatement(con,
        "INSERT INTO cliente (IdCliente,Nombre,Email,Tlf,FechaDeRegistro,NombreDeUsuario) VALUES (?,?,?,?,?,?);");
  }

  public static PreparedStatement updateUsuarioTS(Connection con) {

    return getStatement(con, "UPDATE usuario SET UltimoAccesoParking = ? WHERE NombreDeUsuario = ?;");
  }

  public static PreparedStatement getPassUsuario(Connection con) {

    return getStatement(con, "SELECT Password FROM usuario WHERE NombreDeUsuario=?");
  }

  public static PreparedStatement getCountIdCliente(Connection con) {

    return getStatement(con, "SELECT count(IdCliente) as recuento FROM cliente");
  }
  public static PreparedStatement getIdCliente(Connection con) {

    return getStatement(con, "SELECT IdCliente FROM cliente WHERE NombreDeUsuario=?");
  }
  public static PreparedStatement getPrecioHora(Connection con) {

    return getStatement(con, "SELECT precio FROM tabla_de_precios WHERE IdParking=? AND hora IN(SELECT MAX(tablaDePrecios.Hora) as hora FROM (SELECT * FROM tabla_de_precios WHERE IdParking=? AND hora <= ?) as tablaDePrecios)");
  }
  public static PreparedStatement setReserva(Connection con) {
    return getStatement(con,
        "INSERT INTO reserva (FechaHoraFin,FechaHoraInicio,IdCliente,IdParking,IdPlaza,PrecioPagado) VALUES (?,?,?,?,?,?);");
  }
  /*
   * - Usuarios registrados en ultimo tiempo dado - Cantidad de plazas ocupadas en
   * un parking en un tiempo dado - Veces que plaza ha sido ocupada en un último
   * tiempo dado - Cuantas reservas se han realizado en un parking ultimo tiempo
   * dado - Reservas que tiene la plaza en un tiempo dado - Tiempo medio de
   * reserva de una plaza
   * SELECT precio FROM tabla_de_precios WHERE IdParking=300 AND hora IN(SELECT MAX(tablaDePrecios.Hora) as hora FROM (SELECT * FROM tabla_de_precios WHERE IdParking=300 AND hora <= '20:00:00') as tablaDePrecios)
   **/
}// SELECT * FROM (SELECT IdPlaza,IdParking FROM plaza where IdParking=300 AND
 // EsReservable=1) as PlazaBien WHERE plazabien.IdPlaza NOT IN (Select Idplaza
 // FROM reserva WHERE IdParking=300 AND ((FechaHoraInicio BETWEEN
 // '2021-01-28-16:00:00' AND '2021-01-28-19:00:00') OR (FechaHoraFin BETWEEN
 // '2021-01-28-16:00:00' AND '2021-01-28-19:00:00')))