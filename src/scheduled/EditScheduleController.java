package scheduled;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import users.User;

public class EditScheduleController extends main.MainMenuDriveController{

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;

  @FXML private JFXDatePicker calendarDate;
  @FXML private JFXTimePicker time;

  //table used to display what the user has scheduled.
  @FXML private TableView<User> table;
  @FXML private TableColumn<User, String> usernameCol;
  @FXML private TableColumn<users.User, String> locationCol;
  @FXML private TableColumn<users.User, String> destinationcol;
  @FXML private TableColumn<users.User, String> datecol;
  @FXML private TableColumn<users.User, String> timeCol;

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
  public void confirmChangeButtonPushed(ActionEvent actionEvent) {



    if (!(pickUp.getSelectionModel().isEmpty())) {

      user.setLocation((String) pickUp.getValue());
    }

    if (!(dropOff.getSelectionModel().isEmpty())) {

      user.setDestination((String) dropOff.getValue());
    }

    if (calendarDate.getValue() != null) {

      user.setDay(java.sql.Date.valueOf(calendarDate.getValue()));
    }

    if (time.getValue() != null) {

      user.setTime(java.sql.Time.valueOf(time.getValue()));
    }

    System.out.println(person.getLocation());
    System.out.println(user.getLocation());

  }

  /**
   * When the table is clicked, the user will be set to all the values from the table.
   **/
  public void selectedRow(MouseEvent mouseEvent) {


    person = table.getSelectionModel().getSelectedItem();
    user = table.getSelectionModel().getSelectedItem();
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

      String query = "UPDATE USERINFO SET LOCATION=?, DESTINATION=?, DATE=?, TIME=?";

    } catch (Exception e) {

      System.out.println(e);
    }
  }
}
