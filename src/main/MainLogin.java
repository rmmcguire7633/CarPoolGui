/*******************************************
 *
 * Author: Ryan McGuire
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
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/login/LoginScene.fxml"));
        primaryStage.setTitle("Carpool Sign In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void setPrimaryStage(Stage stage) {

        MainLogin.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {

        return primaryStage;
    }



    public static void main(String[] args) throws Exception {

        launch(args);
    }
}
