 
/**
  * The package 'waiterorder' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the waiter can view order data of the restaurant.
  */

package waiterorder;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;

/**
 * The 'TrackedOrders' class is used to create staff objects 
 * 'singleOrder' with data from the database.
 * @author Svetoslav Mihovski
 */
public class TrackedOrders {

  //Private fields of the class used to create the object.

  private SimpleIntegerProperty orderId;
  private SimpleIntegerProperty tableId;
  private SimpleStringProperty orderStatus;
  private SimpleFloatProperty orderPrice;
  private SimpleStringProperty orderTimeReq;
  private SimpleStringProperty orderTimeConf;
  private SimpleStringProperty orderTimeDeliv;
  private SimpleStringProperty orderTimeComp;

  /**
   * The constructor of the class used to initialize the variables.
   */
  public TrackedOrders() {
    this.orderId = new SimpleIntegerProperty();
    this.tableId = new SimpleIntegerProperty();
    this.orderStatus = new SimpleStringProperty();
    this.orderPrice = new SimpleFloatProperty();
    this.orderTimeReq = new SimpleStringProperty();
    this.orderTimeConf = new SimpleStringProperty();
    this.orderTimeDeliv = new SimpleStringProperty();
    this.orderTimeComp = new SimpleStringProperty();
  }

  /**
   * Constructor of 'Staff' class is used to initialize the fields in the database such as:
   * @param orderId - id of an order.
   * @param tableId - id of a table.
   * @param orderStatus - current status of an order.
   * @param orderPrice - current overall price of an order.
   * @param orderTimeReq - time when the order was requested.
   * @param orderTimeConf - time when the order was confirmed.
   * @param orderTimeDeliv -time when the order was delivered.
   * @param orderTimeComp - time when the order was completed.
   */
  public TrackedOrders(int orderId, int tableId, String orderStatus, float orderPrice,
      String orderTimeReq, String orderTimeConf,
      String orderTimeDeliv, String orderTimeComp) {

    // Assign fields

    this.orderId = new SimpleIntegerProperty(orderId);
    this.tableId = new SimpleIntegerProperty(tableId);
    this.orderStatus = new SimpleStringProperty(orderStatus);
    this.orderPrice = new SimpleFloatProperty(orderPrice);
    this.orderTimeReq = new SimpleStringProperty(orderTimeReq);
    this.orderTimeConf = new SimpleStringProperty(orderTimeConf);
    this.orderTimeDeliv = new SimpleStringProperty(orderTimeDeliv);
    this.orderTimeComp = new SimpleStringProperty(orderTimeComp);

  }
  
  /**
   * Returns a list of all of the tracked orders from the database.
   * 
   * @return a list of information about tracked orders.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<TrackedOrders> getAllConfirmedOrders()
      throws MalformedURLException, IOException {

    HttpRequester httpRequester = new HttpRequester();
    ObservableList<TrackedOrders> allOrders = FXCollections.observableArrayList();

    String sqlCommand = "SELECT orders.id, client_devices.table_id, order_state.name,"
        + " orders.cost, orders.time_requested::time, orders.time_confirmed::time,"
        + " orders.time_delivered::time, orders.time_completed::time"
        + " FROM order_state"
        + " INNER JOIN orders"
        + " ON order_state.id = orders.order_state_id"
        + " INNER JOIN client_devices"
        + " ON orders.order_device_id = client_devices.id"
        + " WHERE (orders.order_state_id <> 1)"
        + " AND (date(orders.time_requested) = now()::date)"
        + " ORDER BY orders.time_requested ASC;";

    // EACH LINE == one 'orders' object
    String[] orders = httpRequester
      .sendPost("/database/select",
        sqlCommand)
          .split("\n");
    
    if (!orders[0].equals("")) {
      for (String order : orders) {

        // split up and pass into a 'singleOrder' object
        String[] singleOrder = order.split(" ");

        TrackedOrders tempStaff = new TrackedOrders(
        
            Integer.parseInt(singleOrder[0]),
            Integer.parseInt(singleOrder[1]),
            singleOrder[2],
            Float.parseFloat(singleOrder[3]),
            singleOrder[4],
            singleOrder[5],
            singleOrder[6],
            singleOrder[7]
            );

        allOrders.add(tempStaff);
      }
      
    } else {
      allOrders.clear();
    }
    return allOrders;
  }

  //Set of getters and setters for the private fields.

  public Integer getOrderId() {
    return this.orderId.get();
  }

  public void setOrderId(Integer orderId) {
    this.orderId.set(orderId);
  }
  
  public Integer getTableId() {
    return this.tableId.get();
  }

  public void setTableId(Integer tableId) {
    this.tableId.set(tableId);
  }

  public String getOrderStatus() {
    return this.orderStatus.get();
  }
  
  public void setOrderStatus(String orderStatus) {
    this.orderStatus.set(orderStatus);
  }

  public Float getOrderPrice() {
    return this.orderPrice.get();
  }

  public void setOrderPrice(Float orderPrice) {
    this.orderPrice.set(orderPrice);
  }

  public String getOrderTimeReq() {
    return this.orderTimeReq.get();
  }

  public void setOrderTimeReq(String orderTimeReq) {
    this.orderTimeReq.set(orderTimeReq);
  }
  
  public String getOrderTimeConf() {
    return this.orderTimeConf.get();
  }

  public void setOrderTimeConf(String orderTimeConf) {
    this.orderTimeConf.set(orderTimeConf);
  }

  public String getOrderTimeDeliv() {
    return this.orderTimeDeliv.get();
  }

  public void setOrderTimeDeliv(String orderTimeDeliv) {
    this.orderTimeDeliv.set(orderTimeDeliv);
  }
  
  public String getOrderTimeComp() {
    return this.orderTimeComp.get();
  }

  public void setOrderTimeComp(String orderTimeComp) {
    this.orderTimeComp.set(orderTimeComp);
  }
  
}