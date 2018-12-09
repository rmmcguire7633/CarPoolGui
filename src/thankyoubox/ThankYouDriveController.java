/*******************************************
 *
 * Author: Ryan McGuire on 11/21/18
 * This scene allows the user to rate the the rider they picked from the tableview in
 * main.MainMenuDriveController in the drive tab.
 *
 *******************************************/

package thankyoubox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class ThankYouDriveController {

  @FXML private Rating rating;
  @FXML private Label label;

  // the type created from the selected row from main.MainMenuDriveController tableview.
  private users.User rider = new main.MainMenuDriveController().getPerson();

  /**
   * When the scene first loads, the label will display the selected rider's username.
   **/
  public void initialize() {

    label.setText(rider.getUserName());
  }

  /**
   * When this button is pushed, the average riders's rating will be updated with the selected
   * rating.
   **/
  public void submitButtonPushed(ActionEvent actionEvent) throws IOException, SQLException {

    getRiderRating();

    Double rated = rating.getRating();

    rider.setRating((rated + rider.getRating()) / 2);

    //updates the RATING column in USERINFO table with the new rating.
    ThankYouRideController.pushAverageRatingToDatabase(rider);

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/main/MainMenuDrive.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }

  /**
   * When this method is called, it will get the RATING of the rider's USERNAME from the
   * USERINFO table.
   **/
  public void getRiderRating() throws SQLException {

    final String query = "SELECT RATING FROM USERINFO WHERE USERNAME=?";
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

      statement.setString(1, rider.getUserName());

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {

        Double riderRating = resultSet.getDouble("RATING");
        rider.setRating(riderRating);
      }


    } catch (Exception e) {

      System.out.println(e);
    } finally {

      statement.close();
      connection.close();
    }
  }
}
