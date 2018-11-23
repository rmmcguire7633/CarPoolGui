package thankyoubox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import newuser.Validator;
import org.controlsfx.control.Rating;

public class ThankYouRideController {

  @FXML private Rating rating;
  @FXML private Label label;

  private users.User driver = new scheduled.ViewDriverController().getDriver();

  public void initialize() throws SQLException {

    label.setText(driver.getUserName());
  }

  public void submitButtonPushed(ActionEvent actionEvent) throws IOException {

    performRatingCalculation(driver);

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/ViewDriver.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }

  public void performRatingCalculation(users.User person) {

    double rated = rating.getRating();

    person.setRating((rated + person.getRating()) / 2);

    pushAverageRatingToDatabase(person);

    Validator.successfulBox("Success!", "Thank You For Your Feedback");
  }

  public static void pushAverageRatingToDatabase(users.User person) {

    Connection connection;
    PreparedStatement statement;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "UPDATE USERINFO SET RATING=? WHERE USERNAME=?";

      statement = connection.prepareStatement(query);
      statement.setDouble(1, person.getRating());
      statement.setString(2, person.getUserName());

      statement.executeUpdate();

      statement.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    }
  }
}
