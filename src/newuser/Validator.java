package newuser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

public interface Validator {
  
  Pattern pattern = null;
  Matcher matcher = null;

  /**
   * Validates the password input based on the regular expression(PASSWORD_FORMAT)
   * @param password password for the validation.
   * @return true if the password is valid, false otherwise.
   * */
  public boolean Validate(final String password);

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
}
