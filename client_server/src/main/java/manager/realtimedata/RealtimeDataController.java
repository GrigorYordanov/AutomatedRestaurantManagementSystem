
/**
  * The package 'realtimedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view real-time data data of the restaurant.
  */

package manager.realtimedata;

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
import manager.employeedata.Staff;
import staff.menu.Ingredient;
import utilities.Navigation;

/**
 * The class 'RealtimeDataController' is used to fill the FXML file with data
 * from the 'Table' and 'Ingredient'classes.
 * @author Svetoslav Mihovski
 */
public class RealtimeDataController
    implements Initializable {
  
  ScheduledExecutorService scheduledExecutorService;
  
  //Injecting values from FXML loader
  
  @FXML private TableView<Ingredient> stockTable;
  @FXML private TableColumn<Ingredient,String> ingredientColumn;
  @FXML private TableColumn<Ingredient,Float> quantityColumn;
  @FXML private TableColumn<Ingredient,String> metricColumn;

  @FXML private TableView<Table> dataTable;
  @FXML private TableColumn<Table,Integer> tableColumn;
  @FXML private TableColumn<Table,String> tstatusColumn;
  @FXML private TableColumn<Table,Integer> orderColumn;
  @FXML private TableColumn<Table,String> ostatusColumn;
  @FXML private TableColumn<Table,Float> costColumn;
  @FXML private TableColumn<Table,String> requestedColumn;
  @FXML private TableColumn<Table,String> confirmedColumn;
  @FXML private TableColumn<Table,String> deliveredColumn;
  @FXML private TableColumn<Table,String> completedColumn;
  @FXML private TableColumn<Table,String> waiterColumn;

  /**
   * Method used to load the FXML file with data from the database,
   * when the main method is started.
   * Implements auto refresh (reloads all data) each 10 seconds in the GUI.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));

    ingredientColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("quantity"));
    metricColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("metricName"));

    tableColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("tableId"));
    tstatusColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("tableStatus"));
    orderColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("orderId"));
    ostatusColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("orderStatus"));
    costColumn.setCellValueFactory(new PropertyValueFactory<Table, Float>("orderCost"));
    requestedColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("timeRequested"));
    confirmedColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("timeConfirmed"));
    deliveredColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("timeDelivered"));
    completedColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("timeCompleted"));
    waiterColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("waiterName"));

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
                  Ingredient ingredient = new Ingredient();
                  ObservableList<Ingredient> stock;
                
                  Table table = new Table();
                  ObservableList<Table> tableOrders;
                
                    try {
                      stock = ingredient.getAllIngredients();
                      stockTable.getItems().setAll(stock);
                
                      tableOrders = table.getAllTables();
                      dataTable.getItems().setAll(tableOrders);
                
                    } catch (IOException exception) {
                      exception.printStackTrace();
                    }
                }
            };
            Platform.runLater(runnable);
        }
        
    }, 0, 10, TimeUnit.SECONDS);
  }
  
  //------Darren Code----//
  @FXML
  private ImageView backButton;
  
  private Staff thisManager;

  @FXML
  void navigateBack(MouseEvent event) {
    //stop the thread from running when not on the gui no mores
    scheduledExecutorService.shutdown();
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), thisManager, "Manager Main Menu");
  }
  
  public void initData(Staff user) {
    this.thisManager = user;
  }

}