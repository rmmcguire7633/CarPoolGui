/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/25/2018
 * Contains the controller for the NewUser scene.
 *
 *******************************************/

package newuser;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserController {

  @FXML private TextField pswd;
  @FXML private TextField pswd2;
  @FXML private TextField username;

  private PasswordValidator passwordValidator = new PasswordValidator();

  private UserNameValidator userNameValidator = new UserNameValidator();

  public void createNewUserButtonPushed(ActionEvent actionEvent) throws IOException {

    //returns true if password meets the requirements.
    Boolean resultPassword = passwordValidator.Validate(pswd.getText());
    //used for debugging
    System.out.println(resultPassword);

    String password = pswd.getText();
    String password2 = pswd2.getText();

    //returns true if user name meets the requirements.
    Boolean resultUserName = userNameValidator.Validate(username.getText());

    //if password has one number, one number,is between 6-20 characters long and both
    // pass word fields are the same and usr name is between 6-20 characters long.
    if (resultPassword == true && password.equals(password2) && resultUserName == true) {

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent newUserParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

      stage.setScene(new Scene(newUserParent));
      stage.show();
    }
    else {

      //if password does not have one number, one capitol letter or is not between 6-20 characters long.
      if (resultPassword == false) {

        pswd.getStyleClass().add("wrong-credentials");

        Validator.ErrorBox("Incorrect password format",
            "password must contain at least one capital letter, one number and be"
                + "between 6-20 characters long");
      }
      else if (resultUserName == false) {

        username.getStyleClass().add("wrong-credentials");

        Validator.ErrorBox("Incorrect user name format",
            "user name must be between 6-20 characters long");
      }
      else {

        pswd.getStyleClass().add("wrong-credentials");
        pswd2.getStyleClass().add("wrong-credentials");

        Validator.ErrorBox("Passwords do not match",
            "The passwords do not match, please re enter the Password");
      }
    }
  }

  public void cancelButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent cancelParent = FXMLLoader.load( getClass().getResource( "/login/LoginScene.fxml" ) );

    stage.setScene( new Scene( cancelParent ) );
    stage.show();
  }

  public void passwordTextField(ActionEvent actionEvent) {
  }
}
