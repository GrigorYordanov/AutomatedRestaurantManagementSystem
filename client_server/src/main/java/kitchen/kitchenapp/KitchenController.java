package kitchen.kitchenapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import manager.employeedata.Staff;
import staff.orders.Order;
import utilities.Navigation;


/**
 * This is the controller class used to add functionality to the Kitchen Staff GUI.
 * 
 * @author Christos Dexiades
 *
 */

public class KitchenController implements Initializable {
  
  ScheduledExecutorService scheduledExecutor;
  Staff thisCook;
  

  /**
   * Method to pass the logged in kitchen staff from the previous application screen.
   * 
   * @param user the kitchen staff for this session
   * 
   * @author Darren Matthews
   */
  
  public void initData(Staff user) {
    this.thisCook = user;
  }

  // Global variables

  ObservableList<Order> ordersMasterData = null;
  String[] orderStatuses = null;

  int refreshInterval = 20;
 
  @FXML Pane mainPane;
  
  // FXML controls for the 'Current Orders' table

  @FXML TableView<Order> ordersTable = new TableView<Order>();
  @FXML TableColumn idCol = new TableColumn("Order ID");
  @FXML TableColumn tableCol = new TableColumn("Table");
  @FXML TableColumn costCol = new TableColumn("Price");
  @FXML TableColumn dateCol = new TableColumn("Date Requested");
  @FXML TableColumn timeCol = new TableColumn("Time Requested");
  @FXML TableColumn statusCol = new TableColumn("Order Status");
  @FXML TableColumn<Order, Boolean> statusUpdateCol = new TableColumn<Order, Boolean>(
      "Update Status");

  // FXML button for refreshing the GUI  
  
  @FXML Button refreshOrders;
  
  @FXML private ImageView backButton;

  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutor.shutdown();
    
    Navigation.navigateTo("/home/login/StaffLogin.fxml",
        (Stage) backButton.getScene().getWindow(), "Staff Login");
  }

  public void refreshOrders(EventHandler<ActionEvent> event) {
    refreshOrders.setOnAction(event);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    
    Order order = new Order();

    try {
      orderStatuses = order.getAllOrderStatuses();
    } catch (IOException exception) {
      System.out.println(
          "Kitchen Staff GUI: a list of allowed order statuses could not be retrieved!");
    }

    // Keeps the columns in the 'Current Orders' table evenly sized

    ordersTable.setColumnResizePolicy(ordersTable.CONSTRAINED_RESIZE_POLICY);

    // Initialise the columns in the 'Current Orders' table

    idCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
    tableCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("table"));
    costCol.setCellValueFactory(new PropertyValueFactory<Order, Float>("cost"));
    dateCol.setCellValueFactory(new PropertyValueFactory<Order, String>("dateRequested"));
    timeCol.setCellValueFactory(new PropertyValueFactory<Order, String>("timeRequested"));
    statusCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderStatus"));

    // Allows the user to update the status of a specific order in the table

    statusUpdateCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Order, Boolean>, ObservableValue<Boolean>>() {

            @Override
              public ObservableValue<Boolean> call(
                  TableColumn.CellDataFeatures<Order, Boolean> features) {
              return new SimpleBooleanProperty(features.getValue() != null);
            }
          });

    // create a cell value factory with an add button for each row in the table for table 1
    
    statusUpdateCol.setCellFactory(new Callback<TableColumn<Order, Boolean>, 
            TableCell<Order, Boolean>>() {
    
          @Override
          public TableCell<Order, Boolean> call(TableColumn<Order, 
                  Boolean> unitBooleanTableColumn) {
            return new ChangeOrderStatusCell(ordersTable);
          }
        });

    // Adds all columns defined above to the table that is displayed on the GUI

    ordersTable.getColumns().addAll(idCol, tableCol, costCol, dateCol, timeCol,
        statusCol, statusUpdateCol);

    // Initial call to populate the 'Current Orders' table with data

    updateOrdersPanel();

    // Adds functionality to the refresh button to allow manual reloading of the GUI

    refreshOrders(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        refreshUserInterface();
      }
    });

    // Enables the GUI to refresh itself automatically after 10 seconds

    scheduledExecutor = Executors
        .newSingleThreadScheduledExecutor(new ThreadFactory() {
          @Override
          public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
          }
        });

    scheduledExecutor.scheduleAtFixedRate(new Runnable() {

        @Override
        public void run() {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                  refreshUserInterface();
              }
            };
            Platform.runLater(runnable);
        }
        
    }, 0, refreshInterval, TimeUnit.SECONDS);

  }

  /**
   * This method is called by the initialize method to set up the 'Current
   * Orders' tab and is assigned to the refresh button on this tab to manually
   * update the orders table.
   * 
   */

  public void updateOrdersPanel() {

    // Obtain master set of data (i.e. a list of all current orders)

    Order order = new Order();

    ordersTable.setEditable(true);

    try {
      ordersMasterData = order.getCurrentOrders();
    } catch (IOException exception) {
      System.out.println("IOException!");
    }

    // Wrap ObservableList into FilteredList

    FilteredList<Order> ordersFilteredData = new FilteredList<>(ordersMasterData, p -> true);

    // Filter list of orders to just those that are confirmed but not prepared

    ordersFilteredData.setPredicate(filteredOrder -> {
      if (filteredOrder.getOrderStatus().equalsIgnoreCase("confirmed")) {
        return true;
      } else {
        return false;
      }
    });

    // Wraps FilteredList in a SortedList

    SortedList<Order> currentOrdersSorted = new SortedList<>(ordersFilteredData);
    currentOrdersSorted.comparatorProperty().bind(ordersTable.comparatorProperty());
    
    // Set the orders table to display the current orders, taking into account
    // any filtering

    ordersTable.setItems(currentOrdersSorted);
  }

  /** 
   *  Method is called each time the refresh button or the automatic refresh feature is used.
   *  
   */
  
  public void refreshUserInterface() {    
    updateOrdersPanel();
  }
  
  private class ChangeOrderStatusCell extends TableCell<Order, Boolean> {

    // A button to change the order status to prepared

    final Button changeStatusButton = new Button("Next Step");
    
    // Pads and centers the button within the cell in TableView

    final StackPane paddedButton = new StackPane();

    /**
     * Constructor to create a new ChangeOrderStatusCell object
     * 
     * @param table The TableView displaying the list of orders.
     */

    ChangeOrderStatusCell(final TableView<Order> table) {
      paddedButton.setPadding(new Insets(3));
      paddedButton.getChildren().add(changeStatusButton);
      changeStatusButton.setStyle("-fx-background-color: #00ff00");
      changeStatusButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {

          Order selectedOrder = table.getItems().get(getIndex());
          System.out.println(selectedOrder.getOrderStatus());

          try {
            selectedOrder.setOrderStatus("prepared");
          } catch (IOException expected) {
            // DO NOTHING
          } 
        }
      });
    }
    
    // Places a button in the row if and only if the row is not empty
  
    @Override
    protected void updateItem(Boolean item, boolean empty) {

      super.updateItem(item, empty);

      if (!empty) {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(paddedButton);
      } else {
        setGraphic(null);
      }
    }
  }
}