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
        String nombreBBDD = "Parking"; 
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
            // Log.logdb.info("Cerrando la cone xión. "); 
            if (con != null){
                con.close();
            }

        } catch (SQLException sqlException){
            // Log.logdb("ERROR sql cerrando la conexión: " + sqlExcetion); 
        }

    }

}