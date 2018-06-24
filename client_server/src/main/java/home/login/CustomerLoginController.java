package home.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Customer;
import utilities.Navigation;
import utilities.Users;

/**
 * This class is the controller for the Customer login page, which handles all
 * the button clicks of the gui.
 * 
 * @author Darren
 *
 */
public class CustomerLoginController {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField usernameField;
  @FXML private Label loginButton;
  @FXML private Button registerButton;
  @FXML private Button viewMenuButton;
  @FXML private PasswordField passwordField;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateTo("/home/login/LoginPage.fxml",
        (Stage) backButton.getScene().getWindow(), "Restaurant");
  }

  @FXML
  void cutomerRegister(MouseEvent event) {
    Navigation.navigateTo("/home/login/CustomerRegistration.fxml",
        (Stage) registerButton.getScene().getWindow(), "Customer Registration");
  }

  @FXML
  void logUserIn(MouseEvent event) {
    String[] usernames = Users.getCustomerUserNames();
    String userInput = usernameField.getText();

    if (Users.userNameIsThere(userInput, usernames) && Users.passwordAuthentication(userInput,
        passwordField.getText(), "customer")) {
      // create customer object
      Customer thisUser = Users.getCustomerObject(userInput);
      
      Navigation.navigateToCustomer("/customer/customermainmenu/CustomerMainMenu.fxml",
          (Stage) loginButton.getScene().getWindow(), thisUser.getId(),
          Users.getFreeTable(), "Customer Main Menu");
    } else {
      // not registered
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Incorrect Login, Check you details or register if you are new.");
      alert.showAndWait();
    }

  }

  @FXML
  void viewReadOnlyMenu(MouseEvent event) {
    Navigation.navigateTo("/customer/viewmenu/ViewMenu.fxml",
        (Stage) viewMenuButton.getScene().getWindow(), "Menu");
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton
        .setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    usrIcon.setImage(new Image("file:res/images/userIcon.png"));
  }
}
