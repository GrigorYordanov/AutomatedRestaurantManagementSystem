 
/**
  * The package 'employeedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view employee data of the restaurant.
  */

package manager.employeedata;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
* The 'EmployeeDataMain' class is used to load the FXML file.
* @author Svetoslav Mihovski
*/
public class EmployeeDataMain extends Application {

  /**
  * Main method to launch GUI.
  */
  public static void main(String[] args) {
    Application.launch(EmployeeDataMain.class, (java.lang.String[])null);
  }

  @Override
  public void start(Stage primaryStage) {

    try {
    
      BorderPane page = (BorderPane) FXMLLoader.load(
          EmployeeDataMain.class.getResource("EmployeeData.fxml"));//Loads the adequate FXML file.

      Scene scene = new Scene(page);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Employee Data");
      primaryStage.show();
      
    } catch (Exception exception) {
      Logger.getLogger(EmployeeDataMain.class.getName()).log(Level.SEVERE, null, exception);
    }
  }
    
}