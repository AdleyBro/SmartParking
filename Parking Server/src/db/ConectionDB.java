package db;

import javax.sql.*;
import java.sql.*;
import java.util.Calendar;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.Log;
import javax.naming.NamingException;

public class ConectionDB{

    public Connection obtainConnection() throws NullPointerException, ClassNotFoundException,NamingException {
        String usuario = "root";
        String password = "";
        String nombreBBDD = "parking"; 
        Connection con = null;
        //Calendar cal = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ nombreBBDD, usuario, password);
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTime().getTime());
             Log.logdb.debug("Conexión creada, DB Id: %i fecha de obtención: %s ", con.toString(), date.toString());
        } catch (SQLException sqlException) {
        Log.logdb.error("ERROR sql obteniendo la conexión: " + sqlException);
       }
       return con;
    }

    public void closeConnection(Connection con){

        try{
            Log.logdb.info("Cerrando la cone xión. "); 
            if (con != null){
                con.close();
            }

        } catch (SQLException sqlException){
            Log.logdb.error("ERROR sql cerrando la conexión: "+ sqlException); 
        }

    }


    public void closeTransaction(Connection con){

        try{
          con.commit();
          Log.logdb.debug("Transaction closed");
        } catch (SQLException sqlException){
          Log.logdb.error("Error closing the transaction: "+ sqlException);
        }

    }

    public void cancelTransaction(Connection con){

        try{
          con.rollback();
          Log.logdb.debug("Transaction canceled");
        } catch (SQLException sqlException){
          Log.logdb.error("ERROR sql when canceling the transation: "+ sqlException);
        }


    }

    public static PreparedStatement getStatement(Connection con,String sql)
    {
        PreparedStatement ps = null;
        try{
            if (con != null){
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }
        } catch (SQLException sqlException){
    	        Log.logdb.warn("ERROR sql creating PreparedStatement:{} "+ sqlException);
          }

        return ps;
    } 


    //Llamadas a la Base de Datos
    public static PreparedStatement getParking(Connection con){

    	return getStatement(con,"SELECT * FROM parking ");  	
    } 

    public static PreparedStatement getPlazasParking(Connection con){

    	return getStatement(con,"SELECT * FROM plazas WHERE idParking=?");  	
    } 
    public static PreparedStatement getCountPlazasParking(Connection con){

    	return getStatement(con,"SELECT COUNT(IdPlaza) AS nPlazasOcupadas FROM plaza WHERE IdParking=? AND EstaOcupado like '0'");  	
    } 
    public static PreparedStatement updatePlazas(Connection con){

    	return getStatement(con,"UPDATE usuario SET UltimoAccesoParking = ? WHERE NombreDeUsuario = ?;");  	
    } 

    public static PreparedStatement getCountUsuario(Connection con){

    	return getStatement(con,"SELECT count(NombreDeUsuario) as recuento FROM usuario WHERE NombreDeUsuario=? ");  	
    } 
   

    public static PreparedStatement setUsuario(Connection con){
    	return getStatement(con,"INSERT INTO usuario (NombreDeUsuario,Password,UltimoAccesoParking) VALUES (?,?,?);");  	
    }
    public static PreparedStatement setCliente(Connection con){
    	return getStatement(con,"INSERT INTO cliente (IdCliente,Nombre,Email,Tlf,FechaDeRegistro,NombreDeUsuario) VALUES (?,?,?,?,?,?);");  	
    }
    public static PreparedStatement updateUsuarioTS(Connection con){

    	return getStatement(con,"UPDATE usuario SET UltimoAccesoParking = ? WHERE NombreDeUsuario = ?;");  	
    } 

    public static PreparedStatement getPassUsuario(Connection con){

    	return getStatement(con,"SELECT pass FROM usuario WHERE NombreDeUsuario = ?");  	
    }
    public static PreparedStatement getCountIdCliente(Connection con){

    	return getStatement(con,"SELECT count(IdCliente) as recuento FROM cliente");  	
    } 
/*
- Usuarios registrados en ultimo tiempo dado
- Cantidad de plazas ocupadas en un parking en un tiempo dado
- Veces que plaza ha sido ocupada en un último tiempo dado
- Cuantas reservas se han realizado en un parking ultimo tiempo dado
- Reservas que tiene la plaza en un tiempo dado
- Tiempo medio de reserva de una plaza
  **/  
}