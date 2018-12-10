package databasecontroller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This class holds the database url.
 * Date: 12/08/2018.
 * @author Ryan McGuire
 */
public class DatabaseInfo {

  private static String DATABASE_URL = "jdbc:derby:C:lib\\carpool";

  /**
   * This method is used to get the database url.
   * @return the database url.
   */
  public static String getDatabaseUrl() {

    return DATABASE_URL;
  }
}
