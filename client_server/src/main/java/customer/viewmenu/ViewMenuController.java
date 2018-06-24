
/**
  * The package 'viewmenu' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the customers can view the menu of the restaurant.
  */

package customer.viewmenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Navigation;

/**
 * The class 'ViewMenuController' is used to fill the FXML file with data
 * from the 'ViewMenuDishes' class.
 * @author Svetoslav Mihovski
 */
public class ViewMenuController
    implements Initializable {
  
  ScheduledExecutorService scheduledExecutorService;

  //Injecting values from FXML loader

  @FXML private TableView<ViewMenuDishes> menuTable;
  @FXML private TableColumn<ViewMenuDishes,Image> imageColumn;
  @FXML private TableColumn<ViewMenuDishes,String> nameColumn;
  @FXML private TableColumn<ViewMenuDishes,String> ingredientsColumn;
  @FXML private TableColumn<ViewMenuDishes,Float> caloriesColumn;
  @FXML private TableColumn<ViewMenuDishes,String> typeColumn;
  @FXML private TableColumn<ViewMenuDishes,String> allergyColumn;
  @FXML private TableColumn<ViewMenuDishes,String> dietaryColumn;
  @FXML private TableColumn<ViewMenuDishes,Float> priceColumn;

  /**
   * Method used to load the FXML file with data from the database,
   * when the main method is started.
   * Implements auto refresh (reloads all data) each 10 seconds in the GUI.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    
    imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("name"));   
    ingredientsColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes,
                                                                   String>("ingredients"));
    
    caloriesColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, Float>("calories"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("foodType"));
    allergyColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes,
                                                               String>("allergyInfo"));
    
    dietaryColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes,
                                                               String>("dietaryReq"));
    
    priceColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, Float>("price"));

    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactory() {
        
        //Creating a thread to implement auto refresh of the GUI.
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        }
      });
    
    //Reloads GUI at a fixed time.
    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
    
        @Override
        public void run() {
            final Runnable runnable = new Runnable() {
            
                @Override
                public void run() {
                  ViewMenuDishes dishes = new ViewMenuDishes();
                  ObservableList<ViewMenuDishes> stock;
                
                    try {
                      stock = dishes.getAllIngredients();
                      menuTable.getItems().setAll(stock);
                
                    } catch (IOException exception) {
                      exception.printStackTrace();
                    }
                }
            };
            Platform.runLater(runnable);
        }
        
    }, 0, 10, TimeUnit.SECONDS);
  }
  
  @FXML
  private ImageView backButton;
  
  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutorService.shutdown();
    
    Navigation.navigateTo("/home/login/CustomerLoginPage.fxml",
        (Stage) backButton.getScene().getWindow(), "Customer Login Page");
  }

}