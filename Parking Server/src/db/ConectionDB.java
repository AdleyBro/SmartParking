package db;

import javax.sql.*;
import java.sql.*;
import java.util.Calendar;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    } catch (SQLException sqlException) {
    }
    return con;
  }

  public void closeConnection(Connection con) {

    try {
      if (con != null) {
        con.close();
      }

    } catch (SQLException sqlException) {
    }

  }

  public void closeTransaction(Connection con) {

    try {
      con.commit();
    } catch (SQLException sqlException) {
    }

  }

  public void cancelTransaction(Connection con) {

    try {
      con.rollback();
    } catch (SQLException sqlException) {
    }

  }

  public static PreparedStatement getStatement(Connection con, String sql) {
    PreparedStatement ps = null;
    try {
      if (con != null) {
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      }
    } catch (SQLException sqlException) {
    }

    return ps;
  }

  // Llamadas a la Base de Datos
 
  public static PreparedStatement getParking(Connection con) {

    return getStatement(con, "SELECT * FROM parking");
  }

  public static PreparedStatement getPlazasParking(Connection con) {

    return getStatement(con,
        "SELECT * FROM (SELECT * FROM plaza where IdParking=? AND EsReservable=1) as PlazaBien WHERE plazabien.IdPlaza NOT IN (Select Idplaza FROM reserva WHERE IdParking=? AND ((FechaHoraInicio BETWEEN ?  AND ? ) OR (FechaHoraFin BETWEEN ?  AND ?))) LIMIT 0,1");
  }
  public static PreparedStatement getPlazasParkingMosquitto(Connection con) {

    return getStatement(con,
        "SELECT * FROM plaza WHERE IdParking=?");
  }
  public static PreparedStatement getCountPlazasParking(Connection con) {

    return getStatement(con,
        "SELECT COUNT(IdPlaza) AS nPlazasOcupadas FROM plaza WHERE IdParking=? AND EstaOcupado like '0'");
  }

  public static PreparedStatement updateEstadoPlazas(Connection con) {

    return getStatement(con, "UPDATE Plaza SET EstaOcupado=? WHERE IdParking=? AND IdPlaza=?;");
  }

  public static PreparedStatement getCountUsuario(Connection con) {

    return getStatement(con, "SELECT COUNT(NombreDeUsuario) as recuento FROM Cliente WHERE NombreDeUsuario=? ");
  }

  public static PreparedStatement getCliente(Connection con) {
    return getStatement(con, "SELECT * FROM Cliente WHERE NombreDeUsuario=?");
  }

  public static PreparedStatement setCliente(Connection con) {
    return getStatement(con,
        "INSERT INTO Cliente VALUES(?,?,?,?,?,?,?);");
  }

  public static PreparedStatement getPassUsuario(Connection con) {

    return getStatement(con, "SELECT Password FROM Cliente WHERE NombreDeUsuario=?");
  }

  public static PreparedStatement getPrecioHora(Connection con) {

    return getStatement(con, "SELECT precio FROM tabla_de_precios WHERE IdParking=? AND hora IN(SELECT MAX(tablaDePrecios.Hora) as hora FROM (SELECT * FROM tabla_de_precios WHERE IdParking=? AND hora <= ?) as tablaDePrecios)");
  }
  public static PreparedStatement setReserva(Connection con) {
    return getStatement(con,
        "INSERT INTO reserva VALUES (?,?,?,?,?,?);");
  }

  public static PreparedStatement getReservaProxima(Connection con) {
    return getStatement(con, "SELECT * FROM reserva WHERE NombreDeUsuario=? AND FechaHoraInicio = (SELECT MIN(FechaHoraInicio) FROM reserva WHERE FechaHoraInicio > CURRENT_TIMESTAMP())");
  }
  
  public static PreparedStatement getLatLongParking(Connection con) {
    return getStatement(con, "SELECT Latitud, Longitud FROM parking WHERE IdParking=?");
  }

  public static PreparedStatement setHistorialEntrada(Connection con) {
    return getStatement(con,
        "INSERT INTO HistorialEntrada VALUES(?,?,?);");
  }
 
  public static PreparedStatement setHistorialSalida(Connection con) {
    return getStatement(con,
        "INSERT INTO HistorialSalida VALUES(?,?,?);");
  }
   public static PreparedStatement setHistorialPlaza(Connection con) {
    return getStatement(con,
        "INSERT INTO `Historial plazas` VALUES(?,?,?,?,?);");
  }
  public static PreparedStatement updateHistorialPlaza(Connection con) {

    return getStatement(con, "UPDATE `Historial plazas` SET FechaHoraSalida = ? WHERE FechaHoraEntrada = ? AND IdPlaza = ? ;");
  }
  public static PreparedStatement getEsReservable(Connection con) {
    return getStatement(con, "SELECT EsReservable FROM Plaza WHERE IdPlaza=?;");
  }
  public static PreparedStatement getFechaE(Connection con) {
    return getStatement(con, "SELECT FechaHoraEntrada FROM `Historial plazas` WHERE IdPlaza=? ORDER BY FechaHoraEntrada ASC;");
  }
  public static PreparedStatement updateEstadoCliente(Connection con) {

    return getStatement(con, "UPDATE Cliente SET EstaDentro = ? WHERE NombreDeUsuario = ?;");
  }
  public static PreparedStatement getEstadoCliente(Connection con) {
    return getStatement(con, "SELECT EstaDentro FROM Cliente WHERE NombreDeUsuario = ?;");
  }


  //************** Queries estadísticas ********************/

    /**
    Query para el uso estadistico, devuelve las plazas más comunes
   */
  public static PreparedStatement getPlazasComunes(Connection con) {
    return getStatement(con,
        "SELECT IdPlaza,COUNT(IdPlaza) AS nOcupaciones FROM `historial plazas` WHERE IdParking=? GROUP BY IdPlaza ORDER BY nOcupaciones DESC;");

        
  }

    /**
    Query para el uso estadistico, devuelve el día de la semana con más reservas
   */
  public static PreparedStatement getDiaMasComun(Connection con){
    return getStatement(con,
        "SELECT count(IdPlaza) as recuento,DAYOFWEEK(FechaHoraInicio) as dia from reserva where IdParking =? GROUP BY DAYOFWEEK(FechaHoraInicio) ORDER BY recuento DESC;");
  }

/**
    Query para el uso estadistico, devuelve numero de usuarios registrados en el último mes
   */
  public static PreparedStatement getNUserMonth(Connection con) {
    return getStatement(con,
        "SELECT COUNT(NombreDeUsuario) AS nClientesM FROM Cliente WHERE  FechaDeRegistro BETWEEN CURRENT_TIMESTAMP - interval 1 month AND CURRENT_TIMESTAMP;");
  }
  /**
    Query para el uso estadistico, devuelve numero de usuarios que han entrado al parking en un día determinado
   */
  public static PreparedStatement getNUserParking(Connection con) {
    return getStatement(con,
        "SELECT COUNT(NombreDeUsuario) AS nClientesH FROM HistorialEntrada WHERE IdParking =? FechaEntrada BETWEEN ?  AND ? + interval 1 day;");
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