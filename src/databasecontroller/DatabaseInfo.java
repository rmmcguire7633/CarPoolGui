package databasecontroller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseInfo {

  private static String DATABASE_URL = "jdbc:derby:C:lib\\carpool";

  public static String getDatabaseUrl() {

    return DATABASE_URL;
  }
}
