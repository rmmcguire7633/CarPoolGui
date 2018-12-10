/*******************************************
 *
 * @author - Ryan McGuire on 11/21/18
 * This scene allows the user to rate the the driver they picked from the tableview in
 * scheduled.ViewDriverController.
 *
 *******************************************/

package thankyoubox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
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

  // the type created from the selected table view row in scheduled.ViewDriverController.
  private users.User driver = new scheduled.ViewDriverController().getDriver();

  /**
   * When the scene is first loaded, the driver username will populate the label field.
   **/
  public void initialize() throws SQLException {

    label.setText(driver.getUserName());
  }

  /**
   * When this button is pushed, it will add the new calculated rating for the driver into the
   * RATING column USERINFO table.
   * This method will also change the scene to scheduled.ViewDriver.fxml.
   **/
  public void submitButtonPushed(ActionEvent actionEvent) throws IOException, SQLException {

    performRatingCalculation(driver);

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent parent = FXMLLoader.load(getClass().getResource("/scheduled/ViewDriver.fxml"));

    stage.setScene(new Scene(parent));

    stage.show();
  }

  /**
   * When this method is called, it will add the new calculated rating for the driver into the
   * RATING column USERINFO table.
   * This method will also display a confirmation dialogue box to thank the user.
   * @param person the type created from the row selection in the table view located in
   *      scheduled.ViewDriverController.
   **/
  public void performRatingCalculation(users.User person) throws SQLException {

    double rated = rating.getRating();

    person.setRating((rated + person.getRating()) / 2);

    pushAverageRatingToDatabase(person);

    Validator.successfulBox("Success!", "Thank You For Your Feedback");
  }

  /**
   * When this method is called, it will add the new calculated user rating for the driver to the
   * RATING column in the USERINFO table.
   * @param person the the type created from the row selection in the table view located in
   *        scheduled.ViewDriverController.
   **/
  public static void pushAverageRatingToDatabase(users.User person) throws SQLException {

    final String query = "UPDATE USERINFO SET RATING=? WHERE USERNAME=?";
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

      statement.setDouble(1, person.getRating());
      statement.setString(2, person.getUserName());

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
}
