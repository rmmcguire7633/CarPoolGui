/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/28/2018
 * Provides the template for comparing user input to correct format. - Ryan 10/28/2018.
 * Contains all methods for creating alert boxes both confirmation and error. -Ryan 11/15/2018.
 *
 *******************************************/

package newuser;

import com.jfoenix.controls.JFXButton;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public interface Validator {

  /**
   * Validates the the input of the user.
   * @param textToBeCompared user input that is being compared.
   * @return true if user input == correct format.
   * */
  boolean validate(final String textToBeCompared);

  /**
   * Displays an error box when called.
   * @param errorMessageTitle the title of the error alert box.
   * @param errorMessageText the text inside the error alert box.
   * */
  static void errorBox(String errorMessageTitle, String errorMessageText) {

    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(errorMessageTitle);
    alert.setHeaderText(null);
    alert.setContentText(errorMessageText);
    alert.showAndWait();
  }

  /**
   * When this method is called a confirmation box will pop up informing
   * the user of an confirmation.
   * @param confirmationTitle the title of the conformation alert box.
   * @param confirmationMessageText the text inside the confirmation alert box.
   **/
  static boolean confirmationBox(String confirmationTitle, String confirmationMessageText) {

    boolean userConfirmed = false;

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(confirmationTitle);
    alert.setHeaderText(null);
    alert.setContentText(confirmationMessageText);

    Optional<ButtonType> action = alert.showAndWait();

    if (action.get() == ButtonType.OK) {

      userConfirmed = true;
    }

    return userConfirmed;
  }

  /**
   * When this method is called a successful outcome is shown to the user.
   * @param successTitle the tile of the success alert box.
   * @param successMessage the text inside the success alert box.
   **/
  static void successfulBox(String successTitle, String successMessage) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(successTitle);
    alert.setHeaderText(null);
    alert.setContentText(successMessage);
    alert.showAndWait();
  }
}
