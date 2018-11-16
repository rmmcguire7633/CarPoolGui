/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/2/2018
 * Used as the controller for the MainMenu.fxml scene.
 * Allows user to schedule a day and time for transportation to destination.
 * Allows user to navigate to the AccountSettings.fxml scene.
 *
 *******************************************/

package main;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class MainMenuController {

  //used for displaying user name and rating.
  @FXML private Rating ratingBar;
  @FXML private Label ratingHolder;
  @FXML private Label userLabel;

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;

  //table used to see riders who need a ride.
  @FXML private TableView <users.User> table;
  @FXML private TableColumn <users.User, String> usernameCol;
  @FXML private TableColumn <users.User, String> locationCol;
  @FXML private TableColumn <users.User, String> destinationcol;
  @FXML private TableColumn <users.User, String> datecol;
  @FXML private TableColumn <users.User, String> timeCol;

  private users.User user;

  /**
   * When scene starts, username will be in a label along with their rating.
   * **/
  public void initialize() {

    user = new login.LoginController().getUser();
    userLabel.setText(user.getUserName());
    ratingHolder.setText(user.getRating() + "/5");

    pickUp.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    dropOff.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    showTable();

    ratingBar.ratingProperty().setValue(user.getRating());
  }


  public void backMenuPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent backParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

    stage.setScene(new Scene(backParent));
    stage.show();
  }

  public void accountSettingsButtonPushed(ActionEvent actionEvent) throws IOException{
    Stage stage = main.MainLogin.getPrimaryStage();

    Parent settingsParent = FXMLLoader.load( getClass().getResource(
        "/accountsettings/AccountSettings.fxml" ) );
    stage.setScene( new Scene(settingsParent) );
    stage.show();
  }

  public ObservableList<users.User> getRiderInfo () {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();
    scheduleInfo.add(new users.User("John","start","end","11/15/2018","8:00 P.M"));
    scheduleInfo.add(new users.User("Ryan", "start", "end" , "11/30/2018", "10:00A.M"));

    return scheduleInfo;
  }

  public void showTable () {

    usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationcol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    datecol.setCellValueFactory(new PropertyValueFactory<>("day"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

    table.setItems(getRiderInfo());
  }
}
