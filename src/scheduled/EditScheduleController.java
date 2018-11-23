package scheduled;

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
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import users.User;

public class EditScheduleController extends main.MainMenuDriveController{

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;

  @FXML private JFXDatePicker calendarDate;
  @FXML private JFXTimePicker time;

  //table used to display what the user has scheduled.
  @FXML private TableView<User> table;

  private users.User user = new main.MainMenuDriveController().getUser();
  private users.User user2;
  private users.User person;

  public void initialize() throws SQLException {

    pickUp.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    dropOff.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    showTable();
  }

  /**
   * When this button is pushed, the schedule row for the user will update to the indicated change.
   **/
  public void confirmChangeButtonPushed(ActionEvent actionEvent) throws SQLException {

    if (!(pickUp.getSelectionModel().isEmpty())) {

      user.setLocation((String) pickUp.getValue());
    } else {

      user.setLocation(person.getLocation());
    }

    if (!(dropOff.getSelectionModel().isEmpty())) {

      user.setDestination((String) dropOff.getValue());
    } else {

      user.setDestination(person.getDestination());
    }

    if (calendarDate.getValue() != null) {

      user.setDay(java.sql.Date.valueOf(calendarDate.getValue()));
    } else {

      user.setDay(person.getDay());
    }

    if (time.getValue() != null) {

      user.setTime(java.sql.Time.valueOf(time.getValue()));
    } else {

      user.setTime(person.getTime());
    }

    System.out.println(person.getLocation());
    System.out.println(user.getLocation());

    changeScheduleInfo();
    showTable();

  }

  /**
   * When the table is clicked, the user will be set to all the values from the table.
   **/
  public void selectedRow(MouseEvent mouseEvent) {


    person = table.getSelectionModel().getSelectedItem();

    setPerson(person);

    System.out.println(user.getLocation());
    System.out.println(person.getLocation());
  }

  /**
   * When this method is called, it will set the values inside the table.
   **/
  public ObservableList<User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    Connection connection;
    PreparedStatement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "SELECT * FROM SCHEDULEINFO WHERE USERNAME = ?";

      statement = connection.prepareStatement(query);
      statement.setString(1, user.getUserName());

      ResultSet resultSet = statement.executeQuery();

      getResultSet(scheduleInfo, resultSet);
    } catch (Exception e) {

      System.out.println(e);
    }

    return scheduleInfo;
  }

  public void changeScheduleInfo(){

    Connection connection;
    PreparedStatement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "UPDATE SCHEDULEINFO SET LOCATION=?, DESTINATION=?, DATE=?, TIME=?"
          + "WHERE USERNAME=? AND LOCATION=? AND DESTINATION=? AND DATE=? AND TIME=?";

      String date = user.getDay().toString();
      String time = user.getTime().toString();
      String date2 = person.getDay().toString();
      String time2 = person.getTime().toString();

      statement = connection.prepareStatement(query);
      statement.setString(1, user.getLocation());
      statement.setString(2, user.getDestination());
      statement.setString(3, date);
      statement.setString(4, time);
      statement.setString(5, person.getUserName());
      statement.setString(6, person.getLocation());
      statement.setString(7, person.getDestination());
      statement.setString(8, date2);
      statement.setString(9, time2);

      statement.executeUpdate();
      statement.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    }
  }

  public void setPerson (users.User person){

    this.person=person;
  }

  public void mainMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    accountsettings.AccountSettingsController changeScene = new accountsettings.AccountSettingsController();
    changeScene.getDriverOrRiderScene(user);
  }

  public void viewDriverButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/ViewDriver.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }
}
