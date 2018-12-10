/*******************************************
 *
 * @author - Ryan McGuire on 11/21/18
 * Populates a table with the users scheduled rides from the SCHEDULEINFO table.
 * Allows user to edit the columns LOCATION, DESTINATION, DATE,
 * and TIME from the SCHEDULEINFO table.
 * Allows the user to navigate to the scheduled.ViewDriver.fxml scene.
 *
 *******************************************/

package scheduled;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Properties;
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

public class EditScheduleController extends main.MainMenuDriveController {

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;

  @FXML private JFXDatePicker calendarDate;
  @FXML private JFXTimePicker time;

  //table used to display what the user has scheduled.
  @FXML private TableView<User> table;

  // the information on the user passed from the main.MainMenuDriveController class.
  private users.User user = new main.MainMenuDriveController().getUser();

  // the user information created by clicking on the table.
  private users.User person;

  /**
   * When the scene first loads, it will populate the combo boxes and place information
   * pulled form the SCHEDULEINFO table into the table view.
   **/
  public void initialize() throws SQLException {

    pickUp.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    dropOff.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    showTable();
  }

  /**
   * When this button is pushed, the information from the SCHEDULEINFO tables columns LOCATION,
   * DESTINATION, DATE, and TIME will update to what the user has placed in the text fields.
   *
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

      LocalDate date = java.sql.Date.valueOf(calendarDate.getValue()).toLocalDate();
      user.setDay(date);
    } else {

      user.setDay(person.getDay());
    }

    if (time.getValue() != null) {

      user.setTime(java.sql.Time.valueOf(time.getValue()));
    } else {

      user.setTime(person.getTime());
    }

    changeScheduleInfo();
    showTable();
  }

  /**
   * When the table is clicked, the person field will be set to all the values from the table.
   **/
  public void selectedRow(MouseEvent mouseEvent) {


    person = table.getSelectionModel().getSelectedItem();

    setPerson(person);
  }

  /**
   * When this method is called, it will set the values inside the table.
   * The values are pulled from the SCHEDULEINFO table columns LOCATION, DESTINATION,
   * DATE and TIME.
   **/
  public ObservableList<User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    final String query = "SELECT * FROM SCHEDULEINFO WHERE USERNAME = ? AND DATE >= CURRENT_DATE";
    PreparedStatement statement;

    Properties props = new Properties();

    try (FileInputStream in = new FileInputStream("dir/db.properties")) {
      props.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String username = props.getProperty("jdbc.username");
    String dataPassword = props.getProperty("jdbc.password");

    Connection connection = DriverManager.getConnection(
        databasecontroller.DatabaseInfo.getDatabaseUrl(), username, dataPassword);

    statement = connection.prepareStatement(query);

    try {

      statement.setString(1, user.getUserName());

      ResultSet resultSet = statement.executeQuery();

      //creates result set to store information pulled from the SCHEDULEINFO table into the list.
      getResultSet(scheduleInfo, resultSet);
    } catch (Exception e) {

      System.out.println(e);
    } finally {

      statement.close();
      connection.close();
    }

    return scheduleInfo;
  }

  /**
   * When this method is called, the SCHEDULEINFO table's columns LOCATION, DESTINATION, DATE and
   * TIME will be updated with the information with in the combobox, pickdate, and picktime.
   * The selection that will be updated is the row the user has active on the table view.
   **/
  public void changeScheduleInfo() throws SQLException {

    final String query = "UPDATE SCHEDULEINFO SET LOCATION=?, DESTINATION=?, DATE=?, TIME=? "
        + "WHERE USERNAME=? AND LOCATION=? AND DESTINATION=? AND DATE=? AND TIME=?";
    PreparedStatement statement;

    Properties props = new Properties();

    try (FileInputStream in = new FileInputStream("dir/db.properties")) {
      props.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String username = props.getProperty("jdbc.username");
    String dataPassword = props.getProperty("jdbc.password");

    Connection connection = DriverManager.getConnection(
        databasecontroller.DatabaseInfo.getDatabaseUrl(), username, dataPassword);

    statement = connection.prepareStatement(query);

    try {

      final String date = user.getDay().toString();
      final String time = user.getTime().toString();
      final String date2 = person.getDay().toString();
      final String time2 = person.getTime().toString();

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
    } finally {

      statement.close();
      connection.close();
    }
  }

  /**
   * Used to set the information from the selected row to the person field.
   **/
  public void setPerson(users.User person) {

    this.person = person;
  }

  /**
   * When this button is pushed, the scene will change to the main.MainMenuDriveControler.fxml
   * if the boolean value (isAdriver) is true, or main.MainMenuRideController.fxml if the value is
   * false.
   **/
  public void mainMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    accountsettings.AccountSettingsController changeScene =
        new accountsettings.AccountSettingsController();
    changeScene.getDriverOrRiderScene(user);
  }

  /**
   * When this button is pushed, the scene will change to the scheduled.ViewDriver.fxml scene.
   **/
  public void viewDriverButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/ViewDriver.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }
}
