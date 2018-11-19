/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 11/17/2018
 * When this scene loads, a progress bar will display on the screen.
 * When the progress bar reaches the end it will load backgroundcheck.BackGroundCheckSubmitted.fxml.
 * This is used to simulate waiting for the background check to pass.
 *
 *******************************************/

package backgroundcheck;

import com.jfoenix.controls.JFXProgressBar;
import com.sun.jmx.snmp.tasks.Task;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BackgroundCheckController {

  @FXML JFXProgressBar progressBar;

  users.User user;

  /**
   * When the scene starts, a progress bar will display
   * and after it loads, a new scene will display.
   **/
  public void initialize() {


    doProgressBarStuff();
  }

  /**
   * When this method is called it will perform an animation for the progressbar for 6 seconds.
   **/
  private void doProgressBarStuff() {

    // progress bar goes for 6 seconds.
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
        new KeyFrame(Duration.seconds(6), e -> {

          //changes user to a driver user when the progress bar is finished.
          changeToDriver();

          //changes the scene once the progress bar is done.
          Stage stage = main.MainLogin.getPrimaryStage();

          Parent parent = null;
          try {

            parent = FXMLLoader.load(getClass()
                .getResource("/backgroundcheck/BackGroundCheckSubmitted.fxml"));
          } catch (IOException e1) {

            e1.printStackTrace();
          }

          stage.setScene(new Scene(parent));

          stage.show();
        }, new KeyValue(progressBar.progressProperty(), 1))
    );

    timeline.play();
  }

  /**
   * When this method is called it will update the USERINFO's DRIVER column.
   **/
  private void changeToDriver () {

    user = new login.LoginController().getUser();
    user.setADriver(true);

    Connection connection;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl, "ryan", "ryan");

      String query = "UPDATE USERINFO SET DRIVER=? WHERE USERID=?";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setBoolean(1, user.getIsAdriver());
      statement.setInt(2, user.getUserId());

      statement.executeUpdate();

      statement.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    }
  }
}
