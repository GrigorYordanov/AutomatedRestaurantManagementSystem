
/**
  * The package 'waiterconfirmorder' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the customers can view the menu of the restaurant.
  */

package waiterorder;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
* The 'WaiterMain' class is used to load the FXML file.
* @author Svetoslav Mihovski
*/
public class WaiterMain extends Application {

  /**
  * Main method to launch GUI.
  */
  public static void main(String[] args) {
    Application.launch(WaiterMain.class, (java.lang.String[])null);
  }

  @Override
  public void start(Stage primaryStage) {
 
    try {
    
      BorderPane page = (BorderPane) FXMLLoader.load(
           WaiterMain.class.getResource("Waiter.fxml")); //Loads the adequate FXML file.
      
      Scene scene = new Scene(page);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Waiter GUI");
      primaryStage.show();
      
    } catch (Exception exception) {
      Logger.getLogger(WaiterMain.class.getName()).log(Level.SEVERE, null, exception);
    }
  }
    
}