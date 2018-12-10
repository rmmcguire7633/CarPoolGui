package scheduled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import newuser.Validator;
import users.User;

/**
 * This scene will display a table that contains information from both SCHEDULEINFO and
 * USERINFO table.
 * SCHEDULEINFO columns displayed - DRIVER, LOCATION, DESTINATION, DATE, TIME.
 * USERINFO columns displayed - RATING.
 * The user will be able to double click the row and confirm the Driver who is picking them up and
 * afterwords will allow the user to rate the driver.
 * Date 11/17/2018
 * @author Ryan McGuire
 */
public class ViewDriverController {

  //the current user
  private users.User user = new main.MainMenuDriveController().getUser();

  //the user type created by the row selection of the table.
  static users.User driver;

  //all the table view fields.
  @FXML private TableView<User> table;
  @FXML private TableColumn<users.User, String> driverCol;
  @FXML private TableColumn<users.User, String> ratingCol;
  @FXML private TableColumn<users.User, String> locationCol;
  @FXML private TableColumn<users.User, String> destinationcol;
  @FXML private TableColumn<users.User, String> datecol;
  @FXML private TableColumn<users.User, String> timeCol;

  /**
   * When the scene first loads, it will display the table the holds information from the
   * SCHEDULEINFO table column's DRIVER, LOCATION, DESTINATION, DATE, TIME and the information
   * from the USERINFO table column RATING.
   * @throws SQLException the SQL exception.
   */
  public void initialize() throws SQLException {

    showTable();
  }

  /**
   * When this method is called, it will get the information from the
   * SCHEDULEINFO table column's DRIVER, LOCATION, DESTINATION, DATE, TIME and the information
   * based on the users username USERNAME column form the SCHEDULEINFO table and
   * from the USERINFO table column RATING based on the DRIVER column in the SCHEDULEINFO table.
   * @return ObservableList the list created from the contents of the database.
   * @throws SQLException the SQL exception.
   */
  public ObservableList<User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    final String query = "SELECT SCHEDULEINFO.DRIVER, USERINFO.RATING, SCHEDULEINFO.LOCATION, "
        + "SCHEDULEINFO.DESTINATION, SCHEDULEINFO.DATE, SCHEDULEINFO.TIME "
        + "From SCHEDULEINFO INNER JOIN USERINFO ON SCHEDULEINFO.DRIVER = USERINFO.USERNAME "
        + "WHERE SCHEDULEINFO.DATE >= CURRENT_DATE "
        + "AND SCHEDULEINFO.DRIVER = USERINFO.USERNAME AND SCHEDULEINFO.USERNAME = ?";
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

      while (resultSet.next()) {

        String driver = resultSet.getString("DRIVER");
        Double rating = resultSet.getDouble("RATING");
        String location = resultSet.getString("LOCATION");
        String destination = resultSet.getString("DESTINATION");
        LocalDate day = resultSet.getDate("DATE").toLocalDate();
        Time time = resultSet.getTime("TIME");

        scheduleInfo.add(new users.User(driver, rating, location, destination, day, time));
      }

      resultSet.close();
      statement.close();
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
   * When this method is called, the table will display with the designated contents.
   * @throws SQLException the SQL exception.
   */
  public void showTable() throws SQLException {

    driverCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
    locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationcol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    datecol.setCellValueFactory(new PropertyValueFactory<>("day"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

    table.setItems(getRiderInfo());
  }

  /**
   * When this button is pushed, the scene will change to the scheduled.EditSchedule.fxml scene.
   * @throws IOException the IO exception.
   */
  public void backButtonPushed() throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/EditSchedule.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }

  /**
   * When the table is double clicked, the selected row will set the value for the driver field.
   * This method will also display a confirmation box for the user, if the user clicks ok then
   * the scene will change to thankyou.ThankYouRide.fxml scene.
   * @param mouseEvent the selected row of the table view.
   * @throws SQLException the SQL exception.
   * @throws IOException the IO exception.
   */
  public void selectedRow(MouseEvent mouseEvent) throws SQLException, IOException {

    if (mouseEvent.getClickCount() == 2) {

      setDriver(table.getSelectionModel().getSelectedItem());

      boolean userPressedOk = Validator.confirmationBox("Confirmation",
          "Would you like "
          + driver.getUserName() + " to pick you up?");

      if (userPressedOk) {

        clearRow();

        Stage stage = main.MainLogin.getPrimaryStage();

        Parent parent = FXMLLoader.load(getClass().getResource("/thankyoubox/ThankYouRide.fxml"));

        stage.setScene(new Scene(parent));

        stage.show();
      }
    }
  }

  /**
   * This method is used to set the staic driver field.
   * @param user the selected driver type from the table.
   */
  public static void setStaticDriver(users.User user) {

    driver = user;
  }

  /**
   * This method is used to be able to set the static fields, driver in an instance method.
   * @param user the selected driver type from the table.
   */
  public void setDriver(users.User user) {

    setStaticDriver(user);
  }

  /**
   * When this method is called, it will delete the selected info from the tableview and the
   * information from that row will delete the row in SCHEDULEINFO.
   * @throws SQLException the SQL exception.
   */
  public void clearRow() throws SQLException {

    final String query = "DELETE FROM SCHEDULEINFO WHERE USERNAME=? AND LOCATION=? "
        + "AND DESTINATION=? AND DATE=? AND TIME=? AND DRIVER=?";
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

      String date = driver.getDay().toString();
      String time = driver.getTime().toString();

      statement.setString(1, user.getUserName());
      statement.setString(2, driver.getLocation());
      statement.setString(3, driver.getDestination());
      statement.setString(4, date);
      statement.setString(5, time);
      statement.setString(6, driver.getUserName());

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
   * This method allows the user type driver to be passed to another class.
   * @return the user type from the row selected by the user.
   */
  public users.User getDriver() {

    return driver;
  }
}
