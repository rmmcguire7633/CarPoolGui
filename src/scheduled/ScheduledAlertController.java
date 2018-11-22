/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/17/2018
 * This scene will display how long the user must wait for the driver.
 *
 *******************************************/

package scheduled;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.MainMenuDriveController;

public class ScheduledAlertController {

  private Time timeOf;
  private LocalTime timeNow;
  private Time currentTime;

  static users.User user = new main.MainMenuRideController().getUser();

  @FXML Label timeLabel;

  /**
   * When the scene is loaded, the scene will display how long the user must wait for the
   * driver based on the user's pickup time and current time.
   **/
  public void initialize() throws SQLException {

    timeOf = new MainMenuDriveController().getTimeOf();
    timeNow = LocalTime.now();
    currentTime = Time.valueOf(timeNow);

    long diff = timeOf.getTime() - currentTime.getTime();

    long diffDays = diff / (24 * 60 * 60 * 1000);
    long diffHours = diff / (60 * 60 * 1000) % 24;
    long diffMin = diff / (60 * 1000) % 60;

    timeLabel.setText(String.valueOf("Days: " + diffDays
        + "Hours: " + diffHours + " Minutes: " + diffMin));
  }

  public void continueButtonPushed(ActionEvent actionEvent) throws IOException {

    if (user.getIsAdriver()) {

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent parent = FXMLLoader.load(getClass()
          .getResource("/main/MainMenuDrive.fxml"));

      stage.setScene(new Scene(parent));

      stage.show();
    } else {

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent parent = FXMLLoader.load(getClass()
          .getResource("/main/MainMenuRide.fxml"));

      stage.setScene(new Scene(parent));
    }
  }
}
