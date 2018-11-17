/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/23/2018
 * Contains the controller for the LoginScene
 *
 *******************************************/

package login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

  @FXML private JFXTextField username;

  @FXML private JFXPasswordField password;

  private static users.User user;


  /**
   * When this method is called the scene will change to the Main menu scene if username and
   * password is correct.
   ***/
  public void signInButtonPushed(ActionEvent actionEvent) throws IOException, SQLException {

    //If username and password is correct.
    if (isValid()) {


      Stage stage = main.MainLogin.getPrimaryStage();

      Parent signInParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

      stage.setScene(new Scene(signInParent));

      stage.show();
    }
  }

  /**
   * When this method is called, the scene will change to NewUser.fxml.
   **/
  public void newUserButtonPushed(ActionEvent actionEvent) throws IOException {


    Parent newMemberParent = FXMLLoader.load(getClass().getResource("/newuser/NewUser.fxml"));

    stage.setScene(new Scene(newMemberParent));
    stage.show();
  }

  Stage stage = main.MainLogin.getPrimaryStage();

  /**
   * Searches database for username and password and compares it against
   * the textfield and password field.
   * @return true if username and password from the database match the textfield.
   * */
  private boolean isValid() throws SQLException {

    boolean validation = false;

    Connection connection = null;
    Statement statement = null;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      System.out.println("connected to database");

      statement = connection.createStatement();

      ResultSet resultSet = statement.executeQuery("SELECT * FROM USERINFO WHERE USERNAME= "
          + "'" + username.getText() + "'"
          + " AND PASSWORD= " + "'" + password.getText() + "'");

      while (resultSet.next()) {
        if (resultSet.getString("USERNAME") != null
            && resultSet.getString("PASSWORD") != null) {

          //gets username from database and sets in the driver type.
          String userName = resultSet.getString("USERNAME");

          //gets password from database and sets in the driver type.
          String password = resultSet.getString("PASSWORD");

          //gets email from the database and sets in the driver type.
          String email = resultSet.getString("EMAIL");

          //gets id from the database and sets in the driver type.
          int userId = resultSet.getInt("USERID");

          //ses if the userInfo is a driver.
          boolean isADriver = resultSet.getBoolean("DRIVER");

          //gets rating of the userInfo form the database.
          Double rating = resultSet.getDouble("RATING");

          user = new users.User(userId, userName, password, email, isADriver, rating);
          setUser(user);

          validation = true;
        }
      }

      resultSet.close();
      statement.close();
      connection.close();

    } catch (Exception e) {

      System.out.println(e);
    }

    return validation;
  }

  /**
   * Initializes the userInfo.Driver type.
   * @param userInfo the type to be initialize.
   **/
  public static void setUser(users.User userInfo) {

    user = userInfo;
  }

  public users.User getUser() {

    return user;
  }
}
