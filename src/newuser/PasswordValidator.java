package newuser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PasswordValidator implements Validator {

  private Pattern pattern;
  private Matcher matcher;

  /**
   * must have at least 1 number character, at least one Capital letter character and be between
   * 6 and 20 characters long.
   */
  private static final String PASSWORD_FORMAT = "((?=.*\\d)(?=.*[A-Z]).{6,20})";

  public PasswordValidator (){

    pattern = Pattern.compile(PASSWORD_FORMAT);
  }

  @Override
  public boolean Validate(final String password) {

    matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
