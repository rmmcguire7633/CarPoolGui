/*******************************************
 *
 * @author - Ryan McGuire
 * Date: 11/17/2018
 * This scene will display how long the user must wait for the driver based
 * on the current day and time.
 *
 *******************************************/

package scheduled;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
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

  private Date dayOf;
  private LocalDate dateNow;
  private Date currentDay;

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

    LocalDate date = new MainMenuDriveController().getDayOf();

    dayOf = java.sql.Date.valueOf(date);
    dateNow = LocalDate.now();
    currentDay = Date.valueOf(dateNow);

    long diffTime = timeOf.getTime() - currentTime.getTime();
    long diffDay = dayOf.getTime() - currentDay.getTime();

    long diffDays = diffDay / (24 * 60 * 60 * 1000);
    long diffHours = diffTime / (60 * 60 * 1000) % 24;
    long diffMin = diffTime / (60 * 1000) % 60;

    if (diffHours < 0) {

      diffHours *= -1;
    }

    if (diffMin < 0) {

      diffMin *= -1;
    }

    timeLabel.setText(String.valueOf("Days: " + diffDays
        + " Hours: " + diffHours + " Minutes: " + diffMin));
  }

  /**
   * When this button is pushed, the scene will change to the main.MainMenuDrive.fxml (true)
   * scene or the main.MainMenuRide.fxml (false) scene based on the users isAdriver boolean value.
   **/
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
