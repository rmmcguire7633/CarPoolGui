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
  boolean Validate(final String textToBeCompared);

  /**
   * Displays an error box when called.
   * @param errorMessageTitle the title of the error alert box.
   * @param errorMessageText the text inside the error alert box.
   * */
  static void ErrorBox(String errorMessageTitle, String errorMessageText) {

    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(errorMessageTitle);
    alert.setHeaderText(null);
    alert.setContentText(errorMessageText);
    alert.showAndWait();
  }

  static boolean ConfirmationBox(String confirmationTitle, String confirmationMessageText) {

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

  static void SuccessfulBox (String successTitle, String successMessage) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(successTitle);
    alert.setHeaderText(null);
    alert.setContentText(successMessage);
    alert.showAndWait();
  }
}
