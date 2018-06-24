/**
  * The package 'employeedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view employee data of the restaurant.
  */

package manager.employeedata;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Navigation;

/**
 * The class 'EmployeeDataController' is used to fill the FXML file with data
 * from the 'Staff' class.
 * @author Svetoslav Mihovski
 */
public class EmployeeDataController
    implements Initializable {

  //Injecting values from FXML loader

  @FXML private TableView<Staff> dataTable;
  @FXML private TableColumn<Staff,String> jobposColumn;
  @FXML private TableColumn<Staff,String> nameColumn;
  @FXML private TableColumn<Staff,String> usernameColumn;
  @FXML private TableColumn<Staff,String> emailColumn;
  @FXML private TableColumn<Staff,String> telephoneColumn;
  @FXML private TableColumn<Staff,String> addressColumn;
  @FXML private TableColumn<Staff,Integer> ordersColumn;
  @FXML private TableColumn<Staff,Float> salesColumn;
   
  /**
   * Method used to load the FXML file with data from the database,
   * when the main method is started.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));

    jobposColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("jobPosition"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("fullName"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("userName"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
    telephoneColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("telephoneNumber"));
    addressColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("address"));
    ordersColumn.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("ordersCompleted"));
    salesColumn.setCellValueFactory(new PropertyValueFactory<Staff, Float>("salesFromOrders"));

    Staff staff = new Staff();
    ObservableList<Staff> allStaff;

    try {
      allStaff = staff.getAllStaff();
      dataTable.getItems().setAll(allStaff);
      
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
  
  //-----Darren Code-----//
  @FXML
  private ImageView backButton;
  
  private Staff thisManager;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manager Main Menu");
  }
  
  public void initData(Staff user) {
    this.thisManager = user;
  }

}