package customer.paymentscreen;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.httprequest.HttpRequester;
import utilities.Navigation;

/**
 * This controller is used to control the reciept screen.
 * 
 * @author Darren
 *
 */
public class ReceiptController {

  private HttpRequester requester;
  private int userId;
  private int tableId;
  private ArrayList<String> dishes = new ArrayList<String>();
  private double totalPrice;

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Label mainBody;
  @FXML private ImageView backButton;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToCustomer("/customer/customermainmenu/CustomerMainMenu.fxml",
        (Stage) backButton.getScene().getWindow(), this.userId,
        this.tableId, "Customer Menu");
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton
        .setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
  }

  private void printTotal() {
    for (String item : dishes) {
      String[] dish = item.split(" ");
      try {
        totalPrice = totalPrice + Double.parseDouble(dish[1]);
      } catch (NumberFormatException nfE) {
        totalPrice = 0;
      }
    }

    mainBody
        .setText(mainBody.getText() + "\n-----------------------\nTOTAL PAID : "
            + NumberFormat.getCurrencyInstance().format(totalPrice));
  }

  private void displayDishes() {
    for (String item : dishes) {
      String[] dish = item.split(" ");

      for (String info : dish) {
        mainBody.setText(mainBody.getText() + "    " + info);
      }
    }
    
    printTotal();
  }

  private void getOrderDetails() {
    requester = new HttpRequester();
    
    //Query to get all delivered orders
    String sqlCommand = "SELECT id FROM orders WHERE customer_id=" + this.userId
        + " AND order_state_id=4";

    try {
      String[] ordersForPerson = requester
          .sendPost("/database/select", sqlCommand).split("\n");
      
      //the check if there is anything to pay for
      if (ordersForPerson[0].equals("")) {
        dishes.add("You have no orders waiting to be paid for!");
        
      } else {
        //go through each order and get the dish id's
        for (String orderId : ordersForPerson) {
          System.out.println(orderId);
          sqlCommand = "SELECT dish_id FROM order_dishes WHERE order_id="
              + orderId;
          String[] dishForPerson = requester
              .sendPost("/database/select", sqlCommand).split("\n");

          //go through each dish and get teh cost and name
          for (String dishId : dishForPerson) {
            sqlCommand = "SELECT name, cost FROM dishes WHERE id=" + dishId;
            dishes.add(requester.sendPost("/database/select", sqlCommand));
          }
          
          //make this order paid for
          sqlCommand = "UPDATE orders SET order_state_id=5 WHERE id=" + orderId;
          System.out.println(sqlCommand);
          requester.sendPost("/database/updateRow", sqlCommand);
        }
      }

    } catch (IOException ioE) {
      System.out.println("Reciept Error");
    }
  }

  /**
   * This method is used to pass in variables to the controller for use in the
   * GUI.
   * 
   * @param customerId
   *          ID of the customer paying
   * @param tableId
   *          The table ID of where there orders went to
   */
  public void initData(int customerId, int tableId) {
    setUserId(customerId);
    this.tableId = tableId;
    setUpScreen();
  }

  private void setUserId(int id) {
    this.userId = id;
    mainBody.setText("\nCustomer ID: " + userId + "\n");
  }

  private void setUpScreen() {
    getOrderDetails();
    displayDishes();
  }

}
