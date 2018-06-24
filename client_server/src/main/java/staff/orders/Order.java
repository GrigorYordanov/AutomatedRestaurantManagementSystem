package staff.orders;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;


/**
 * The Order class takes the orders stored in the database and converts them into Order objects.
 * 
 * @author Christos Dexiades
 */

public class Order {

  private SimpleIntegerProperty id;
  private SimpleIntegerProperty table;
  private SimpleFloatProperty cost;
  private SimpleStringProperty dateRequested;
  private SimpleStringProperty timeRequested;
  private SimpleStringProperty orderStatus;

  private HttpRequester httpRequester = new HttpRequester();
  
  // Used to identify when a new order has been made so that a notification can be displayed on
  // the Waiter GUI.
  
  private int highestOrderId = 0;
  
  public Order() {

  }

  /** 
   *  Constructor for an Order object 
   *  
   *  @param id The unique numerical identifier assigned to each Order object
   *  @param table The table number corresponding to the order.
   *  @param cost The total cost of the order.
   *  @param dateRequested The date that the order was requested.
   *  @param timeRequested The time that the order was requested.
   *  @param orderStatus The current state of the order, which can be used to track progress.
   *  
   */
  
  public Order(int id, int table, float cost, String dateRequested, String timeRequested, 
      String orderStatus) {

    this.id = new SimpleIntegerProperty(id);
    this.table = new SimpleIntegerProperty(table);
    this.cost = new SimpleFloatProperty(cost);
    this.dateRequested = new SimpleStringProperty(dateRequested);
    this.timeRequested = new SimpleStringProperty(timeRequested);
    this.orderStatus = new SimpleStringProperty(orderStatus);

  }

  // Getters and setters for each of the attributes in an Order object.
  
  public int getId() {
    return id.get();
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public int getTable() {
    return table.get();
  }

  public void setTable(int table) {
    this.table.set(table);
  }

  public float getCost() {
    return cost.get();
  }

  public void setCost(float cost) {
    this.cost.set(cost);
  }

  public String getDateRequested() {
    return dateRequested.get();
  }

  public void setDateRequested(String dateRequested) {
    this.dateRequested.set(dateRequested);
  }

  public String getTimeRequested() {
    return timeRequested.get();
  }

  public void setTimeRequested(String timeRequested) {
    this.timeRequested.set(timeRequested);
  }

  public String getOrderStatus() {
    return orderStatus.get();
  }
  
  /** 
   *  Receives a string version of the order status and is passed to an SQL statement, 
   *  converted to its integer version and is set as the status of the order in the database.
   *  
   *  @throws MalformedURLException When the URL passed to the HTTPRequested is invalid
   *  @throws IOException
   *  
   */
  
  public void setOrderStatus(String orderStatus) throws MalformedURLException, IOException {

    String sqlCommand = null;

    // Depending on the new status of an order, a different SQL query is executed as each one
    // updates a different timestamp field in the orders table.
    
    if (orderStatus.trim().equalsIgnoreCase("confirmed")) {

      sqlCommand = "UPDATE orders"
            + " SET order_state_id=(SELECT id FROM order_state"
            + " WHERE order_state.name = 'confirmed'), time_confirmed = CURRENT_TIMESTAMP"
            + " WHERE orders.id = '" + this.getId() + "'";

    } else if (orderStatus.trim().equalsIgnoreCase("delivered")) {

      sqlCommand = "UPDATE orders"
            + " SET order_state_id=(SELECT id FROM order_state"
            + " WHERE order_state.name = 'delivered'), time_delivered = CURRENT_TIMESTAMP"
            + " WHERE orders.id = '" + this.getId() + "'";
    } else if (orderStatus.trim().equalsIgnoreCase("completed")) {

      sqlCommand = "UPDATE orders"
            + " SET order_state_id=(SELECT id FROM order_state"
            + " WHERE order_state.name = 'completed'), time_completed = CURRENT_TIMESTAMP"
            + " WHERE orders.id = '" + this.getId() + "'";
    } else {

      sqlCommand = "UPDATE orders"
            + " SET order_state_id=(SELECT id FROM order_state"
            + " WHERE order_state.name = '" + orderStatus + "')"
            + " WHERE orders.id = '" + this.getId() + "'";
    }

    httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    this.orderStatus.set(orderStatus);
   
  }
  
  public int getHighestOrderId() {
    return highestOrderId;
  }
  
  public void setHighestOrderId(int id) {
    highestOrderId = id;
  }
  
  /** 
   *  Retrieves and converts each order record in the database to an Order object and added
   *  to an ObservableList for displaying on the Waiter GUI.
   *  
   *  @throws MalformedURLException When the URL passed to the HTTPRequested is invalid
   *  @throws IOException
   *  
   */
  
  public ObservableList<Order> getCurrentOrders() throws MalformedURLException, IOException {

    ObservableList<Order> ordersList = FXCollections.observableArrayList();
    
    String sqlCommand = "SELECT orders.id, orders.order_device_id, orders.cost,"
        + " orders.time_requested, order_state.name"
        + " FROM orders"
        + " INNER JOIN order_state"
        + " ON orders.order_state_id = order_state.id"
        + " ORDER BY orders.time_requested"
        + " ASC";

    String[] orders = httpRequester.sendPost("/database/select", sqlCommand).split("\n");

    for (String order : orders) {

      String[] individualOrder = order.split(" ");

      Order tempOrder = new Order(
          Integer.parseInt(individualOrder[0]),
          Integer.parseInt(individualOrder[1]),
          Float.parseFloat(individualOrder[2]),
          individualOrder[3],
          individualOrder[4],
          individualOrder[5]);
      
      if (!(tempOrder.getOrderStatus().equals("completed"))) {
        ordersList.add(tempOrder);
        
        if (tempOrder.getId() > highestOrderId) {
          highestOrderId = tempOrder.getId();
        }
      }
      
    }
    return ordersList;
  }
  
  /** 
   *  Retrieves each order status in the database for displaying on the Waiter GUI in a
   *  ComboBoxTableCell.
   *  
   *  @throws MalformedURLException When the URL passed to the HTTPRequested is invalid
   *  @throws IOException
   *  
   */
  
  public String[] getAllOrderStatuses() throws MalformedURLException, IOException {

    String sqlCommand = "SELECT name FROM order_state";
    
    String[] statuses = httpRequester.sendPost("/database/select", sqlCommand).split("\n");

    return statuses;

  }

}