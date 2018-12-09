/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/23/2018
 * This scene accesses the database to check if usernameTextField and password are valid.
 * If the user is valid it will promt the user and change the scene depending on the DRIVER column
 * in the USERINFO table value.
 * If user is not valid, there access will be blocked.
 * This scene also allows the user to navigate to the newuser.NewUser.fxml scene so they can
 * create an account to sign in.
 *
 *******************************************/

package login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newuser.Validator;

public class LoginController {

  @FXML private JFXTextField usernameTextField;

  @FXML private JFXPasswordField password;

  private static users.User user;


  /**
   * When this method is called the scene will change to the Main menu
   * scene if usernameTextField and password is correct.
   ***/
  public void signInButtonPushed(ActionEvent actionEvent) throws IOException, SQLException {

    boolean validSignIn = isValid();

    //If usernameTextField and password is correct.
    if (validSignIn && user.getIsAdriver()) {


      Stage stage = main.MainLogin.getPrimaryStage();

      Parent signInParent = FXMLLoader.load(getClass().getResource("/main/MainMenuDrive.fxml"));

      stage.setScene(new Scene(signInParent));

      stage.show();
    } else if (validSignIn && user.getIsAdriver() == false) {

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent signInParent = FXMLLoader.load(getClass().getResource("/main/MainMenuRide.fxml"));

      stage.setScene(new Scene(signInParent));

      stage.show();
    } else {

      Validator.errorBox("Wrong user Information",
          "The UserName And Password Is Incorrect");
    }
  }

  /**
   * When this method is called, the scene will change to newuser.NewUser.fxml.
   **/
  public void newUserButtonPushed(ActionEvent actionEvent) throws IOException {


    Parent newMemberParent = FXMLLoader.load(getClass().getResource("/newuser/NewUser.fxml"));

    stage.setScene(new Scene(newMemberParent));
    stage.show();
  }

  Stage stage = main.MainLogin.getPrimaryStage();

  /**
   * Searches database for usernameTextField and password and compares it against
   * the textfield and password field.
   * @return true if usernameTextField and password from the database match the textfield.
   * */
  private boolean isValid() throws SQLException {

    boolean validation = false;

    final String query = "SELECT * FROM USERINFO WHERE USERNAME=? AND PASSWORD=?";
    PreparedStatement statement;

    Properties props = new Properties();

    try (FileInputStream in = new FileInputStream("dir/db.properties")) {
      props.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String username = props.getProperty("jdbc.username");
    String dataPassword = props.getProperty("jdbc.password");

    Connection connection = DriverManager.getConnection(
        databasecontroller.DatabaseInfo.getDatabaseUrl(), username, dataPassword);

    statement = connection.prepareStatement(query);

    try {

      statement.setString(1, usernameTextField.getText());
      statement.setString(2, password.getText());

      statement.execute();

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        if (resultSet.getString("USERNAME") != null
            && resultSet.getString("PASSWORD") != null) {

          //gets usernameTextField from database and sets in the driver type.
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

          //creates a new instance of the user who signed in.
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
    } finally {

      statement.close();
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

  /**
   * This method is used to transfer the contents of the user who signed in to other classes.
   * @return users.user the user who signed in.
   **/
  public users.User getUser() {

    return user;
  }
}
