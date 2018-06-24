package customer.order;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import customer.viewmenu.ViewMenuDishes;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import menu.Dish;
import server.httprequest.HttpRequester;

/**
 * This class represents an order that the customer can place.
 * 
 * @author James Harris
 */
public class Order {
  /**
   * The ID of the order.
   */
  private SimpleIntegerProperty id;

  /**
   * The ID of the device that is being used to create the order.
   */
  private SimpleIntegerProperty orderDeviceId;

  /**
   * The ID of the customer placing the order.
   */
  private SimpleIntegerProperty customerId;

  /**
   * The total cost of the order.
   */
  private SimpleFloatProperty cost;

  /**
   * The time that the order is requested by the customer.
   */
  private SimpleStringProperty timeRequested;

  /**
   * The time that the order is confirmed by the waiter.
   */
  private SimpleStringProperty timeConfirmed;

  /**
   * The time that the order is delivered to the table by the waiter.
   */
  private SimpleStringProperty timeDelivered;

  /**
   * The time that the order is completed by the kitchen staff.
   */
  private SimpleStringProperty timeCompleted;

  /**
   * The state of the order (i.e. whether the order has been requested,
   * delivered, etc.)
   */
  private SimpleIntegerProperty orderStateId;

  /**
   * The list of dishes in the order.
   */
  private ObservableList<ViewMenuDishes> dishList;

  /**
   * Used to send HTTP requests to server.
   */
  private HttpRequester httpRequester;

  /**
   * Constructor for the Order class.
   * 
   * @param id
   *          The ID of the order
   * @param orderDeviceId
   *          The ID of the device that is being used to create the order
   * @param customerId
   *          The ID of the customer placing the order
   * @param cost
   *          The total cost of the order
   * @param timeRequested
   *          The time that the order is requested by the customer
   * @param timeConfirmed
   *          The time that the order is confirmed by the waiter
   * @param timeDelivered
   *          The time that the order is delivered to the table by the waiter
   * @param timeCompleted
   *          The time that the order is completed by the kitchen staff
   * @param orderStateId
   *          The state of the order (i.e. whether the order has been requested,
   *          delivered, etc.)
   */
  public Order(int id, int orderDeviceId, int customerId, float cost, String timeRequested,
      String timeConfirmed, String timeDelivered, String timeCompleted, int orderStateId) {
    assignAttributes(id, orderDeviceId, customerId, cost, timeRequested, timeConfirmed,
        timeDelivered, timeCompleted, orderStateId);
  }

  /**
   * Constructor for the order class that sets every attribute to 0 or null
   * (except the list of orders which is initialised to an empty list).
   */
  // public Order() {
  // assignAttributes(0, 0, 0, 0, null, null, null, null, 0);
  // }

  /**
   * Constructor that is most likely going to be used for Order.
   * 
   * @param orderDeviceId
   *          The ID of the device that is being used to create the order
   * @param customerId
   *          The ID of the customer placing the order
   */
  public Order(int orderDeviceId, int customerId) {
    assignAttributes(0, orderDeviceId, customerId, 0, null, null, null, null, 0);
  }

  /**
   * Assigns the attributes in the order the specified arguments.
   * 
   * @param id
   *          The ID of the order
   * @param orderDeviceId
   *          The ID of the device that is being used to create the order
   * @param customerId
   *          The ID of the customer placing the order
   * @param cost
   *          The total cost of the order
   * @param timeRequested
   *          The time that the order is requested by the customer
   * @param timeConfirmed
   *          The time that the order is confirmed by the waiter
   * @param timeDelivered
   *          The time that the order is delivered to the table by the waiter
   * @param timeCompleted
   *          The time that the order is completed by the kitchen staff
   * @param orderStateId
   *          The state of the order (i.e. whether the order has been requested,
   *          delivered, etc.)
   */
  private void assignAttributes(int id, int orderDeviceId, int customerId, float cost,
      String timeRequested, String timeConfirmed, String timeDelivered, String timeCompleted,
      int orderStateId) {
    this.id = new SimpleIntegerProperty(id);
    this.orderDeviceId = new SimpleIntegerProperty(orderDeviceId);
    this.customerId = new SimpleIntegerProperty(customerId);
    this.cost = new SimpleFloatProperty(cost);
    this.timeRequested = new SimpleStringProperty(timeRequested);
    this.timeConfirmed = new SimpleStringProperty(timeConfirmed);
    this.timeDelivered = new SimpleStringProperty(timeDelivered);
    this.timeCompleted = new SimpleStringProperty(timeCompleted);
    this.orderStateId = new SimpleIntegerProperty(orderStateId);
    this.dishList = FXCollections.observableArrayList();
    this.httpRequester = new HttpRequester();
  }

  /**
   * Gets ID of order.
   * 
   * @return ID of order
   */
  public int getId() {
    return id.get();
  }

  /**
   * Sets ID of order.
   * 
   * @param id
   *          ID of order
   */
  public void setId(int id) {
    this.id.set(id);
  }

  /**
   * Gets ID of device used to request the order.
   * 
   * @return ID of device used to request the order
   */
  public int getOrderDeviceId() {
    return orderDeviceId.get();
  }

  /**
   * Sets the ID of the device used to request the order.
   * 
   * @param orderDeviceId
   *          ID of device used to request the order
   */
  public void setOrderDeviceId(int orderDeviceId) {
    this.orderDeviceId.set(orderDeviceId);
  }

  /**
   * Gets the ID of the customer that requested the order.
   * 
   * @return ID of the customer that requested the order
   */
  public int getCustomerId() {
    return customerId.get();
  }

  /**
   * Sets the ID of the customer that requested the order.
   * 
   * @param customerId
   *          ID of the customer that requested the order
   */
  public void setCustomerId(int customerId) {
    this.customerId.set(customerId);
  }

  /**
   * Gets the total cost of the order.
   * 
   * @return Total cost of the order
   */
  public float getCost() {
    return cost.get();
  }

  /**
   * Sets the total cost of the order.
   * 
   * @param cost
   *          Total cost of the order
   */
  public void setCost(float cost) {
    this.cost.set(cost);
  }

  /**
   * Gets the time that the customer requested the order.
   * 
   * @return Time that the customer requested the order
   */
  public String getTimeRequested() {
    return this.timeRequested.get();
  }

  /**
   * Sets the time that the customer requested the order.
   * 
   * @param timeRequested
   *          Time that the customer requested the order
   */
  public void setTimeRequested(String timeRequested) {
    this.timeRequested.set(timeRequested);
  }

  /**
   * Gets the time that the waiter confirmed the order.
   * 
   * @return Time that the waiter confirmed the order
   */
  public String getTimeConfirmed() {
    return this.timeConfirmed.get();
  }

  /**
   * Sets the time that the waiter confirmed the order.
   * 
   * @param timeConfirmed
   *          Time that the waiter confirmed the order
   */
  public void setTimeConfirmed(String timeConfirmed) {
    this.timeConfirmed.set(timeConfirmed);
  }

  /**
   * Gets the time that the waiter delivered the order.
   * 
   * @return Time that the waiter delivered the order
   */
  public String getTimeDelivered() {
    return this.timeDelivered.get();
  }

  /**
   * Sets the time that the waiter delivered the order.
   * 
   * @param timeDelivered
   *          Time that the waiter delivered the order
   */
  public void setTimeDelivered(String timeDelivered) {
    this.timeDelivered.set(timeDelivered);
  }

  /**
   * Gets the time that the kitchen staff completed preparing the order.
   * 
   * @return Time that the kitchen staff completed preparing the order
   */
  public String getTimeCompleted() {
    return this.timeCompleted.get();
  }

  /**
   * Sets the time that the kitchen staff completed preparing the order.
   * 
   * @param timeCompleted
   *          Time that the kitchen staff completed preparing the order
   */
  public void setTimeCompleted(String timeCompleted) {
    this.timeCompleted.set(timeCompleted);
  }

  /**
   * Gets the state of the order (e.g. placed, delivered).
   * 
   * @return State of the order
   */
  public int getOrderStateId() {
    return this.orderStateId.get();
  }

  /**
   * Sets the state of the order 9e.g. placed, delivered).
   * 
   * @param orderStateId
   *          State of the order
   */
  public void setOrderStateId(int orderStateId) {
    this.orderStateId.set(orderStateId);
  }

  /**
   * Gets the list of dishes that are added to the order.
   * 
   * @return List of dishes that have been added to the order
   */
  public ObservableList<ViewMenuDishes> getDishList() {
    return dishList;
  }

  /**
   * Adds <code>dish</code> to the local list of dishes in the order.
   * 
   * @param dish
   *          The dish to be added to the list of dishes for the order
   */
  private void addToDishList(ViewMenuDishes dish) {
    this.getDishList().add(dish);
  }

  /**
   * Deletes <code>dish</code> from the local list of dishes in the order.
   * 
   * @param dish
   *          The dish to be removed from the list of dishes for the order
   */
  public void deleteFromDishList(ViewMenuDishes dish) {
    this.getDishList().remove(dish);
  }

  /**
   * Adds the dish given as an argument to the order both locally and on the
   * database.
   * 
   * @param dish
   *          The dish to be added to the order
   */
  public void addDish(ViewMenuDishes dish) {
    // If number of dishes in order is 0
    if (getDishList().size() == 0) {
      try {
        // Insert order into database
        insertOrderIntoDb(dish);
      } catch (MalformedURLException exception) {
        System.out.println("Server error: Incorrect URL");
        return;
      } catch (IOException exception) {
        System.out.println("Server error: Connection error");
        return;
      }

      try {
        // Add dish to order in database
        insertIntoOrderDishesInDb(dish);
      } catch (MalformedURLException exception) {
        System.out.println("Server error: Incorrect URL");
        return;
      } catch (IOException exception) {
        System.out.println("Server error: Connection error");
        return;
      }

      // Add to local list of dishes in order
      this.addToDishList(dish);
    } else if (this.getOrderStatusFomDb() == 1) {
      try {
        // Increases cost of order in database
        increaseTotalCostInDb(dish);
      } catch (MalformedURLException exception) {
        System.out.println("Server error: Incorrect URL");
        return;
      } catch (IOException exception) {
        System.out.println("Server error: Connection error");
        return;
      }

      try {
        // Add dish to order in database
        insertIntoOrderDishesInDb(dish);
      } catch (MalformedURLException exception) {
        System.out.println("Server error: Incorrect URL");
        return;
      } catch (IOException exception) {
        System.out.println("Server error: Connection error");
        return;
      }

      // Add dish to local list of dishes in order
      this.addToDishList(dish);
    }
  }

  /**
   * Deletes the dish given as an argument from the order both locally and on
   * the database.
   * 
   * @param dish
   *          The dish to be added to the order
   */
  public void deleteDish(ViewMenuDishes dish) {
    // Check status of order to see if the customer is allowed to change the
    // order
    if (this.getOrderStatusFomDb() == 1) {
      try {
        // Delete dish from the order on the database
        deleteDishFromOrderInDb(dish);
      } catch (MalformedURLException exception) {
        System.out.println("Server error: Incorrect URL");
        return;
      } catch (IOException exception) {
        System.out.println("Server error: Connection error");
        return;
      }

      if (getDishList().size() == 1) {
        try {
          // Delete order from database if no dishes are in the order
          deleteOrderFromDb();
        } catch (MalformedURLException exception) {
          System.out.println("Server error: Incorrect URL");
          return;
        } catch (IOException exception) {
          System.out.println("Server error: Connection error");
          return;
        }
      } else {
        try {
          // Decrease total cost in database if there are still dishes within
          // the order when the dish argument is deleted from the list of orders
          decreaseTotalCostInDb(dish);
        } catch (MalformedURLException exception) {
          System.out.println("Server error: Incorrect URL");
          return;
        } catch (IOException exception) {
          System.out.println("Server error: Connection error");
          return;
        }
      }

      // Delete dish from local list of dishes for the order
      this.deleteFromDishList(dish);
    }
  }

  /**
   * Inserts order into database.
   * 
   * @param dish
   *          Dish to be added to order
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void insertOrderIntoDb(ViewMenuDishes dish) throws MalformedURLException, IOException {
    // Gets current time stamp
    Calendar calendar = Calendar.getInstance();
    Date now = calendar.getTime();
    Timestamp currentTimeStamp = new Timestamp(now.getTime());
    String currentTime = currentTimeStamp.toString().substring(0, 19);

    setTimeRequested(currentTime);
    setOrderStateId(1);

    String sql = "INSERT INTO orders(cost, time_requested, time_confirmed, time_delivered, "
        + "customer_id, order_state_id, order_device_id, time_completed) VALUES ("
        + Float.toString(dish.getPrice()) + ", '" + currentTime + "', null, null, "
        + getCustomerId() + ", " + getOrderStateId() + ", " + getOrderDeviceId() + ", null);";

    httpRequester.sendPost("/database/insertRow", sql);

    setId(getOrderIdFromDb(currentTime));
  }

  /**
   * Gets the ID of the order from the database.
   * 
   * @param currentTime
   *          The current time in the format '01:02:03'
   * @return ID of the order from the database
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private int getOrderIdFromDb(String currentTime) throws MalformedURLException, IOException {
    String sql = "SELECT id FROM orders WHERE orders.customer_id = " + this.getCustomerId()
        + " AND orders.order_device_id = " + this.getOrderDeviceId()
        + " AND orders.time_requested = '" + currentTime + "';";

    String[] dish = httpRequester.sendPost("/database/select", sql).split("\n");

    String idString = dish[0].split(" ")[0];
    System.out.println("DISH: " + idString);

    return Integer.parseInt(idString);
  }

  /**
   * Increases the total cost of the order within the database by the cost of
   * the <code>dish</code> argument.
   * 
   * @param dish
   *          The cost of this dish is added to the cost of the order
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void increaseTotalCostInDb(ViewMenuDishes dish)
      throws MalformedURLException, IOException {
    String sql = "UPDATE orders SET cost = cost + " + Float.toString(dish.getPrice())
        + " WHERE id = " + Integer.toString(this.getId()) + ";";
    System.out.println(sql);
    httpRequester.sendPost("/database/updateRow", sql);

  }

  /**
   * Adds the <code>dish</code> argument to the order in the database.
   * 
   * @param dish
   *          The dish to be added to the order in the database
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void insertIntoOrderDishesInDb(ViewMenuDishes dish)
      throws MalformedURLException, IOException {
    String sql = "INSERT INTO order_dishes(dish_id, order_id) VALUES ("
        + Integer.toString(dish.getId()) + ", " + Integer.toString(this.getId()) + ");";

    httpRequester.sendPost("/database/insertRow", sql);
  }

  /**
   * Deletes the <code>dish</code> argument from the order in the database.
   * 
   * @param dish
   *          The dish to be deleted from the order in the database
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void deleteDishFromOrderInDb(ViewMenuDishes dish)
      throws MalformedURLException, IOException {
    String sql = "DELETE FROM order_dishes WHERE ctid IN (SELECT ctid FROM order_dishes"
        + " WHERE order_dishes.dish_id = " + Integer.toString(dish.getId())
        + " AND order_dishes.order_id = " + Integer.toString(this.getId()) + " LIMIT 1);";

    httpRequester.sendPost("/database/deleteRow", sql);
  }

  /**
   * Deletes the order from the database.
   * 
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void deleteOrderFromDb() throws MalformedURLException, IOException {
    String sql = "DELETE FROM orders WHERE orders.id = " + Integer.toString(this.getId()) + ";";

    httpRequester.sendPost("/database/deleteRow", sql);
  }

  /**
   * Decreases the total cost of the order within the database by the cost of
   * the <code>dish</code> argument.
   * 
   * @param dish
   *          The cost of this dish is subtracted from the cost of the order
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   */
  private void decreaseTotalCostInDb(ViewMenuDishes dish)
      throws MalformedURLException, IOException {
    String sql = "UPDATE orders SET cost = cost - " + Float.toString(dish.getPrice())
        + " WHERE id = " + Integer.toString(this.getId()) + ";";

    httpRequester.sendPost("/database/updateRow", sql);
  }

  /**
   * Gets the order status of the order from the database and assigns the
   * current local order status the order status from the database.
   * 
   * @return The order state of the current order or -1 if there was an error
   */
  private int getOrderStatusFomDb() {
    String sql = "SELECT order_state_id FROM orders WHERE orders.id = "
        + Integer.toString(this.getId()) + ";";
    try {
      String[] orderStates = httpRequester.sendPost("/database/select", sql).split("\n");
      String orderStateString = orderStates[0].split(" ")[0];
      Integer orderState = Integer.parseInt(orderStateString);
      this.setOrderStateId(orderState);
      return orderState;
    } catch (MalformedURLException exception) {
      System.out.println("Server error: Incorrect URL");
    } catch (IOException exception) {
      System.out.println("Server error: Connection error");
    } catch (NumberFormatException exception) {
      System.out.println("Error converting database result (order_id) to integer");
    }
    return -1;
  }
}