package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contains the main method to run the program.
 * Date: 10/23/2018.
 * @author Ryan McGuire
 */
public class MainLogin extends Application {

  private static Stage primaryStage;

  /**
   * Starts the program.
   * @param primaryStage the current primary stage.
   * @throws Exception if program fails.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    setPrimaryStage(primaryStage);

    Parent root = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));
    primaryStage.setTitle("FGCU Carpool App");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * gets the primary scene.
   * @return Stage the primary scene.
   */
  public static Stage getPrimaryStage() {

    return primaryStage;
  }

  /**
   * Sets the primary scene.
   * @param stage the primary scene.
   */
  private void setPrimaryStage(Stage stage) {

    MainLogin.primaryStage = stage;
  }

  /**
   * launches the program.
   * @param args argument.
   * @throws Exception if program fails.
   */
  public static void main(String[] args) throws Exception {

    launch(args);
  }
}
