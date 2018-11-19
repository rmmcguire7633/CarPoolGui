package backgroundcheck;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BackGroundCheckSubmittedController {


  public void maineMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/main/MainMenuDrive.fxml"));

    stage.setScene(new Scene(parent));
    stage.show();
  }
}
