package home.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import utilities.Navigation;
import utilities.StaffRoles;
import utilities.Users;

/**
 * This class is the controller for the staff login page. It handles all the
 * button clicks of the gui.
 * 
 * @author Darren
 *
 */
public class StaffLoginController {

  //------GUI Variables------//
  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField usernameTextbox;
  @FXML private PasswordField passwordTextbox;
  @FXML private ImageView backButton;
  @FXML private ImageView usrIcon;
  @FXML private Button loginButton;

  @FXML
  void applyHoverEffect(MouseEvent event) {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(5.0);
    dropShadow.setWidth(10.0);
    dropShadow.setHeight(10.0);
    dropShadow.setOffsetX(1.0);
    dropShadow.setOffsetY(1.0);

    loginButton.setEffect(dropShadow);
  }

  @FXML
  void exitHoverEffect(MouseEvent event) {
    loginButton.setEffect(null);
  }

  @FXML
  void returnToMain(MouseEvent event) {
    Navigation.navigateTo("/home/login/LoginPage.fxml",
        ((Stage) backButton.getScene().getWindow()), "Restaurant");
  }

  @FXML
  void goToView(MouseEvent event) {
    String username = usernameTextbox.getText();
    String password = passwordTextbox.getText();

    //check for correct login
    if ((Users.userNameIsThere(username, Users.getStaffUsernames()))
        && (Users.passwordAuthentication(username, password, "staff"))) {

      //get staff object and navigate to correct gui
      Staff thisUser = Users.getStaffObject(username);
      whichUser(thisUser);

    } else {
      // tell incorrect entry
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText(
          "Incorrect Login, Check you details again. If you are a Customer"
          + " please login in using the other screen");
      alert.showAndWait();
    }

  }

  private void whichUser(Staff thisUser) {
    if ((Integer.parseInt(thisUser.getJobPosition())) == StaffRoles.MANAGER.getId()) {
      
      Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
          (Stage) loginButton.getScene().getWindow(), thisUser, "Manager Main Menu");

    } else if ((Integer.parseInt(thisUser.getJobPosition())) == StaffRoles.WAITER.getId()) {
      // assign the waiter to available table
      Users.assignWaiterTables(thisUser);
      
      Navigation.navigateToStaff("/waiterorder/Waiter.fxml",
          (Stage) loginButton.getScene().getWindow(), thisUser, "Waiter Application");

    } else {
      Navigation.navigateToStaff("/kitchen/kitchenapp/KitchenView.fxml",
          (Stage) loginButton.getScene().getWindow(), thisUser, "Kitchen");
    }
  }

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    backButton
        .setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    usrIcon.setImage(new Image("file:res/images/userIcon.png"));
  }
}
