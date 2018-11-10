package main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainMenuController {

  private users.Driver userDriver;

  @FXML private Label userLabel;

  public void initialize() {

    userLabel.setText(userDriver.getUserName());
  }


  public void backMenuPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent backParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

    stage.setScene(new Scene(backParent));
    stage.show();
  }

  public void scheduleRiderButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent scheduleParent = FXMLLoader.load(getClass().getResource("/schedule/Schedule.fxml"));

    stage.setScene(new Scene(scheduleParent));
    stage.show();
  }
  public void accountSettingsButtonPushed(ActionEvent actionEvent) throws IOException{
    Stage stage = main.MainLogin.getPrimaryStage();

    Parent settingsParent = FXMLLoader.load( getClass().getResource(
        "/accountsettings/AccountSettings.fxml" ) );
    stage.setScene( new Scene(settingsParent) );
    stage.show();
  }

  public void setUserDriver (users.Driver user) {

    this.userDriver = user;
  }
}
