package schedule;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScheduleController {

  public void mainMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(mainMenuParent));
    stage.show();
  }
}
