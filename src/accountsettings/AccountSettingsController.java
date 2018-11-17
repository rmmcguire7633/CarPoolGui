/*******************************************
 *
 * Author: James Hood on 10/28/18
 * Modified by Ryan McGuire
 * James-created scene and the functionality of being able to change picture.
 * Ryan- changed scene and added update function to the database on 11/13/2018.
 * Ryan- Added format check for text fields and alert boxes to let user know of errors and
 * successful input.
 *
 *******************************************/

package accountsettings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import newuser.EmailValidator;
import newuser.PasswordValidator;
import newuser.UserNameValidator;
import newuser.Validator;

public class AccountSettingsController {

  @FXML private ImageView imageView;
  @FXML private JFXTextField username;
  @FXML private JFXTextField email;
  @FXML private JFXPasswordField pswd;
  @FXML private JFXPasswordField pswd2;

  //Used for checking correct formats.
  private PasswordValidator passwordValidator = new PasswordValidator();
  private UserNameValidator userNameValidator = new UserNameValidator();
  private EmailValidator emailValidator = new EmailValidator();

  private users.User user;

  /***
   *  <p>
   * Transfers the users.User type created in login.Logincontroller that holds all the
   * user information from the database.
   * </p>
   * **/
  public void initialize() {

    user = new login.LoginController().getUser();
  }

  /**
   * When this button is pushed, the scene will change to the MainMenu.fxml.
   * **/
  public void backMenuPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent mainParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(mainParent));
    stage.show();
  }

  /**
   * When this button is pushed, the scene will change to the MainMenu scene.
   * **/
  public void mainMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent mainParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(mainParent));
    stage.show();
  }

  /***
   * <p>
   * When this button is pushed, it will open the users files and allow the user to place a picture
   * inside to change the present image.
   * </p>
   * **/
  public void changeImageButtonPushed(ActionEvent actionEvent) throws MalformedURLException {

    FileChooser fileChooser = new FileChooser();
    //Set extension filter
    FileChooser.ExtensionFilter extFilterJpg =
        new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
    FileChooser.ExtensionFilter extFilterjpg =
        new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
    FileChooser.ExtensionFilter extFilterPng =
        new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
    FileChooser.ExtensionFilter extFilterpng =
        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
    fileChooser.getExtensionFilters()
        .addAll(extFilterJpg, extFilterjpg, extFilterPng, extFilterpng);

    //Show open file dialog
    File file = fileChooser.showOpenDialog(null);
    try {
      BufferedImage bufferedImage = ImageIO.read(file);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      imageView.setImage(image);
    } catch (IOException ex) {
      Logger.getLogger(AccountSettingsController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * When this method is called, the USERINFO table's USERNAME,
   * PASSWORD and EMAIL column will be updated.
   * @param user the person using the application.
   * @return returns true if username is not taken.
   **/
  public boolean updateDatabase(users.User user) {

    Connection connection;
    boolean userDoesNotExist = false;

    try {

      final String databaseUrl = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection(databaseUrl,"ryan", "ryan");

      String query = "UPDATE USERINFO SET USERNAME=?, PASSWORD=?, EMAIL=? WHERE USERID = ?";

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, user.getUserName());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.setInt(4, user.getUserId());

      statement.executeUpdate();

      userDoesNotExist = true;

      statement.close();
      connection.close();
    } catch (Exception e) {

      System.out.println(e);
    }

    return userDoesNotExist;
  }

  /**
   * When this button is pushed, the username field is checked for correct format.
   * If username field is in correct format, will allow user to update username in USERINFO table;
   * **/
  public void changeUsernameButton(ActionEvent actionEvent) {

    //if user name text field is not empty.
    if (!(username.getText().trim().isEmpty())) {

      //returns true if user name is formatted correctly.
      Boolean resultUserName = userNameValidator.validate(username.getText());

      Boolean userPressedOk = checkInput(resultUserName, "You Want to Change Your Username?",
          "Username Successfully Changed", username,
          "Incorrect user name format",
          "user name must be between 6-20 characters long");

      if (userPressedOk) {

        user.setUserName(username.getText());
        updateDatabase(user);
      }
    } else {

      Validator.errorBox("Blank Field Detected", "Username Field "
          + "Is  Blank,"
          + "Please Enter A Username If You Would Like To Change Your Username");
    }
  }

  /**
   * When this button is pushed, the email field is checked for correct format.
   * If email field is in correct format, will allow user to update email in USERINFO table;
   * **/
  public void changeEmailButton(ActionEvent actionEvent) {

    //if email text field is not empty.
    if (!(email.getText().trim().isEmpty())) {

      //returns true if email field is formatted correctly.
      Boolean resultEmail = emailValidator.validate(email.getText());

      Boolean userPressedOk = checkInput(resultEmail, "You Want to Change Your Email?",
          "Email Successfully Changed", email,
          "Incorrect e-mail format", "Must Be A Valid Email Address");

      if (userPressedOk) {

        user.setEmail(email.getText());
        updateDatabase(user);
      }
    } else {

      Validator.errorBox("Blank Field Detected",
          "Email Field Is Blank, Please Enter An Email Address If You Would Like"
              + "To Change Your Email Address");
    }
  }

  /**
   * When this button is pushed, the pswd field is checked for correct format and if equals pswd2.
   * If pswd field is in correct format and equals pswd2, will allow
   * user to update password in USERINFO table.
   * **/
  public void changePasswordButton(ActionEvent actionEvent) {

    //if pswd field is not empty and matches pswd2.
    if (!(pswd.getText().trim().isEmpty()) && pswd.getText().equals(pswd2.getText())) {

      //returns true if email field is formatted correctly.
      Boolean resultPassword = passwordValidator.validate(pswd.getText());

      Boolean userPressedok = checkInput(resultPassword, "You Want to Change Your Password?",
          "Password Successfully Changed", pswd,
          "Incorrect Password Format", "password must "
              + "contain at least one capital letter, one number "
              + "and be between 6-20 characters long");

      if (userPressedok) {

        user.setPassword(pswd.getText());
        updateDatabase(user);
      }
    //if pswd field is empty.
    } else if (pswd.getText().trim().isEmpty()) {

      Validator.errorBox("Blank Field Detected",
          "Password Field Is Blank, Please Enter A Password If You Would Like"
              + "To Change Your Password");
    } else {

      Validator.errorBox("Passwords Do Not Match",
          "The Passwords Do Not Match, Please Re Enter The Password");
    }
  }

  /**
   * When this method is called it will populate the correct alert box according to user input.
   * @param isValid if format is correct, then = true.
   * @param message that confirms if user wants to update information.
   * @param successMessage that indicates the database was updated.
   * @param textField highlights the indicated field red when incorrect input is entered.
   * @param incorrectTitle title for Error box.
   * @param incorrectMessage message for the Error box.
   * @return true if user presses ok to confirm change.
   **/
  private boolean checkInput(Boolean isValid, String message, String successMessage,
      TextInputControl textField, String incorrectTitle, String incorrectMessage) {

    boolean userPressedOk = false;

    if (isValid) {

      boolean userConfirmed = Validator.confirmationBox("Confirmation",
          "Are You Sure "
          + message);

      //if user clicks OK to confirm change
      if (userConfirmed) {

        userPressedOk = true;

        Validator.successfulBox("Success!", successMessage);
      }

    } else {

      textField.getStyleClass().add("wrong-credentials");

      Validator.errorBox(incorrectTitle,
          incorrectMessage);
    }

    return userPressedOk;
  }
}
