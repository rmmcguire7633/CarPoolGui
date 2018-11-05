package newuser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
}
