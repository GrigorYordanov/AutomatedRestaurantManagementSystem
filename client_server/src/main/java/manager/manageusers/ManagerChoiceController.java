package manager.manageusers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import utilities.Navigation;

/**
 * This class is the controller for the manager choice . It handles all the
 * button clicks.
 * 
 * @author Darren
 *
 */
public class ManagerChoiceController {

  private Staff thisManager;
  
  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Button addUserButton;
  @FXML private Button editUserButton;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manager Main Menu");
  }

  @FXML
  void addNewStaff(MouseEvent event) {
    Navigation.navigateToStaff("/manager/manageusers/StaffRegistration.fxml",
        (Stage) addUserButton.getScene().getWindow(), this.thisManager, "Staff Registration");
  }

  @FXML
  void editCurrentStaff(MouseEvent event) {
    Navigation.navigateToStaff("/manager/manageusers/StaffEdit.fxml",
        (Stage) editUserButton.getScene().getWindow(), this.thisManager, "Edit Staff");
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/back-arrow2.png"));
    usrIcon.setImage(new Image("file:res/images/user2.png"));
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
