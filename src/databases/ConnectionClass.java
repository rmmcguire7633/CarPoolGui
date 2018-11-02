package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionClass {


  public static Connection getConnection() throws Exception {

    try {
      final String databaseURL = "jdbc:derby:C:lib\\carpool";

      Connection connection = DriverManager.getConnection( databaseURL , "ryan", "ryan");
      System.out.println("Connected");

      //  return connection;
    } catch(Exception e){
      System.out.println(e);
    }

    return null;
  }
}
