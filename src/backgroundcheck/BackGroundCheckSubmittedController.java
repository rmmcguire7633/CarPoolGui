/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/17/2018
 * This scene is used to alert the user that they are now able to drive for the carpooling app.
 *
 *******************************************/

package backgroundcheck;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BackGroundCheckSubmittedController {

  /**
   * When this button is pushed, it will change the scene to the main.MainMenuDrive.fxml scene.
   **/
  public void maineMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/main/MainMenuDrive.fxml"));

    stage.setScene(new Scene(parent));
    stage.show();
  }
}
