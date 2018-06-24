 
/**
  * The package 'employeedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view employee data of the restaurant.
  */

package manager.employeedata;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;

/**
 * The 'Staff' class is used to create staff objects 'singleStaff' with data from the database.
 * @author Svetoslav Mihovski
 */
public class Staff {

  //Private fields of the class used to create the object.
  private SimpleStringProperty jobPosition;
  private SimpleStringProperty fullName;
  private SimpleStringProperty userName;
  private SimpleStringProperty email;
  private SimpleStringProperty telephoneNumber;
  private SimpleStringProperty address;
  private SimpleIntegerProperty ordersCompleted;
  private SimpleFloatProperty salesFromOrders;

  /**
   * The constructor of the class used to initialize the variables.
   */
  public Staff() {
    this.jobPosition = new SimpleStringProperty();
    this.fullName = new SimpleStringProperty();
    this.userName = new SimpleStringProperty();
    this.email = new SimpleStringProperty();
    this.telephoneNumber = new SimpleStringProperty();
    this.address = new SimpleStringProperty();
    this.ordersCompleted = new SimpleIntegerProperty();
    this.salesFromOrders = new SimpleFloatProperty();
  }

  /**
   * Constructor of 'Staff' class is used to initialize the fields in the database such as:
   * @param jobPosition - the job position of a staff member.
   * @param fullName - the full name of a staff member.
   * @param userName - the user name of a staff member.
   * @param email - the email of a staff member.
   * @param telephoneNumber - the telephone number of a staff member.
   * @param address - the address of a staff member.
   * @param ordersCompleted - orders completed by a waiter.
   * @param salesFromOrders - sales made by the a waiter.
   */
  public Staff(String jobPosition, String fullName, String userName,
      String email, String telephoneNumber, String address, int ordersCompleted,
      float salesFromOrders) {

    // Assign fields
    this.jobPosition = new SimpleStringProperty(jobPosition);
    this.fullName = new SimpleStringProperty(fullName);
    this.userName = new SimpleStringProperty(userName);
    this.email = new SimpleStringProperty(email);
    this.telephoneNumber = new SimpleStringProperty(telephoneNumber);
    this.address = new SimpleStringProperty(address);
    this.ordersCompleted = new SimpleIntegerProperty(ordersCompleted);
    this.salesFromOrders = new SimpleFloatProperty(salesFromOrders);

  }

  /**
   * Returns a list of all of the specific staff data from the database.
   * 
   * @return a list of information about a staff member.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<Staff> getAllStaff()
      throws MalformedURLException, IOException {

    HttpRequester httpRequester = new HttpRequester();
    ObservableList<Staff> allStaff = FXCollections.observableArrayList();

    String sqlCommand = "SELECT job_position.name, st.full_name, st.username,"
            + " st.email,st.telephone_number, st.address,"
            + " (SELECT COUNT(orders.id) AS countsales"
            + " FROM staff"
            + " LEFT OUTER JOIN restourant_table ON (restourant_table.waiter_id = staff.id)"
            + " LEFT OUTER JOIN client_devices ON (restourant_table.id = client_devices.table_id)"
            + " LEFT OUTER JOIN orders ON (client_devices.id = orders.order_device_id)"
            + " WHERE staff.id = st.id AND orders.order_state_id = 5) AS ordersCompleted,"
            + " (SELECT SUM(orders.cost) AS sumsale"
            + " FROM staff"
            + " LEFT OUTER JOIN restourant_table ON (restourant_table.waiter_id = staff.id)"
            + " LEFT OUTER JOIN client_devices ON (restourant_table.id = client_devices.table_id)"
            + " LEFT OUTER JOIN orders ON (client_devices.id = orders.order_device_id)"
            + " WHERE staff.id = st.id AND orders.order_state_id = 5) AS salesFromOrders"
            + " FROM staff st"
            + " INNER JOIN job_position ON (st.job_position_id = job_position.id);";
    
    // EACH LINE == one 'staff' object
    String[] staff = httpRequester
        .sendPost("/database/select",
            sqlCommand)
        .split("\n");

    for (String employee : staff) {
   
      // split up and pass into a 'singleStaff' object
      String[] singleStaff = employee.split(" ");

      if (singleStaff[6].equals("null")) {
        singleStaff[6] = "0";
      }
      if (singleStaff[7].equals("null")) {
        singleStaff[7] = "0";
      }

      Staff tempStaff = new Staff(
              singleStaff[0],
              singleStaff[1],
              singleStaff[2],
              singleStaff[3],
              singleStaff[4],
              singleStaff[5],
              Integer.parseInt(singleStaff[6]),
              Float.parseFloat(singleStaff[7])
              );
      
      allStaff.add(tempStaff);
    }
    return allStaff;
  }

  //Set of getters and setters for the private fields.
  
  public String getJobPosition() {
    return jobPosition.get();
  }

  public String getFullName() {
    return fullName.get();
  }

  public String getUserName() {
    return userName.get();
  }

  public String getEmail() {
    return email.get();
  }

  public String getTelephoneNumber() {
    return this.telephoneNumber.get();
  }

  public String getAddress() {
    return address.get();
  }

  public Integer getOrdersCompleted() {
    return this.ordersCompleted.get();
  }

  public Float getSalesFromOrders() {
    return this.salesFromOrders.get();
  }

  public void setJobPosition(String jobPosition) {
    this.jobPosition.set(jobPosition);
  }

  public void setFullName(String fullName) {
    this.fullName.set(fullName);
  }

  public void setUserName(String userName) {
    this.userName.set(userName);
  }

  public void setEmail(String email) {
    this.email.set(email);
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber.set(telephoneNumber);
  }

  public void setAddress(String address) {
    this.address.set(address);
  }

  public void setOrdersCompleted(Integer ordersCompleted) {
    this.ordersCompleted.set(ordersCompleted);
  }

  public void setSalesFromOrdsers(Float salesFromOrders) {
    this.salesFromOrders.set(salesFromOrders);
  }

}