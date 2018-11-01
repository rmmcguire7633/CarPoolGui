/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/23/2018
 * Contains the controller for the LoginScene
 *
 *******************************************/

package login;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

  /**
   * When this method is called the scene will change to the Main menu scene.
   ***/
  public void signInButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent signInParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(signInParent));
    stage.show();
  }

  public void newUserButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent newMemberParent = FXMLLoader.load(getClass().getResource("/newuser/NewUser.fxml"));

    stage.setScene(new Scene(newMemberParent));
    stage.show();
  }
}
