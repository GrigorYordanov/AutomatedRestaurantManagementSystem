
/**
  * The package 'waiterorder' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the waiter can view order data of the restaurant.
  */

package waiterorder;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import server.httprequest.HttpRequester;
import utilities.Navigation;

/**
 * The class 'WaiterController' is used to fill the FXML file with data
 * from the 'RequestOrders' and 'TrackedOrders' classes.
 * @author Svetoslav Mihovski
 */
public class WaiterController
    implements Initializable {

  private Staff thisWaiter;
  ScheduledExecutorService scheduledExecutorService;
  
  //Injecting values from FXML loader
  
  @FXML private TableView<RequestedOrders> confirmOrder;
  @FXML private TableColumn<RequestedOrders,Integer> orderId;
  @FXML private TableColumn<RequestedOrders,Integer> tableId;
  @FXML private TableColumn<RequestedOrders,String> orderStatus;
  @FXML private TableColumn<RequestedOrders,Float> orderPrice;
  @FXML private TableColumn<RequestedOrders,String> orderTimeReq;
  
  @FXML private TableView<TrackedOrders> trackOrder;
  @FXML private TableColumn<TrackedOrders,Integer> orderId2;
  @FXML private TableColumn<TrackedOrders,Integer> tableId2;
  @FXML private TableColumn<TrackedOrders,String> orderStatus2;
  @FXML private TableColumn<TrackedOrders,Float> orderPrice2;
  @FXML private TableColumn<TrackedOrders,String> orderTimeReq2;
  @FXML private TableColumn<TrackedOrders,String> orderTimeConf2;
  @FXML private TableColumn<TrackedOrders,String> orderTimeDeliv2;
  @FXML private TableColumn<TrackedOrders,String> orderTimeComp2;
  
  @FXML private TextField waiterName;
  @FXML private TextField tableIdField;
  @FXML private Button confirmButton;

  /**
   * Method used to load the FXML file with data from the database,
   * when the main method is started.
   * Implements auto refresh (reloads all data) each 10 seconds in the GUI.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    //imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
    
    orderId.setCellValueFactory(new PropertyValueFactory<RequestedOrders, Integer>("orderId"));
    tableId.setCellValueFactory(new PropertyValueFactory<RequestedOrders, Integer>("tableId"));
    orderStatus.setCellValueFactory(new PropertyValueFactory<RequestedOrders,
                                                             String>("orderStatus"));
    
    orderPrice.setCellValueFactory(new PropertyValueFactory<RequestedOrders, Float>("orderPrice"));
    orderTimeReq.setCellValueFactory(new PropertyValueFactory<RequestedOrders,
                                                              String>("orderTimeReq"));
    
    orderId2.setCellValueFactory(new PropertyValueFactory<TrackedOrders, Integer>("orderId"));
    tableId2.setCellValueFactory(new PropertyValueFactory<TrackedOrders, Integer>("tableId"));
    orderStatus2.setCellValueFactory(new PropertyValueFactory<TrackedOrders,
                                                              String>("orderStatus"));
    
    orderPrice2.setCellValueFactory(new PropertyValueFactory<TrackedOrders, Float>("orderPrice"));
    orderTimeReq2.setCellValueFactory(new PropertyValueFactory<TrackedOrders,
                                                               String>("orderTimeReq"));
    
    orderTimeConf2.setCellValueFactory(new PropertyValueFactory<TrackedOrders,
                                                                String>("orderTimeConf"));
    
    orderTimeDeliv2.setCellValueFactory(new PropertyValueFactory<TrackedOrders,
                                                                 String>("orderTimeDeliv"));
    
    orderTimeComp2.setCellValueFactory(new PropertyValueFactory<TrackedOrders,
                                                                String>("orderTimeComp"));

    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
    
        @Override
        public void handle(ActionEvent event) {
      
          //Reads marked field from table.
          RequestedOrders order = confirmOrder.getSelectionModel().getSelectedItem();
          Integer orderId = order.getOrderId();
          
          //SQL to update the database on button click regarding confirmed orders.
          String waiterName = getWaiter().getFullName();
          String sqlInsert = "INSERT INTO order_waiter (order_id, waiter_id)"
              + " VALUES(" + orderId + ","
              + "(SELECT id FROM staff WHERE full_name = '" + waiterName + "'));";
          String sqlUpdate = "UPDATE orders SET order_state_id = 2"
              + " WHERE id = " + orderId + ";";
       
          HttpRequester httpRequester = new HttpRequester();
          try {
            httpRequester.sendPost("/database/insertRow", sqlInsert);
            httpRequester.sendPost("/database/updateRow", sqlUpdate);
          } catch (MalformedURLException exception) {
            exception.printStackTrace();
          } catch (IOException exception) {
            exception.printStackTrace();
          }
          
          RequestedOrders placedOrders = new RequestedOrders();
          ObservableList<RequestedOrders> ordersView;
          
          TrackedOrders placedOrders2 = new TrackedOrders();
          ObservableList<TrackedOrders> ordersView2;
        
            try {
            
              ordersView = placedOrders.getAllPlacedOrders();
              confirmOrder.getItems().setAll(ordersView);
              
              ordersView2 = placedOrders2.getAllConfirmedOrders();
              trackOrder.getItems().setAll(ordersView2);
        
            } catch (IOException exception) {
              exception.printStackTrace();
            }
        }
      });
    
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
                
                    RequestedOrders placedOrders = new RequestedOrders();
                    ObservableList<RequestedOrders> ordersView;
                     
                    TrackedOrders placedOrders2 = new TrackedOrders();
                    ObservableList<TrackedOrders> ordersView2;
                
                    try {
                    
                      ordersView = placedOrders.getAllPlacedOrders();
                      confirmOrder.getItems().setAll(ordersView);
                      
                      ordersView2 = placedOrders2.getAllConfirmedOrders();
                      trackOrder.getItems().setAll(ordersView2);
                      
                      String name = getWaiter().getFullName().toString();
                      waiterName.setText(name);
                      
                      tableIdField.setText(getTableNumber(name));
                
                    } catch (IOException exception) {
                      exception.printStackTrace();
                    }
                }
            };
            Platform.runLater(runnable);
        }
        
    }, 0, 10, TimeUnit.SECONDS);
  }
  
  /**
   * Method used to get the assigned table to a waiter using waiter's full name.
   * @param name - name of the waiter.
   * @return number of the table assigned to the waiter.
   */
  private String getTableNumber(String name)
                                throws MalformedURLException, IOException  {

    HttpRequester httpRequester = new HttpRequester();

    String sqlSelect = "SELECT id FROM restourant_table"
              + " WHERE waiter_id = (SELECT id FROM staff"
              + " WHERE full_name = '" + name + "');";

    String[] tables = httpRequester
      .sendPost("/database/select", sqlSelect)
        .split("\n");
    
    return tables[0];
  }
  
  @FXML
  private ImageView backButton;
  
  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutorService.shutdown();
    
    Navigation.navigateTo("/home/login/StaffLogin.fxml",
        (Stage) backButton.getScene().getWindow(), "Staff Login");
  }
  
  /**
   * Method to return the waiter that is currently logged in to the application.
   * 
   * @return Staff waiter logged in
   * 
   * @author Darren Matthews
   */
  
  public Staff getWaiter() {
    return this.thisWaiter;
  }

  /**
   * Method to pass the logged in waiter from the previous application screen.
   * 
   * @param thisUser the waiter for this session
   * 
   * @author Darren Matthews
   */
  
  public void initData(Staff thisUser) {
    this.thisWaiter = thisUser;
  }
  

  @FXML private Button deliverOrder;

  @FXML
  void deliverOrderClick(MouseEvent event) {
    Navigation.navigateToStaff("/waiter/waiterapp/WaiterApplication.fxml",
        (Stage) deliverOrder.getScene().getWindow(), this.thisWaiter,
        "Waiter Delivery");
  }

}