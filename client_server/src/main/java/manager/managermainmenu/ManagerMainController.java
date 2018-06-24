package manager.managermainmenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.editmenu.ManagerMenu;
import manager.employeedata.Staff;
import utilities.Navigation;

/**
 * This class is the controller for the manger main menu, it handles the button
 * clicks.
 * 
 * @author Darren
 *
 */
public class ManagerMainController {
  private Staff thisManager;

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private ImageView logoutButton;
  @FXML private Button realTimeButton;
  @FXML private Button manageUserButton;
  @FXML private Button employeeData;
  @FXML private Button aggregateData;
  @FXML private Button editMenuButton;
  @FXML private Button editIngredientButton;
  @FXML private Button setPriceButton;
  @FXML private Button addToMenuButton;

  @FXML
  void initialize() {
    // Updated code to make sure image loads
    logoutButton.setImage(
        new Image("file:res/images/ic_power_settings_new_black_48dp_2x.png"));
  }

  @FXML
  void editIngredientClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/editmenu/AddIngredientView.fxml",
        (Stage) editIngredientButton.getScene().getWindow(), this.thisManager, "Add Ingredients");
  }
  
  @FXML
  void addDishClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/editMenu/AddDishView.fxml",
        (Stage) addToMenuButton.getScene().getWindow(), this.thisManager,
        "Add Dish");
  }

  @FXML
  void editMenuClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/editmenu/EditMenuView.fxml",
        (Stage) editMenuButton.getScene().getWindow(), this.thisManager, "Edit Menu");
  }
  
  @FXML
  void aggregateDataClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/aggregatedata/AggregateData.fxml",
        (Stage) aggregateData.getScene().getWindow(), this.thisManager, "Aggregate Data");
  }

  @FXML
  void employeerDataClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/employeedata/EmployeeData.fxml",
        (Stage) employeeData.getScene().getWindow(), this.thisManager, "Employee Data");
  }

  @FXML
  void manageUserClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/manageusers/ManagerChoice.fxml",
        (Stage) manageUserButton.getScene().getWindow(), this.thisManager, "Manage Users");
  }

  @FXML
  void realTimeClick(MouseEvent event) {
    Navigation.navigateToStaff("/manager/realtimedata/RealtimeData.fxml",
        (Stage) realTimeButton.getScene().getWindow(), this.thisManager, "Realtime Data");
  }

  @FXML
  void hoverEffect(MouseEvent event) {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(5.0);
    dropShadow.setWidth(10.0);
    dropShadow.setHeight(10.0);
    dropShadow.setOffsetX(1.0);
    dropShadow.setOffsetY(1.0);

    ((Node) event.getSource()).setEffect(dropShadow);
  }

  @FXML
  void hoverExitEffect(MouseEvent event) {
    ((Node) event.getSource()).setEffect(null);
  }

  @FXML
  void logout(MouseEvent event) {
    Navigation.navigateTo("/home/login/StaffLogin.fxml",
        (Stage) logoutButton.getScene().getWindow(), "Staff Login");
  }

  @FXML
  void setPriceClick(MouseEvent event) {
    // navigate to set price
    ManagerMenu setPriceView = new ManagerMenu(this.thisManager);
    try {
      setPriceView.start((Stage) setPriceButton.getScene().getWindow());
    } catch (IOException ioE) {
      System.out.println("Could not load the Set Price Page");
    }
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
