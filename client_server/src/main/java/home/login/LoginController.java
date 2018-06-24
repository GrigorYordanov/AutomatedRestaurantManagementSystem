package home.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utilities.Navigation;

/**
 * This class is the controller for the main start page of the application. it
 * handles the button clicks.
 * 
 * @author Darren
 *
 */
public class LoginController {

  //-------GUI Controls----------//
  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private HBox staffBox;
  @FXML private Button staffLoginButton;
  @FXML private HBox customerBox;
  @FXML private Button customerLoginButton;

  @FXML
  void customerHover(MouseEvent event) {
    alterControl(customerBox);
  }

  @FXML
  void staffHovered(MouseEvent event) {
    alterControl(staffBox);
  }

  @FXML
  void staffExitHover(MouseEvent event) {
    staffBox.setEffect(null);
  }

  @FXML
  void customerExitHover(MouseEvent event) {
    customerBox.setEffect(null);
  }

  @FXML
  void customerLogin(MouseEvent event) {    
    Navigation.navigateTo("/home/login/CustomerLoginPage.fxml",
        (Stage) customerLoginButton.getScene().getWindow(), "Customer Login");
  }

  @FXML
  void goToStaffLogin(MouseEvent event) {   
    Navigation.navigateTo("/home/login/StaffLogin.fxml",
        (Stage) staffLoginButton.getScene().getWindow(), "Staff Login");
  }

  @FXML
  void initialize() {
  }

  private void alterControl(HBox target) {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(5.0);
    dropShadow.setWidth(10.0);
    dropShadow.setHeight(10.0);
    dropShadow.setOffsetX(1.0);
    dropShadow.setOffsetY(1.0);

    target.setEffect(dropShadow);
  }
}
