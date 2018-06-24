package customer.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.collections.ObservableList;

import org.junit.Before;
import org.junit.Test;

import customer.viewmenu.ViewMenuDishes;

import server.httprequest.HttpRequester;


/**
 * JUnit test class to test the Order class.
 * 
 * @author James Harris
 */
public class TestOrder {

  Order order;

  int customerId;

  int tabletId;

  ViewMenuDishes dish;
  //Dish dish;

  ObservableList<ViewMenuDishes> dishes;

  HttpRequester httpRequester = new HttpRequester();

  /**
   * Sets up the fields before a test is performed.
   * 
   * @throws java.lang.Exception
   *           In case an exception is thrown
   */
  @Before
  public void setUp() throws Exception {
    tabletId = 2;
    customerId = 2;
    order = new Order(tabletId, customerId);
    dish = new ViewMenuDishes();
    dishes = dish.getAllIngredients();
  }

  /**
   * TEST 1: Test to make sure when a new order is created that an order is
   * added to the database.
   */
  @Test
  public void testNumberOfRowsOneDish() {
    final String tableName = "orders";
    final double oldRowCount = getRowCount(tableName);
    order.addDish(dishes.get(0));
    final double newRowCount = getRowCount(tableName);
    assertEquals(
        "TEST1: Test to make sure when a new order is created that an order is added to the DB",
        oldRowCount + 1, newRowCount, 0.0f);
  }

  /**
   * TEST 2: Test to make sure when a new order is created and 2 dishes are
   * added, only 1 order has been added to the database.
   */
  @Test
  public void testNumberOfRowsTwoDishes() {
    final String tableName = "orders";
    final double oldRowCount = getRowCount(tableName);
    order.addDish(dishes.get(0));
    order.addDish(dishes.get(1));
    final double newRowCount = getRowCount(tableName);
    assertEquals(
        "TEST2: Make sure when new order is created with 2 dishes, 1 order is added to the DB",
        oldRowCount + 1, newRowCount, 0.0f);
  }

  /**
   * TEST 3: Test to make sure when a new order is created and 1 dish is added,
   * then 1 deleted, that the row count is the same both before and after these
   * events in the resulting "orders" table.
   * 
   * <p>
   * This also tests whether items have been removed from the order, because if
   * they weren't then the database would throw an error. This is because the
   * attribute <code>order_id</code> in the table <code>order_dishes</code> is a
   * foreign key to the <code>id</code> attribute in the <code>orders</code>
   * table.
   * </p>
   */
  @Test
  public void addThenDelete() {
    final String tableName = "orders";
    final double oldRowCount = getRowCount(tableName);
    ViewMenuDishes dish = dishes.get(0);
    order.addDish(dish);
    order.deleteDish(dish);
    final double newRowCount = getRowCount(tableName);
    assertEquals(
        "TEST3: Make sure when dish is added then deleted once, 0 rows has been left in DB after",
        oldRowCount, newRowCount, 0.0f);
  }

  /**
   * TEST 4: Test to make sure the cost of the order is reduced whenever a dish
   * is removed from the order.
   * 
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   * @throws NumberFormatException
   *           If the cost could not be converted to a value of type float
   */
  @Test
  public void reduceCostWhenRemovingDish()
      throws NumberFormatException, MalformedURLException, IOException {
    ViewMenuDishes dish1 = dishes.get(0);
    ViewMenuDishes dish2 = dishes.get(1);
    order.addDish(dish1);
    order.addDish(dish2);
    float oldCost = this.getCostOfOrder();
    order.deleteDish(dish1);
    float newCost = this.getCostOfOrder();

    assertEquals("TEST4: Make sure cost of order is subtracted in DB when dish removed",
        oldCost - dish1.getPrice(), newCost, 0.0f);
  }

  /**
   * TEST 5: Test to make sure that when a dish is removed from an order that
   * the dish is removed from the local <code>ObservableList</code> of dishes
   * within the <code>Order</code> class.
   */
  @Test
  public void removeDishLocally() {
    ViewMenuDishes dish = dishes.get(0);
    order.addDish(dish);
    int oldNumberOfDishes = order.getDishList().size();
    order.deleteDish(dish);
    int newNumberOfDishes = order.getDishList().size();

    assertEquals(
        "TEST5: Make suredish is removed from local list of dishes in order when removin dish",
        oldNumberOfDishes - 1, newNumberOfDishes);
  }

  /**
   * This method is used to get the total number of rows within a table.
   * 
   * @param table
   *          The table to query when getting the number of rows
   * @return The number of rows that a table has
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   */
  private double getRowCount(String table) {
    String sql = "SELECT count(*) FROM " + table + ";";
    String[] tableItems = null;
    try {
      tableItems = httpRequester
          .sendPost("/database/select", sql).split("\n");
    } catch (MalformedURLException exception) {
      System.out.println("There is an issue with the URL");
      exception.printStackTrace();
      fail();
    } catch (IOException exception) {
      System.out.println("There is an issue with the connection");
      exception.printStackTrace();
      fail();
    }

    return Double.parseDouble(tableItems[0]);

  }

  /**
   * This method is used to find the cost of a specific order within the
   * <code>orders</code> table.
   * 
   * @return The cost of a specific order
   */
  private Float getCostOfOrder() {
    final String sql = "SELECT cost FROM orders WHERE id = " + order.getId() + ";";
    Float cost = null;
    try {
      cost = Float.parseFloat(
          httpRequester.sendPost("/database/select", sql));
    } catch (NumberFormatException exception) {
      System.out.println("There is an issue with converting from String to Float");
      exception.printStackTrace();
      fail();
    } catch (MalformedURLException exception) {
      System.out.println("There is an issue with the URL");
      exception.printStackTrace();
      fail();
    } catch (IOException exception) {
      System.out.println("There is an issue with the connection");
      exception.printStackTrace();
      fail();
    }

    return cost;
  }

}
