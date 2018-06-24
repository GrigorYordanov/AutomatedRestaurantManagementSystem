
/**
  * The package 'realtimedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view real-time data data of the restaurant.
  */

package manager.realtimedata;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
* The 'RealtimeDataMain' class is used to load the FXML file.
* @author Svetoslav Mihovski
*/
public class RealtimeDataMain extends Application {

  /**
   * Main method to launch GUI.
   */
  public static void main(String[] args) {
    Application.launch(RealtimeDataMain.class, (java.lang.String[])null);
  }

  @Override
  public void start(Stage primaryStage) {
 
    try {
    
      BorderPane page = (BorderPane) FXMLLoader.load(
          RealtimeDataMain.class.getResource("RealtimeData.fxml")); //Loads the adequate FXML file.
      
      Scene scene = new Scene(page);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Realtime Data");
      primaryStage.show();
      
    } catch (Exception exception) {
      Logger.getLogger(RealtimeDataMain.class.getName()).log(Level.SEVERE, null, exception);
    }
  }
    
}