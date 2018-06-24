package manager.manageusers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import utilities.Navigation;
import utilities.Registration;
import utilities.StaffRoles;

/**
 * This class is the controller class for the registration of staff members. It
 * handles all the button clicks.
 * 
 * @author Darren
 *
 */
public class StaffRegistrationController {
  
  private Staff thisManager;

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private TextField username;
  @FXML private TextField email;
  @FXML private TextField password;
  @FXML private TextField number;
  @FXML private TextField address;
  @FXML private ComboBox<StaffRoles> role;
  @FXML private Button registerButton;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;

  @FXML
  private void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/manageusers/ManagerChoice.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manage Menu");
  }

  @FXML
  private void registerUser(MouseEvent event) {
    register();

  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/back-arrow.png"));
    usrIcon.setImage(new Image("file:res/images/user.png"));
    
    // fill role combo box with enums
    role.getItems().addAll(StaffRoles.values());
  }

  /**
   * This method will register a user with the information from the form.
   */
  private void register() {
    String username = this.username.getText();
    String email = this.email.getText();
    String password = this.password.getText();
    String fullName = this.firstName.getText() + "_" + this.lastName.getText();
    String telephone = this.number.getText();
    String address = this.address.getText();
    StaffRoles chosenRole = this.role.getValue();

    // use registration class to register staff
    Registration.registerStaff(username, email, password, fullName, telephone,
        address, chosenRole);

    // say registration successfull
    showSuccessMessage();

    // empty all boxes
    clearAllControls();
  }

  /**
   * This method gets rid of all the text in the form.
   */
  private void clearAllControls() {
    this.username.clear();
    this.email.clear();
    this.password.clear();
    this.firstName.clear();
    this.lastName.clear();
    this.number.clear();
    this.address.clear();
  }

  /**
   * This method will show a success method upon successfully adding a new staff
   * memeber.
   */
  private void showSuccessMessage() {
    Alert successAlert = new Alert(AlertType.INFORMATION);
    successAlert.setTitle("Success");
    successAlert.setHeaderText(null);
    successAlert
        .setContentText("You have successfully registered a new Staff member.");
    successAlert.showAndWait();
  }
  
  /**
   * This method is used to pass in data to the controller from other guis.
   * 
   * @param user
   *          The manager logged in
   */
  public void initData(Staff user) {
    this.thisManager = user;
  }
}
