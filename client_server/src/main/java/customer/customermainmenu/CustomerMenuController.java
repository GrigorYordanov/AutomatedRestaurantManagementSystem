package customer.customermainmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Navigation;

/**
 * This class is the controller for the Customer main menu, which handles all
 * the mouse clicks.
 * 
 * @author Darren
 *
 */
public class CustomerMenuController {

  private int customerId;
  private int tabletId;

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Button orderButton;
  @FXML private Button payButton;
  @FXML private ImageView logOutButton;

  @FXML
  void logOff(MouseEvent event) {
    Navigation.navigateTo("/home/login/CustomerLoginPage.fxml",
        (Stage) orderButton.getScene().getWindow(), "Customer Login");
  }

  @FXML
  void navigateToOrder(MouseEvent event) {
    Navigation.navigateToOrder("/customer/orderapplication/CustOrder.fxml",
        (Stage) orderButton.getScene().getWindow(), this.customerId,
        this.tabletId, "Customer Ordering");
  }

  @FXML
  void navigateToPay(MouseEvent event) {
    Navigation.navigateToCustomer("/customer/paymentscreen/PaymentScreen.fxml",
        (Stage) payButton.getScene().getWindow(), this.customerId,
        this.tabletId, "Payment Screen");
  }

  @FXML
  void initialize() {
    //Updated code to make sure image loads
    logOutButton.setImage(new Image("file:res/images/ic_power_settings_new_black_48dp_2x.png"));
  }

  /**
   * This method is used to pass in data to the controller from other guis.
   * 
   * @param customerId
   *          the ID of the logged in customer
   * @param tabletId
   *          the Id of the tablet in use
   */
  public void initData(int customerId, int tabletId) {
    this.customerId = customerId;
    this.tabletId = tabletId;
  }
}
