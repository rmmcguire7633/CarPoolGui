/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/2/2018
 * Used as the controller for the MainMenuDrive.fxml scene.
 * Allows user to schedule a day and time for transportation to destination.
 * Allows user to navigate to the AccountSettings.fxml scene.
 * Edited by Ryan McGuire 11/16/2018- added functionality of adding the
 * users schedule to the database.
 * Edited by Ryan McGuire 11/16/18 - added functionality to populate table from the drive tab with
 * information from the SCHEDULEINFO table.
 *
 *******************************************/

package main;

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
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import login.LoginController;
import newuser.Validator;
import org.controlsfx.control.Rating;
import users.User;

public class MainMenuDriveController {

  //used for displaying user name and rating.
  @FXML private Rating ratingBar;
  @FXML private Label ratingHolder;
  @FXML private Label userLabel;

  @FXML private JFXComboBox pickUp;
  @FXML private JFXComboBox dropOff;

  @FXML private JFXDatePicker calendarDate;

  //used to black out previous date options in the date picker.
  final LocalDate minDate = LocalDate.now();

  @FXML private JFXTimePicker time;

  //table used to see riders who need a ride.
  @FXML private TableView<users.User> table;
  @FXML private TableColumn<users.User, String> usernameCol;
  @FXML private TableColumn<users.User, String> locationCol;
  @FXML private TableColumn<users.User, String> destinationcol;
  @FXML private TableColumn<users.User, String> datecol;
  @FXML private TableColumn<users.User, String> timeCol;

  private static Time timeOf;
  private static Date dayOf;

  //the rider the user clicked on in the table in the drive tab.
  private static users.User person;

  //the user who signed in.
  private static users.User user;

  /**
   * When scene starts, username will be in a label along with their rating.
   * **/
  public void initialize() throws SQLException {

    getStartUp();

    showTable();
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
   * When this method is called, the table SCHEDULEINFO will be searched.
   * This search will return information from the LOCATION column if it is greater than or equal
   * to the current date.
   * @return scheduleInfo - the list containing all information fetched from the USERINFO database.
   **/
  public ObservableList<users.User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    Connection connection;

    final String query = "SELECT * FROM SCHEDULEINFO WHERE DATE >= CURRENT_DATE AND DRIVER IS NULL";
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

    connection = DriverManager.getConnection(
        databasecontroller.DatabaseInfo.getDatabaseUrl(), username, dataPassword);

    statement = connection.prepareStatement(query);

    try {

      ResultSet resultSet = statement.executeQuery();

      getResultSet(scheduleInfo, resultSet);

      statement.close();
      resultSet.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    } finally {

      statement.close();
      connection.close();
    }

    return scheduleInfo;
  }

  /**
   * When this method is called, the table in the driver tab will populate with users who are
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
   * When this method is called, it will create a new row in the table in the drive tab with
   * information from the SCEDHULEINFO table.
   * @param scheduleInfo list used to store information from the SCHEDULEINFO table search.
   * @param resultSet the result set used to populate the scheduleInfo list.
   **/
  public void getResultSet(ObservableList<User> scheduleInfo, ResultSet resultSet)
      throws SQLException {

    while (resultSet.next()) {

      String userName = resultSet.getString("USERNAME");
      String location = resultSet.getString("LOCATION");
      String destination = resultSet.getString("DESTINATION");
      Date day = resultSet.getDate("DATE");
      Time time = resultSet.getTime("TIME");

      scheduleInfo.add(new User(userName, location, destination, day, time));
    }
  }

  /**
  * When this button is pushed, the text fields, date fields, and time fields will be checked
   * to see if they are blank.
   * If they arte not blank, the information in the fields will be pushed to the SCHEDULEINFO table.
  **/
  public void scheduleButtonPushed(ActionEvent actionEvent) throws IOException, SQLException {

    if (!(pickUp.getValue() == null && dropOff.getValue() == null)
        && calendarDate.getValue() != null && time.getValue() != null) {

      user.setLocation((String) pickUp.getValue());
      user.setDestination((String) dropOff.getValue());
      user.setDay(java.sql.Date.valueOf(calendarDate.getValue()));
      user.setTime(java.sql.Time.valueOf(time.getValue()));

      pushToDatabase(user);

      Time timeSelected = java.sql.Time.valueOf(time.getValue());
      Date dateSelected = java.sql.Date.valueOf(calendarDate.getValue());

      setDateTime(dateSelected, timeSelected);

      Validator.successfulBox("Success!", "Successfully Scheduled");

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent backParent = FXMLLoader.load(getClass().getResource("/scheduled/ScheduledAlert.fxml"));

      stage.setScene(new Scene(backParent));
      stage.show();

    } else {

      Validator.errorBox("Field Is Empty", "Please Complete All "
          + "Fields to Schedule");
    }
  }

  /**
   * This method is used to set the staic timeOf and dayOf field.
   * @param dateselected the date the user selects from the pickDate field.
   * @param timeSelected the time the user selects from the picTime field.
   **/
  public static void setDayAndTimeSelected(Date dateselected, Time timeSelected) {

    timeOf = timeSelected;
    dayOf = dateselected;

  }

  /**
   * This method is used to be able to set the static fields,
   * timeOf and dayOf in an instance method.
   * @param day the date the user selects from the pickDate field.
   * @param time the time the user selects from the picTime field.
   **/
  public void setDateTime(Date day, Time time) {

    setDayAndTimeSelected(day, time);
  }

  /**
   * When this method is called, the database table,
   * SCHEDULEINFO will insert into the LOCATION, DESTINATION,
   * DATE, TIME column from the combo box field, pickdate field, and picktime field.
   * @param user the person using the interface.
   **/
  public void pushToDatabase(users.User user) throws SQLException {

    boolean validation = false;

    final String query = "INSERT INTO SCHEDULEINFO (USERNAME, LOCATION, DESTINATION, DATE, TIME ) "
        + "VALUES (?,?,?,?,?)";
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

      String date = user.getDay().toString();
      String time = user.getTime().toString();

      statement.setString(1, user.getUserName());
      statement.setString(2, user.getLocation());
      statement.setString(3, user.getDestination());
      statement.setString(4, date);
      statement.setString(5, time);

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
   * When this button is pushed, the table in the drive tab will reload.
   * */
  public void reloadButtonPushed(ActionEvent actionEvent) throws SQLException {

    showTable();
  }

  /**
   * Gets the time the user entered.
   * @return Time allows the timeOf field to be passed to another class.
   **/
  public Time getTimeOf() {

    return timeOf;
  }

  /**
   * Gets the day the user entered.
   * @return Date allows the date from the pickdate field to be passed to another class.
  **/
  public Date getDayOf() {

    return new Date(dayOf.getTime());
  }

  /**
   * Method used for initial start up of scene.
   * This will set the label to the username.
   * This will disable users from picking a previous date on the date pick field.
   * This will fill in the choices for both combo boxes.
   **/
  public void getStartUp() {

    setUser(new login.LoginController().getUser());
    userLabel.setText(user.getUserName());
    ratingHolder.setText(user.getRating() + "/5");

    // this will make it so user must pick a current date.
    calendarDate.setDayCellFactory(d ->

        new DateCell() {

          @Override public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            setDisable(item.isBefore(minDate));
          }
        });

    pickUp.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    dropOff.getItems().addAll("FGCU Campus", "South Village", "North lake Village",
        " West Lake Village", "Gulf Coast Town Center");

    ratingBar.ratingProperty().setValue(user.getRating());
  }

  /**
   * This method is used to set the static user field.
   * @param users the data from the person using the application.
   **/
  public static void setUser(users.User users) {

    user = users;
  }

  /**
   * This method is used to be able to set the static field user in an instance method.
   * @param user2 the data from the person using the application.
   **/
  public void user(users.User user2) {

    setUser(user2);
  }

  /**
   * This method allows the user's information to be passed to another class.
   * @return users.user the information of the person using the program.
   **/
  public users.User getUser() {

    return user;
  }

  /**
   * When this button is pushed it will sign the user out and send them to the login.LoginScene.fxml
   * scene.
   **/
  public void signOutButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent backParent = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));

    stage.setScene(new Scene(backParent));
    stage.show();
  }

  /**
   * When the user clicks twice on the table in the drive tab, it will create a new instant of the
   * users.user type from the information in the table.
   * This method will allow the driver to confirm to pick up a rider in the table in the drive tab.
   **/
  public void displaySelection(MouseEvent mouseEvent) throws IOException, SQLException {

    if (mouseEvent.getClickCount() == 2) {

      userSelected(table.getSelectionModel().getSelectedItem());


      Boolean userSelection = Validator.confirmationBox("Confirm Schedule",
          "Schedule to pickup " + person.getUserName() + "?");

      // if user presses ok in the dialog box.
      if (userSelection) {

        //pushed the current users USERNAME into the SCHEDULEINFO table DRIVER column.
        scheduleRide(person);

        Stage stage = main.MainLogin.getPrimaryStage();

        Parent parent = FXMLLoader.load(getClass().getResource("/thankyoubox/ThankYouDrive.fxml"));

        stage.setScene(new Scene(parent));
        stage.show();
      }
    }
  }

  public static void setSelectedUser(users.User user) {

    person = user;
  }

  public void userSelected(users.User user2) {

    setSelectedUser(user2);
  }

  /**
   * Allows the info from the table in the drive tab that the user selected to be passed
   * to other classes.
   * @return users.user the information from the selected row from the table in drive tab.
   **/
  public users.User getPerson() {

    return person;
  }

  /**
   * When this method is called, pushed the current users USERNAME into
   * the SCHEDULEINFO table DRIVER column.
   * @param person the rider info that is selected from the table in drive tab.
   **/
  public void scheduleRide(users.User person) throws SQLException {

    final String query = "UPDATE SCHEDULEINFO SET DRIVER=? "
        + "WHERE USERNAME = ? AND DATE=? AND TIME=?";
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

      String date = person.getDay().toString();
      String time = person.getTime().toString();

      statement.setString(1, user.getUserName());
      statement.setString(2, person.getUserName());
      statement.setString(3, date);
      statement.setString(4, time);

      System.out.println(user.getUserName());

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
   * When this button is pushed, it will send the user to the scheduled.EditSchedule.fxml scene.
   **/
  public void editScheduleButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/EditSchedule.fxml"));

    stage.setScene(new Scene(parent));
    stage.show();
  }
}
