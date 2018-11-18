/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/17/2018
 * This scene will display how long the user must wait for the driver.
 *
 *******************************************/

package scheduled;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.MainMenuDriveController;

public class ScheduledAlertController {

  private Time timeOf;
  private LocalTime timeNow;
  private Time currentTime;

  @FXML Label timeLabel;

  /**
   * When the scene is loaded, the scene will display how long the user must wait for the
   * driver based on the user's pickup time and current time.
   **/
  public void initialize() throws SQLException {

    timeOf = new MainMenuDriveController().getTimOf();
    timeNow = LocalTime.now();
    currentTime = Time.valueOf(timeNow);

    long diff = timeOf.getTime() - currentTime.getTime();

    long diffDays = diff / (24 * 60 * 60 * 1000);
    long diffHours = diff / (60 * 60 * 1000) % 24;
    long diffMin = diff / (60 * 1000) % 60;

    timeLabel.setText(String.valueOf("Days: " + diffDays
        + "Hours: " + diffHours + " Minutes: " + diffMin));
  }

  public void continueButtonPushed(ActionEvent actionEvent) {
  }
}
