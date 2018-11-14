/*******************************************
 *
 * Author: James Hood on 10/28/18
 * Modified by Ryan McGuire on 11/13/2018
 * James-created scene and the functionality of being able to change picture.
 * Ryan- changed scene and added update function to the database.
 *
 *******************************************/

package accountsettings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.bind.v2.model.core.ID;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class AccountSettingsController {

  @FXML private ImageView imageView;
  @FXML private JFXTextField changedUsername;
  @FXML private JFXTextField changedEmail;
  @FXML private JFXPasswordField changedpswd;
  @FXML private JFXPasswordField confirmChangePswd;

  private users.Driver userDriver;

  public void initialize() {

    userDriver = new login.LoginController().getUserDriver();
  }

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
   * When this button is pushed, it will open the users files and allow the user to place a picture
   * inside to change the present image.
   * **/
  public void changeImageButtonPushed(ActionEvent actionEvent) throws MalformedURLException {
    FileChooser fileChooser = new FileChooser();
    //Set extension filter
    FileChooser.ExtensionFilter extFilterJPG =
        new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
    FileChooser.ExtensionFilter extFilterjpg =
        new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
    FileChooser.ExtensionFilter extFilterPNG =
        new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
    FileChooser.ExtensionFilter extFilterpng =
        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
    fileChooser.getExtensionFilters()
        .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

    //Show open file dialog
    File file = fileChooser.showOpenDialog(null);
    try {
      BufferedImage bufferedImage = ImageIO.read(file);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      imageView.setImage(image);
    } catch (IOException ex) {
      Logger.getLogger(AccountSettingsController.class.getName()).log( Level.SEVERE, null, ex);
    }
  }

  /***
   * When this button is pushed, the database will be updated.
   * **/
  public void changeButtonPushed(ActionEvent actionEvent) {

    String userName = changedUsername.getText();
    String email = changedEmail.getText();
    String password = changedpswd.getText();
    String confirmPassword = confirmChangePswd.getText();

    //if changed user name text field is not empty.
    if (!(changedUsername.getText().trim().isEmpty())){

      userDriver.setUserName(userName);
    }

    //if changed email text field is not empty.
    if (!(changedEmail.getText().trim().isEmpty())){

      userDriver.setEmail(email);
    }

    //if changed password password field is not empty.
    if (!(changedpswd.getText().trim().isEmpty())){

      userDriver.setPassword(password);
    }

    System.out.println(userDriver.getUserName());
  }

  /***
   * This method will update the database with the information provided by the user.
   * **/
  private void updateDatabase() throws SQLException {

    Connection connection = null;

    try {

      final String databaseURL = "jdbc:derby:C:lib\\carpool";
      connection = DriverManager.getConnection( databaseURL , "ryan", "ryan");

      String sql = "UPDATE USERINFO SET USERNAME=?, PASSWORD=?, EMAIL=? WHERE USERID = ?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, userDriver.getUserName());
      statement.setString(2, userDriver.getPassword());
      statement.setString(3, userDriver.getEmail());
      statement.setInt(4, userDriver.getUserID());

      statement.executeUpdate();
      statement.close();
      connection.close();
    } catch (Exception e ) {

      System.out.println(e);
    }

  }
}
