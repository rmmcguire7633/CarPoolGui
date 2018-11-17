/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/25/2018
 * Contains the controller for the NewUser scene.
 * This class creates a new row into the USERINFO table from the input provided by the user.
 * Edited by Ryan McGurie 10/30/2018 - created database functionality.
 * Edited by Ryan McGuire 11/14/2018 - added validation method to make sure user
 * has correct format before
 * placing information into database.
 * Edited by Ryan McGuire 11/15/2018 - added alert box informing the user
 * of a successful and unsuccessful
 * input.
 *
 *******************************************/

package newuser;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewUserController implements DataBaseHandler {

  @FXML private JFXPasswordField pswd;
  @FXML private JFXPasswordField pswd2;
  @FXML private JFXTextField username;
  @FXML private JFXTextField email;

  //used to compare valid format with textfield input.
  private PasswordValidator passwordValidator = new PasswordValidator();
  private UserNameValidator userNameValidator = new UserNameValidator();
  private EmailValidator emailValidator = new EmailValidator();

  /**
   * When this button is pushed, user input will be compared with valid format.
   * If user info is in a valid format a new row will be
   * created in the USERINFO table with information
   * from the textfield and the scene will change to the LoginScene.fxml.
   * */
  public void createNewUserButtonPushed(ActionEvent actionEvent) throws IOException {

    //returns true if password is in the correct format.
    Boolean resultPassword = passwordValidator.validate(pswd.getText());

    //creates a string from the pswd text field.
    String password2 = pswd2.getText();

    //returns true if user name is in the correct format.
    Boolean resultUserName = userNameValidator.validate(username.getText());

    //returns true if email address in the correct format.
    Boolean resultEmail = emailValidator.validate(email.getText());

    users.User userRider = new users.User(username.getText(), pswd.getText(), email.getText(),
        false, 3);

    checkValidation(resultUserName, resultEmail, resultPassword, password2,
        new NewUserController(), userRider);
  }

  /**
   * When this button is pushed, the scene will change to LoginScene.fxml.
   * **/
  public void cancelButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent cancelParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

    stage.setScene(new Scene(cancelParent));
    stage.show();
  }

  public void passwordTextField(ActionEvent actionEvent) {
  }

  /**
   * When this method is called it will check the USERINFO table to see if the username in the text
   * field is all ready in use. If the user does not exist in the USERINFO table it will create
   * a new row in the table with the input from pswd textfield,
   * username textfield and email textfield.
   * @param user user type with the data from the text fields.
   * @return returns true if username from textfield is not in the USERINFO table.
   **/
  public boolean checkDatabase(users.User user) {

    Connection connection = null;
    Boolean userNameExist = false;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      System.out.println("connected to database");

      String query = " INSERT INTO userinfo (userName,password,email,driver)"
          + "VALUES (?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(query);

      statement.setString(1, user.getUserName());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.setBoolean(4, false);

      statement.execute();
      userNameExist = true;
      statement.close();
      connection.close();

    } catch (Exception e) {

      System.out.println(e);
    }

    return userNameExist;
  }

  /**
   * When this method is called, it will check all text fields and compare it to valid input.
   * @param resultUserName result of comparing the username text field with valid input.
   * @param resultEmail result of comparing the email text field with valid input.
   * @param resultPassword result of comparing the pswd text field with valid input.
   * @param password2 the re entered password created by the user in the pswd2 text field,
   Used to compare user type password.
   * @param checkDataBase method that performs database actions.
   * @param user the user type created from the text fields.
   **/
  public void checkValidation(boolean resultUserName, boolean resultEmail, boolean resultPassword,
      String password2, DataBaseHandler checkDataBase, users.User user)
      throws IOException {

    //if password has one number is between 6-20 characters long and both
    // password fields are the same and user name is between 6-20 characters long and emil
    // is in the right formatS.
    if (resultPassword && user.getPassword().equals(password2) && resultUserName && resultEmail) {

      boolean userDoesNotExist = checkDataBase.checkDatabase(user);

      if (userDoesNotExist) {

        Validator.successfulBox("Success!", "User Successfully Created!");

        Stage stage = main.MainLogin.getPrimaryStage();

        Parent newUserParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

        stage.setScene(new Scene(newUserParent));
        stage.show();
      } else {

        username.getStyleClass().add("wrong-credentials");

        Validator.errorBox("Username Exist", "The Username all ready"
            + "exist, please re enter a Username");
      }
    } else {

      //if password does not have one number, one capitol letter or is
      // not between 6-20 characters long.
      if (resultPassword == false) {

        pswd.getStyleClass().add("wrong-credentials");

        Validator.errorBox("Incorrect password format",
            "password must contain at least one capital letter, one number and be"
                + "between 6-20 characters long");
      } else if (resultUserName == false) {

        username.getStyleClass().add("wrong-credentials");

        Validator.errorBox("Incorrect user name format",
            "user name must be between 6-20 characters long");
      } else if (resultEmail == false) {

        email.getStyleClass().add("wrong-credentials");

        Validator.errorBox("Incorrect e-mail format", "email "
            + "address must have a @ symbol");
      } else {

        pswd.getStyleClass().add("wrong-credentials");
        pswd2.getStyleClass().add("wrong-credentials");

        Validator.errorBox("Passwords do not match",
            "The passwords do not match, please re enter the Password");
      }
    }
  }
}
