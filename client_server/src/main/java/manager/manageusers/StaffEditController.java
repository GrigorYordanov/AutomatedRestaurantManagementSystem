package manager.manageusers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import utilities.Users;

/**
 * This class is the controller for the edit of the staff members. It handles
 * the button clicks.
 * 
 * @author Darren
 *
 */
public class StaffEditController {
  private String[] staff;
  private Staff thisManager;

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private ComboBox<String> staffSelection;
  @FXML private TextField staffId;
  @FXML private TextField username;
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private TextField number;
  @FXML private TextField address;
  @FXML private TextField jobId;
  @FXML private TextField permissionLvl;
  @FXML private TextField email;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;
  @FXML private Button editUser;
  @FXML private Button deleteUser;

  @FXML
  void deleteuserProfile(MouseEvent event) {
    deleteStaff();
  }

  @FXML
  void editUserProfile(MouseEvent event) {
    updateUser();
    getAllStaff();
  }

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/manageusers/ManagerChoice.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manage Users");
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/back-arrow3.png"));
    usrIcon.setImage(new Image("file:res/images/user3.png"));

    // get staff from database
    getAllStaff();

    // add listener to combobox
    staffSelection.valueProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(
          @SuppressWarnings("rawtypes") ObservableValue objProperty,
          String previousValue, String newValue) {
        // get information about new staff selected
        String[] selectedValue = newValue.split(" ");
        String[] staffDetails = Users.getStaffDetails(selectedValue);
        changeInformation(staffDetails);
      }

    });
  }

  private void changeInformation(String[] details) {
    String id = details[0];
    this.staffId.setText(id);

    String username = details[1];
    this.username.setText(username);

    String email = details[2];
    this.email.setText(email);

    String[] fullName = details[4].split("_");
    String firstName = fullName[0];
    String lastName = fullName[1];
    this.firstName.setText(firstName);
    this.lastName.setText(lastName);

    String number = details[5];
    this.number.setText(number);

    String address = details[6];
    this.address.setText(address);

    String jobId = details[7];
    this.jobId.setText(jobId);

    String permissionLvl = details[8];
    this.permissionLvl.setText(permissionLvl);
  }

  private void updateUser() {
    Users.updateStaffDetails(staffId.getText(), username.getText(),
        email.getText(), firstName.getText() + "_" + lastName.getText(),
        number.getText(), address.getText(), jobId.getText(),
        permissionLvl.getText());
  }

  private void getAllStaff() {
    staff = Users.getStaffIdName();
    staffSelection.getItems().addAll(staff);
  }

  private void deleteStaff() {
    if (this.thisManager.getUserName().equals(username.getText())) {
      //trying to delete yourself?
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("You cannot delete yourself!");
      alert.showAndWait();
    } else {
      Users.deleteStaff(staffId.getText());
      getAllStaff();
      clearTextFields();
    }
  }

  private void clearTextFields() {
    staffId.clear();
    username.clear();
    firstName.clear();
    lastName.clear();
    address.clear();
    number.clear();
    jobId.clear();
    permissionLvl.clear();
    email.clear();
  }
  
  /**
   * This method is used to pass in data to the controller from other guis.
   * 
   * @param thisUser
   *          The manager logged in
   */
  public void initData(Staff thisUser) {
    this.thisManager = thisUser;
  }
}
