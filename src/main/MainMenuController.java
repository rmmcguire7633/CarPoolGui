/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/2/2018
 * Used as the controller for the MainMenu.fxml scene.
 * Allows user to schedule a day and time for transportation to destination.
 * Allows user to navigate to the AccountSettings.fxml scene.
 * Edited by Ryan McGuire 11/16/2018- added functionality of adding the
 * users schedule to the database.
 * Edited by Ryan McGuire 11/16/18 - added functionality to populate table from the drive tab with
 * information from the database.
 *
 *******************************************/

package main;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
import newuser.Validator;
import org.controlsfx.control.Rating;

public class MainMenuController {

  //used for displaying user name and rating.
  @FXML private Rating ratingBar;
  @FXML private Label ratingHolder;
  @FXML private Label userLabel;

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;
  @FXML private JFXDatePicker calendarDate;
  @FXML private JFXTimePicker time;

  //table used to see riders who need a ride.
  @FXML private TableView<users.User> table;
  @FXML private TableColumn<users.User, String> usernameCol;
  @FXML private TableColumn<users.User, String> locationCol;
  @FXML private TableColumn<users.User, String> destinationcol;
  @FXML private TableColumn<users.User, String> datecol;
  @FXML private TableColumn<users.User, String> timeCol;

  private users.User user;

  /**
   * When scene starts, username will be in a label along with their rating.
   * **/
  public void initialize() throws SQLException {

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

  /**
   * When this button is pushed, the scene will change to the login.LoginScene.Fxml.
   **/
  public void backMenuPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent backParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

    stage.setScene(new Scene(backParent));
    stage.show();
  }

  /**
   * When this button is pushed, the scene will change to accountsettings.AccountSettings.fxml.
   **/
  public void accountSettingsButtonPushed(ActionEvent actionEvent) throws IOException {
    Stage stage = main.MainLogin.getPrimaryStage();

    Parent settingsParent = FXMLLoader.load(getClass().getResource(
        "/accountsettings/AccountSettings.fxml"));
    stage.setScene(new Scene(settingsParent));
    stage.show();
  }

  /**
   * When this method is called, the database USERINFO will be searched.
   * This search will return information from the LOCATION column if it is not null.
   * @return scheduleInfo - the list containing all information fetched from the USERINFO database.
   **/
  public ObservableList<users.User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    Connection connection;
    Statement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "SELECT * FROM USERINFO WHERE LOCATION IS NOT NULL";

      statement = connection.createStatement();

      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {

        String userName = resultSet.getString("USERNAME");
        String location = resultSet.getString("LOCATION");
        String destination = resultSet.getString("DESTINATION");
        Date day = resultSet.getDate("DATE");
        Time time = resultSet.getTime("TIME");

        scheduleInfo.add(new users.User(userName, location, destination, day, time));
      }
    } catch (Exception e) {

      System.out.println(e);
    }

    return scheduleInfo;
  }

  /**
   * When this method is called, the table in thje driver tab will populate with users who are
   * looking to carpool.
   * **/
  public void showTable() throws SQLException {

    usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationcol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    datecol.setCellValueFactory(new PropertyValueFactory<>("day"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

    table.setItems(getRiderInfo());
  }

  /**
  * When this button is pushed, the text fields, date fields, and time fields will be checked
   * to see if theya re blank.
   * If they arte not blank, the information in the fields be pushed to the USERINFO table.
  **/
  public void scheduleButtonPushed(ActionEvent actionEvent) {

    if (!(pickUp.getValue() == null && dropOff.getValue() == null)
        && calendarDate.getValue() != null && time.getValue() != null) {

      user.setLocation((String) pickUp.getValue());
      user.setDestination((String) dropOff.getValue());
      user.setDay(java.sql.Date.valueOf(calendarDate.getValue()));
      user.setTime(java.sql.Time.valueOf(time.getValue()));

      pushToDatabase(user);

      Validator.successfulBox("Success!", "Successfully Scheduled");
    } else {

      Validator.errorBox("Field Is Empty", "Please Complete All"
          + "Fields to Schedule");
    }

  }

  /**
   * When this method is called, the database table, USERINFO will update the LOCATION, DESTINATION,
   * DATE, TIME column.
   * @param user the person using the interface.
   **/
  public void pushToDatabase(users.User user) {

    Connection connection;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "UPDATE USERINFO SET LOCATION=?, DESTINATION=?, DATE=?, TIME=?"
          + "WHERE USERNAME=?";
      String date = user.getDay().toString();
      String time = user.getTime().toString();

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, user.getLocation());
      statement.setString(2, user.getDestination());
      statement.setString(3, date);
      statement.setString(4, time);
      statement.setString(5, user.getUserName());

      statement.executeUpdate();

      statement.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    }
  }

  /**
   * When this button is pushed, the table in the drive tab will reload.
   * */
  public void reloadButtonPushed(ActionEvent actionEvent) throws SQLException {

    showTable();
  }
}
