package customer.orderapplication;

/**
 * Main class for the Customer ordering GUI.
 * @author Grigor Yordanov
 * @version 1.0
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerOrderMain extends Application {

  /**
   * Main method to launch GUI.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustOrder.fxml"));

    // create controller
    CustomerOrderController controller = new CustomerOrderController(4, 4);

    // set controller to fxml
    loader.setController(controller);

    Parent root = (Parent) loader.load();
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
