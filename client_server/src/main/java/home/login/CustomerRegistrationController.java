package home.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Navigation;
import utilities.Registration;

/**
 * This class is the controller for the registration gui, it handles all the
 * button clicks of the GUI.
 * 
 * @author Darren
 *
 */
public class CustomerRegistrationController {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private TextField username;
  @FXML private TextField email;
  @FXML private TextField password;
  @FXML private TextField number;
  @FXML private Button registerButton;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateTo("/home/login/CustomerLoginPage.fxml",
        ((Stage) backButton.getScene().getWindow()), "Customer Login");
  }

  @FXML
  void registerUser(MouseEvent event) {
    if (Registration.registerCustomer(username.getText(), email.getText(),
        password.getText(), (firstName.getText() + "_" + lastName.getText()),
        number.getText())) {
      clearScreen();
      
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("You have been registered.");
      alert.showAndWait();
    }else{

      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Error in the details you have entered. ");
      alert.showAndWait();
    }
  }

  private void clearScreen() {
    this.username.clear();
    this.email.clear();
    this.password.clear();
    this.firstName.clear();
    this.lastName.clear();
    this.number.clear();
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/back-arrow.png"));
    usrIcon.setImage(new Image("file:res/images/user.png"));
  }
}
