/*******************************************
 *
 * @author - Ryan McGuire
 * Date: 11/17/2018
 * Same functions as the MainMenuDriveController with no table.
 * Table is replaced with text fields asking user for info so user can be authorized to drive.
 * Inherits the main.MainMenuDriveController class.
 *
 *******************************************/

package main;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newuser.Validator;

public class MainMenuRideController extends MainMenuDriveController {

  @FXML JFXTextField vehicleRegistration;
  @FXML JFXTextField driverLicense;
  @FXML JFXTextField insuranceProvider;
  @FXML JFXTextField insuranceNumber;

  public void initialize() {

    getStartUp();
  }

  /**
   * When this button is pushed it will check to see if the 4 text fields
   * in the drive tab are empty.
   * If one of the fields is empty, an error box will appear.
   * If all text fields are filled, the scene back backgroundcheck.BackGroundCheck.fxml will load.
   **/
  public void submitButtonPushed(Event event) throws IOException {

    if (vehicleRegistration.getText().isEmpty() || driverLicense.getText().isEmpty()
        || insuranceProvider.getText().isEmpty() || insuranceNumber.getText().isEmpty()) {

      Validator.errorBox("Incorrect Info",
          "Please Enter Valid Information");

    } else {

      Stage stage = main.MainLogin.getPrimaryStage();

      Parent signInParent = FXMLLoader.load(getClass()
          .getResource("/backgroundcheck/BackGroundCheck.fxml"));

      stage.setScene(new Scene(signInParent));

      stage.show();
    }
  }
}
