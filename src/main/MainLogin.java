/*******************************************
 *
 * @author - Ryan McGuire
 * Date: 10/23/2018
 * Contains the main method
 *
 *******************************************/

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application {

  private static Stage primaryStage;

  @Override
  public void start(Stage primaryStage) throws Exception {

    setPrimaryStage(primaryStage);

    Parent root = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));
    primaryStage.setTitle("FGCU Carpool App");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static Stage getPrimaryStage() {

    return primaryStage;
  }

  private void setPrimaryStage(Stage stage) {

    MainLogin.primaryStage = stage;
  }

  public static void main(String[] args) throws Exception {

    launch(args);
  }
}
