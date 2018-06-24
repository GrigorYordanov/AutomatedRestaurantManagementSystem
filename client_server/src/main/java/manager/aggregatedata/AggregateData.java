 
/**
  * The package 'aggregatedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view aggregate data of the restaurant.
  */

package manager.aggregatedata;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.MalformedURLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;

/**
 * The 'AggregateData' class is used to create 'aggData' objects with data from the database.
 * @author Svetoslav Mihovski
 */
public class AggregateData {

  //Private fields of the class used to create the object.
  private SimpleStringProperty waiterName;
  private SimpleIntegerProperty orders;
  private SimpleFloatProperty sales;
  private SimpleStringProperty timeConfirmed;
  private SimpleStringProperty timeDelivered;
  private SimpleStringProperty timeCompleted;
  
  public AggregateData() {

  }
  
  /**
   * Constructor of 'AggregateData' class is used to initialize the fields in the database such as:
   * @param waiterName - the full name of a waiter.
   * @param orders - the orders that a waiter has confirmed.
   * @param sales - the sales that a waiter has made.
   * @param timeConfirmed - the time when a waiter confirmed an order.
   * @param timeDelivered - the time when a delivered confirmed an order.
   * @param timeCompleted - the time when an order was completed.
   */
  public AggregateData(String waiterName, Integer orders, Float sales, String timeConfirmed,
      String timeDelivered, String timeCompleted) {

    // Assign fields
    this.waiterName = new SimpleStringProperty(waiterName);
    this.orders = new SimpleIntegerProperty(orders);
    this.sales = new SimpleFloatProperty(new BigDecimal(sales).round(
    new MathContext(3)).floatValue());
    this.timeConfirmed = new SimpleStringProperty(timeConfirmed);
    this.timeDelivered = new SimpleStringProperty(timeDelivered);
    this.timeCompleted = new SimpleStringProperty(timeCompleted);
  }
  
  /**
   * Returns a list of all of specific aggregate data of the restaurant from the database.
   * 
   * @return a list of information about a specific aggregate data of the restaurant.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<AggregateData> getAllAggregateData(String cretariaDataFrom,
         String cretariaDataTo) throws MalformedURLException, IOException {

    HttpRequester httpRequester = new HttpRequester();
    ObservableList<AggregateData> allAggData = FXCollections.observableArrayList();

    String sqlCommand = "SELECT staff.full_name, COUNT(orders.id) AS waiter_orders,"
            + " AVG(orders.cost) AS waiter_cost, "
            + " AVG((orders.time_confirmed::timestamp(0)"
            + " - orders.time_requested::timestamp(0))) AS time_confirmed,"
            + " AVG((orders.time_delivered::timestamp(0)"
            + " - orders.time_confirmed::timestamp(0))) AS time_delivered,"
            + " AVG((orders.time_completed::timestamp(0)"
            + " - orders.time_delivered::timestamp(0))) AS time_completed"
            + " FROM orders"
            + " INNER JOIN public.order_waiter ON public.orders.id = public.order_waiter.order_id"
            + " INNER JOIN public.staff ON public.order_waiter.waiter_id = public.staff.id"
            + " WHERE (orders.time_requested BETWEEN '" + cretariaDataFrom 
            + " ' AND ' " + cretariaDataTo + "')"
            + " AND orders.order_state_id = 5"
            + " GROUP BY staff.id;";
    
    // EACH LINE == one 'aggData' object
    String[] aggData = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    
    if (!aggData[0].equals("")) {
      
      for (String data : aggData) {

        // Split up and pass into a 'singleAggData' object
        String[] singleAggData = data.split(" ");

        if (singleAggData[3].equals("null")) {
          singleAggData[3] = "00:00:00";
        }
        if (singleAggData[4].equals("null")) {
          singleAggData[4] = "00:00:00";
        }
        if (singleAggData[5].equals("null")) {
          singleAggData[5] = "00:00:00";
        }

        AggregateData tempAggData = new AggregateData(
            singleAggData[0],
            Integer.parseInt(singleAggData[1]),
            Float.parseFloat(singleAggData[2]),
            singleAggData[3].substring(0, 8),
            singleAggData[4].substring(0, 8),
            singleAggData[5].substring(0, 8)
            );
        allAggData.add(tempAggData);
      }
      
    } else {
      allAggData.add(new AggregateData("",0,0f,"00:00:00","00:00:00","00:00:00"));
    }
    return allAggData;     
  }
  
  //Set of getters and setters for the private fields.
  
  public String getWaiterName() {
    return waiterName.get();
  }
  
  public void averageOrders(ObservableList<AggregateData> allAggData) {
    System.out.println(allAggData.get(3));
  }
  
  public void setWaiterName(String waiterName) {
    this.waiterName.set(waiterName);
  }

  public Integer getOrders() {
    return orders.get();
  }
  
  public void setOrders(Integer orders) {
    this.orders.set(orders);
  }
  
  public Float getSales() {
    return sales.get();
  }
  
  public void setSales(Float sales) {
    this.sales.set(sales);
  }
  
  public String getTimeConfirmed() {
    return timeConfirmed.get();
  }

  public void setTimeConfirmed(String timeConfirmed) {
    this.timeConfirmed.set(timeConfirmed);
  }
  
  public String getTimeDelivered() {
    return this.timeDelivered.get();
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
  
}