package scheduled;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import users.User;

public class ViewDriverController {

  private users.User user = new main.MainMenuDriveController().getUser();

  @FXML private TableView<User> table;
  @FXML private TableColumn<users.User, String> driverCol;
  @FXML private TableColumn<users.User, String> ratingCol;
  @FXML private TableColumn<users.User, String> locationCol;
  @FXML private TableColumn<users.User, String> destinationcol;
  @FXML private TableColumn<users.User, String> datecol;
  @FXML private TableColumn<users.User, String> timeCol;

  public void initialize() throws SQLException {

    showTable();
  }

  public ObservableList<User> getRiderInfo() throws SQLException {

    ObservableList<users.User> scheduleInfo = FXCollections.observableArrayList();

    Connection connection;
    PreparedStatement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "SELECT SCHEDULEINFO.DRIVER, USERINFO.RATING, SCHEDULEINFO.LOCATION,"
          + " SCHEDULEINFO.DESTINATION, SCHEDULEINFO.DATE, SCHEDULEINFO.TIME "
          + "From SCHEDULEINFO "
          + "INNER JOIN USERINFO "
          + "ON SCHEDULEINFO.DRIVER = USERINFO.USERNAME WHERE SCHEDULEINFO.DATE >= CURRENT_DATE "
          + "AND SCHEDULEINFO.DRIVER = USERINFO.USERNAME AND SCHEDULEINFO.USERNAME = ?";

      statement = connection.prepareStatement(query);
      statement.setString(1, user.getUserName());

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {

        String driver = resultSet.getString("DRIVER");
        Double rating = resultSet.getDouble("RATING");
        String location = resultSet.getString("LOCATION");
        String destination = resultSet.getString("DESTINATION");
        Date day = resultSet.getDate("DATE");
        Time time = resultSet.getTime("TIME");

        System.out.println(driver);

        scheduleInfo.add(new users.User(driver, rating, location, destination, day, time));
      }

    } catch (Exception e) {

      System.out.println(e);
    }

    return scheduleInfo;
  }

  public void showTable() throws SQLException {

    driverCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
    locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    destinationcol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    datecol.setCellValueFactory(new PropertyValueFactory<>("day"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

    table.setItems(getRiderInfo());
  }

  public void backButtonPushed(ActionEvent actionEvent) {
  }

  public void selectedRow(MouseEvent mouseEvent) {
  }
}
