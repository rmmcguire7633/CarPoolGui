package accountsettings;

import com.sun.xml.internal.bind.v2.model.core.ID;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
  public void backMenuPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent mainParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(mainParent));
    stage.show();
  }
  public void mainMenuButtonPushed(ActionEvent actionEvent) throws IOException {

    Stage stage = main.MainLogin.getPrimaryStage();

    Parent mainParent = FXMLLoader.load(getClass().getResource("/main/MainMenu.fxml"));

    stage.setScene(new Scene(mainParent));
    stage.show();
  }
  @FXML private ImageView imageView;

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
}
