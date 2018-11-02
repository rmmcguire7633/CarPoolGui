/***********************************************************
 * Created by Ryan McGuire 11/01/2018
 * Class created to test the connection to the database.
 *
 ************************************************************/

package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ConnectionClass {


  public static Connection getConnection() throws Exception {

    try {
      final String databaseURL = "jdbc:derby:C:lib\\carpool";

      Connection connection = DriverManager.getConnection( databaseURL , "ryan", "ryan");
      System.out.println("Connected");


      return connection;
    } catch(Exception e){
      System.out.println(e);
    }
    return null;
  }
}
