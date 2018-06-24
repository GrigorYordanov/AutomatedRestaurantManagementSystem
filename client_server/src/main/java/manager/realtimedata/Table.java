
/**
  * The package 'realtimedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view real-time data data of the restaurant.
  */

package manager.realtimedata;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;

/**
 * The 'Table' class is used to create table objects 'singleTable' with data from the database.
 * @author Svetoslav Mihovski
 */
public class Table {

  //Private fields of the class used to create the object.
  private SimpleIntegerProperty tableId;
  private SimpleStringProperty tableStatus;
  private SimpleIntegerProperty orderId;
  private SimpleStringProperty orderStatus;
  private SimpleFloatProperty orderCost;
  private SimpleStringProperty timeRequested;
  private SimpleStringProperty timeConfirmed;
  private SimpleStringProperty timeDelivered;
  private SimpleStringProperty timeCompleted;
  private SimpleStringProperty waiterName;
  
  public Table() {

  }
  
  /**
   * Constructor of 'Staff' class is used to initialize the fields in the database such as:
   * @param tableId - ID of a table.
   * @param tableStatus - current status of a table.
   * @param orderId - ID of an order.
   * @param orderStatus - current status of an order.
   * @param orderCost - the cost of an order.
   * @param timeRequested - the time when an order was requested.
   * @param timeConfirmed - the time when an order was confirmed.
   * @param timeDelivered - the time when an order was delivered.
   * @param timeCompleted - the time when an order was completed.
   * @param waiterName - the name of the waiter porcessing the order.
   */
  public Table(int tableId, String tableStatus, int orderId, String orderStatus, Float orderCost,
      String timeRequested,String timeConfirmed, String timeDelivered,
      String timeCompleted, String waiterName) {

    // Assign fields
    this.tableId = new SimpleIntegerProperty(tableId);
    this.tableStatus = new SimpleStringProperty(tableStatus);
    this.orderId = new SimpleIntegerProperty(orderId);
    this.orderStatus = new SimpleStringProperty(orderStatus);
    this.orderCost = new SimpleFloatProperty(orderCost);
    this.timeRequested = new SimpleStringProperty(timeRequested);
    this.timeConfirmed = new SimpleStringProperty(timeConfirmed);
    this.timeDelivered = new SimpleStringProperty(timeDelivered);
    this.timeCompleted = new SimpleStringProperty(timeCompleted);
    this.waiterName = new SimpleStringProperty(waiterName);
  
  }
 
  /**
   * Returns a list of all of the specific table data from the database.
   * 
   * @return a list of information about a table in the restaurant.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<Table> getAllTables() throws MalformedURLException, IOException {
    HttpRequester httpRequester = new HttpRequester();
    ObservableList<Table> tableOrders = FXCollections.observableArrayList();

    String sqlCommand = "SELECT restourant_table.id, table_status.name, orders.id,"
         + " order_state.name, orders.cost, to_char(orders.time_requested, 'HH24:MI:SS')"
         + " AS time_req, to_char(orders.time_confirmed, 'HH24:MI:SS') AS time_conf,"
         + " to_char(orders.time_delivered, 'HH24:MI:SS') AS time_deliv,"
         + " to_char(orders.time_completed, 'HH24:MI:SS') AS time_comp, staff.full_name"
         + " FROM orders"
         + " LEFT OUTER JOIN order_waiter ON orders.id = order_waiter.order_id"
         + " LEFT OUTER JOIN staff ON order_waiter.waiter_id = staff.id"
         + " LEFT OUTER JOIN order_state ON orders.order_state_id = order_state.id"
         + " LEFT OUTER JOIN client_devices ON orders.order_device_id = client_devices.id"
         + " LEFT OUTER JOIN restourant_table ON client_devices.table_id = restourant_table.id"
         + " LEFT OUTER JOIN table_status ON restourant_table.table_status_id = table_status.id"
         + " WHERE date(orders.time_requested) = now()::date"
         + " ORDER BY restourant_table.id ASC;";
      
    String[] tables = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    for (String table : tables) {
    
      String[] singleTable = table.split(" ");
      
      if (singleTable[8].equals("null")) {
        singleTable[8] = "0";
      }

      Table tempTable = new Table(
                  Integer.parseInt(singleTable[0]),
                  singleTable[1],
                  Integer.parseInt(singleTable[2]),
                  singleTable[3],
                  Float.parseFloat(singleTable[4]),
                  singleTable[5],
                  singleTable[6],
                  singleTable[7],
                  singleTable[8],
                  singleTable[9]);
      
      tableOrders.add(tempTable);
    }
    return tableOrders;
  }

  //Set of getters and setters for the private fields.
  
  public int getTableId() {
    return tableId.get();
  }

  public void setTableId(int tableId) {
    this.tableId.set(tableId);
  }

  public String getTableStatus() {
    return tableStatus.get();
  }

  public void setTableStatus(String tableStatus) {
    this.tableStatus.set(tableStatus);
  }

  public int getOrderId() {
    return orderId.get();
  }

  public void setOrderId(int orderId) {
    this.orderId.set(orderId);
  }

  public String getOrderStatus() {
    return orderStatus.get();
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus.set(orderStatus);
  }

  public Float getOrderCost() {
    return orderCost.get();
  }

  public void setOrderCost(Float orderCost) {
    this.orderCost.set(orderCost);
  }

  public String getTimeRequested() {
    return timeRequested.get();
  }

  public void setTimeRequested(String timeRequested) {
    this.timeRequested.set(timeRequested);
  }

  public String getTimeConfirmed() {
    return timeConfirmed.get();
  }

  public void setTimeConfirmed(String timeConfirmed) {
    this.timeConfirmed.set(timeConfirmed);
  }

  public String getTimeDelivered() {
    return timeDelivered.get();
  }

  public void setTimeDelivered(String timeDelivered) {
    this.timeDelivered.set(timeDelivered);
  }

  public String getTimeCompleted() {
    return timeCompleted.get();
  }

  public void setTimeCompleted(String timeCompleted) {
    this.timeCompleted.set(timeCompleted);
  }

  public String getWaiterName() {
    return waiterName.get();
  }

  public void setWaiterName(String waiterName) {
    this.waiterName.set(waiterName);
  }

}