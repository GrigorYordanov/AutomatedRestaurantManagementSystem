package waiter.waiterapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import staff.orders.Order;
import utilities.Navigation;
import utilities.Users;

/**
 * This is the controller class used to add functionality to the Waiter GUI.
 * 
 * @author Christos Dexiades
 * 
 * @see WaiterControllerMain
 */

public class WaiterController implements Initializable {

  // Global variables

  ObservableList<Order> ordersMasterData = null;
  String[] orderStatuses = null;

  int originalNumOfOrders = 0;
  int newNumOfOrders = 0;

  int refreshInterval = 20;

  private Staff thisWaiter;

  @FXML
  Pane mainPane;

  // FXML controls for the 'Current Orders' table

  @FXML
  TableView<Order> ordersTable = new TableView<Order>();
  @FXML
  TableColumn idCol = new TableColumn("Order ID");
  @FXML
  TableColumn tableCol = new TableColumn("Table");
  @FXML
  TableColumn costCol = new TableColumn("Price");
  @FXML
  TableColumn dateCol = new TableColumn("Date Requested");
  @FXML
  TableColumn timeCol = new TableColumn("Time Requested");
  @FXML
  TableColumn statusCol = new TableColumn("Order Status");
  @FXML
  TableColumn<Order, String> statusUpdateCol = new TableColumn<Order, String>("Update Status");

  // FXML controls for filtering the 'Current Orders' table

  @FXML
  ToggleGroup orderFilters;
  @FXML
  RadioButton allOrders;
  @FXML
  RadioButton unpaidOrders;
  @FXML
  RadioButton undeliveredOrders;
  @FXML
  RadioButton unconfirmedOrders;

  // FXML label to display order notifications

  @FXML
  Label orderNotifications;

  // FXML button for refreshing the GUI

  @FXML
  Button refreshOrders;

  @FXML
  private ImageView backButton;

  @FXML
  void navigateBack(MouseEvent event) {
    // unassign waiter from the tables it has
    Users.unassignWaiter(this.thisWaiter);

    Navigation.navigateToStaff("/waiterorder/Waiter.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisWaiter,
        "Waiter Application");
  }

  public void refreshOrders(EventHandler<ActionEvent> event) {
    refreshOrders.setOnAction(event);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    Order order = new Order();

    try {
      orderStatuses = order.getAllOrderStatuses();
    } catch (IOException exception) {
      System.out.println("Waiter GUI: a list of allowed order statuses could not be retrieved!");
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

    statusUpdateCol.setCellFactory(ComboBoxTableCell.forTableColumn(orderStatuses));

    // Allows the user to update the status of a specific order in the table

    statusUpdateCol.setOnEditCommit((TableColumn.CellEditEvent<Order, String> cell) -> {

      try {
        cell.getTableView().getItems().get(cell.getTablePosition().getRow())
            .setOrderStatus(cell.getNewValue());
      } catch (IOException exception) {
        // DO NOTHING
      }

    });

    // Adds all columns defined above to the table that is displayed on the GUI

    ordersTable.getColumns().addAll(idCol, tableCol, costCol, dateCol, timeCol, statusCol,
        statusUpdateCol);

    // Initial call to populate the 'Current Orders' table with data

    updateOrdersPanel();

    // Adds functionality to the refresh button to allow manual reloading of the
    // GUI

    refreshOrders(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        refreshUserInterface();
      }
    });

    // Enables the GUI to refresh itself automatically after 10 seconds

    ScheduledExecutorService scheduledExecutor = Executors
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
   * Code adapted from:
   * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
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

    // Set radio button to filter list by orders whose state is not payed

    unpaidOrders.selectedProperty().addListener((observable, oldValue, newValue) -> {
      ordersFilteredData.setPredicate(filteredOrder -> {
        if (!(filteredOrder.getOrderStatus().equalsIgnoreCase("completed"))) {
          return true;
        } else {
          return false;
        }
      });
    });

    // Set radio button to filter list by orders whose state is not delivered

    undeliveredOrders.selectedProperty().addListener((observable, oldValue, newValue) -> {
      ordersFilteredData.setPredicate(filteredOrder -> {
        if (!(filteredOrder.getOrderStatus().equalsIgnoreCase("delivered")
            || filteredOrder.getOrderStatus().equalsIgnoreCase("completed"))) {
          return true;
        } else {
          return false;
        }
      });
    });

    // Set radio button to filter list by orders that require confirming

    unconfirmedOrders.selectedProperty().addListener((observable, oldValue, newValue) -> {
      ordersFilteredData.setPredicate(filteredOrder -> {
        if (filteredOrder.getOrderStatus().equalsIgnoreCase("placed")) {
          return true;
        } else {
          return false;
        }
      });
    });

    // Set radio button to display all orders. This is needed to display all
    // orders
    // after a filter has been applied

    allOrders.selectedProperty().addListener((observable, oldValue, newValue) -> {
      ordersFilteredData.setPredicate(filteredOrder -> {
        if (!(filteredOrder.getOrderStatus().equalsIgnoreCase(""))) {
          return true;
        } else {
          return false;
        }
      });
    });

    // Wraps FilteredList in a SortedList

    SortedList<Order> currentOrdersSorted = new SortedList<>(ordersFilteredData);

    currentOrdersSorted.comparatorProperty().bind(ordersTable.comparatorProperty());

    // Set the orders table to display the current orders, taking into account
    // any filtering

    ordersTable.setItems(currentOrdersSorted);
  }

  /**
   * Method is called each time the refresh button or the automatic refresh
   * feature is used. It allows the program to decide whether or not a 'New
   * Order' notification should be displayed based on the highest order ID
   * before and after a list of orders is retrieved from the database.
   * 
   * @author Christos Dexiades
   * 
   */

  public void refreshUserInterface() {

    // Number of orders before the database is queried for an updated orders
    // list

    originalNumOfOrders = ordersMasterData.size();

    updateOrdersPanel();

    // Number of orders after the database is queried for an updated orders list

    newNumOfOrders = ordersMasterData.size();

    // If the new number of orders is higher than the original number, a
    // notification is displayed

    if (originalNumOfOrders < newNumOfOrders) {
      orderNotifications.setVisible(true);
    } else {
      orderNotifications.setVisible(false);
    }

    originalNumOfOrders = newNumOfOrders;
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
   * @param thisUser
   *          the waiter for this session
   * 
   * @author Darren Matthews
   */

  public void initData(Staff thisUser) {
    this.thisWaiter = thisUser;
  }

}