package home.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is the starting point of the whole application. It runs the
 * launches the application.
 * 
 * @author Darren
 *
 */
public class MainApplicationStart extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/home/login/LoginPage.fxml"));

    Parent root = (Parent) loader.load();
    stage.setScene(new Scene(root));
    stage.setTitle("Restaurant");
    stage.initStyle(StageStyle.DECORATED);
    stage.show();
  }

}
