package thankyoubox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

  private users.User rider = new main.MainMenuDriveController().getPerson();

  public void initialize() {

    label.setText(rider.getUserName());
  }

  public void submitButtonPushed(ActionEvent actionEvent) throws IOException {

    getRiderRating();

    Double rated = rating.getRating();

    rider.setRating((rated + rider.getRating()) / 2);

    ThankYouRideController.pushAverageRatingToDatabase(rider);

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/main/MainMenuDrive.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }

  public void getRiderRating () {

    Connection connection;
    PreparedStatement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "SELECT RATING FROM USERINFO WHERE USERNAME=?";

      statement = connection.prepareStatement(query);
      statement.setString(1, rider.getUserName());

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {

        Double riderRating = resultSet.getDouble("RATING");
        rider.setRating(riderRating);
      }


    } catch (Exception e) {


    }
  }
}
