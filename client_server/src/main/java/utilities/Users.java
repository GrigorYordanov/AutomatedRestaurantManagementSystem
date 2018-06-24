package utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.scene.control.Alert.AlertType;
import manager.employeedata.Staff;
import javafx.scene.control.Alert;
import server.httprequest.HttpRequester;

/**
 * This class is a static class to get information and details about all users
 * of the system.
 * 
 * @author Darren
 *
 */
public class Users {

  private static HttpRequester httpService;

  /**
   * This method is used to get all the staff usernames from the database.
   * 
   * @return String[] array of all usernames
   */
  public static String[] getStaffUsernames() {
    String[] toReturn = null;

    httpService = new HttpRequester();

    String sqlCommand = "SELECT username FROM staff";

    try {
      toReturn = httpService.sendPost("/database/select", sqlCommand)
          .split(" \n");
    } catch (MalformedURLException mfE) {
      System.out.println("Malformed URL");
    } catch (IOException ioE) {
      System.out.println("IO Error");
    }

    return toReturn;
  }

  /**
   * This method is used to see if a string is present in an array of strings.
   * 
   * @param username
   *          String Item to find.
   * @param dbUserNames
   *          String place to find it.
   * @return Boolean, true if present else false.
   */
  public static Boolean userNameIsThere(String username, String[] dbUserNames) {

    for (String name : dbUserNames) {
      if (name.equals(username)) {
        return true;
      }
    }

    return false;
  }

  /**
   * This method uses a username and returns a staff object with all information
   * about the staff.
   * 
   * @param username
   *          The username of the staff.
   * @return Staff the staff object from username.
   */
  public static Staff getStaffObject(String username) {
    Staff user = new Staff();
    String[] details;
    String sqlCommand = "SELECT * FROM staff WHERE username='" + username + "'";

    httpService = new HttpRequester();

    try {
      details = httpService.sendPost("/database/select", sqlCommand).split(" ");
      staffInputDetails(user, details);
    } catch (MalformedURLException murlE) {
      System.out.println("Error1");
    } catch (IOException ioE) {
      System.out.println("Error2");
    }

    return user;
  }

  private static void staffInputDetails(Staff user, String[] details) {
    user.setUserName(details[1]);
    user.setEmail(details[2]);
    user.setFullName(details[4]);
    user.setTelephoneNumber(details[5]);
    user.setAddress(details[6]);
    user.setJobPosition(details[7]);
  }

  /**
   * This method is used to get every customer username from the Database.
   * 
   * @return String[] returns all the usernames in an array.
   */
  public static String[] getCustomerUserNames() {
    httpService = new HttpRequester();
    String[] names = null;
    String sqlCommand = "SELECT username FROM customer";

    try {
      names = httpService.sendPost("/database/select", sqlCommand).split(" \n");
    } catch (MalformedURLException murlE) {
      System.out.println("Malformed URL");
    } catch (IOException ioE) {
      System.out.println("IO Error");
    }

    return names;
  }

  /**
   * This method creates a customer object with information about the user.
   * 
   * @param username
   *          the username of the customer.
   * @return Customer object of the customer.
   */
  public static Customer getCustomerObject(String username) {
    Customer user = null;
    String[] details;
    String sqlCommand = "SELECT * FROM customer WHERE username='" + username
        + "'";

    httpService = new HttpRequester();

    try {
      details = httpService.sendPost("/database/select", sqlCommand).split(" ");
      user = new Customer(details);
    } catch (MalformedURLException murlE) {
      System.out.println("Error1");
    } catch (IOException ioE) {
      System.out.println("Error2");
    }

    return user;
  }

  /**
   * This method is used to get all the staff names and ids.
   * 
   * @return String[] list of all names and ID of the staff.
   */
  public static String[] getStaffIdName() {
    String[] toReturn = null;
    httpService = new HttpRequester();

    String sqlCommand = "SELECT id, full_name FROM staff";

    try {
      toReturn = httpService.sendPost("/database/select", sqlCommand)
          .split(" \n");
    } catch (MalformedURLException mfE) {
      System.out.println("Malformed URL");
    } catch (IOException ioE) {
      System.out.println("IO Error");
    }

    return toReturn;
  }

  /**
   * This method is used to get all the staff details from the database.
   * 
   * @param details
   *          Array of the id and full_name.
   * @return String[] All information about the staff.
   */
  public static String[] getStaffDetails(String[] details) {
    String[] staffDetails = null;
    String sqlCommand;
    httpService = new HttpRequester();

    sqlCommand = "SELECT * FROM staff WHERE id=" + details[0]
        + " AND full_name='" + details[1] + "'";

    try {
      staffDetails = httpService.sendPost("/database/select", sqlCommand)
          .split(" ");
    } catch (IOException ioE) {
      System.out.println("Error");
    }

    return staffDetails;
  }

  /**
   * This method updates the database with information about the staff member.
   * 
   * @param id
   *          ID of the staff member.
   * @param username
   *          Username of the staff member.
   * @param email
   *          Email of the staff member.
   * @param fullName
   *          Full name of the staff member.
   * @param number
   *          Phone number of the staff member.
   * @param address
   *          Address of the staff member.
   * @param jobId
   *          Job position of the staff member.
   * @param permissionLvl
   *          Permission level of the staff member.
   */
  public static void updateStaffDetails(String id, String username,
      String email, String fullName, String number, String address,
      String jobId, String permissionLvl) {
    httpService = new HttpRequester();

    String sqlCommand = "UPDATE staff SET username='" + username + "', email='"
        + email + "', full_name='" + fullName + "', telephone_number='" + number
        + "', address='" + address + "', job_position_id=" + jobId
        + ", permission_level=" + permissionLvl + " WHERE id=" + id;

    System.out.println(sqlCommand);

    try {
      httpService.sendPost("/database/updateRow", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error");
    }

  }

  /**
   * This method will delete a staff member from the database.
   * 
   * @param id
   *          Staff members ID.
   */
  public static void deleteStaff(String id) {
    String sqlCommand = "DELETE FROM staff WHERE id=" + id;

    try {
      httpService.sendPost("/database/deleteRow", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error");
    }

  }

  /**
   * This method will return the table id of the tables which are free.
   * 
   * @return int Table id of free table.
   */
  public static int getFreeTable() {
    String sqlCommand = "SELECT id FROM restourant_table WHERE table_status_id = 1";
    httpService = new HttpRequester();
    String[] allFreeTables = null;

    try {
      /*
       * allFreeTables = httpService
       * .sendPost("http://localhost:8080/client_server/cafe/database/select",
       * sqlCommand) .split(" \n");
       */

      allFreeTables = httpService.sendPost("/database/select", sqlCommand)
          .split(" \n");

    } catch (IOException ioE) {
      System.out.println("Error");
    }

    return Integer.parseInt(allFreeTables[0]);
  }

  /**
   * This method is used to assign a waiter to a table when they sign in for
   * work.
   * 
   * @param thisUser
   *          The waiter to be assigned.
   */
  public static void assignWaiterTables(Staff thisUser) {
    // get id of waiter from the parameter
    String id = Users.getStaffId(thisUser);

    // get the tables which have no assignment
    String[] waiterFreeTables = Users.waiterFreeTables();

    if (!waiterFreeTables[0].equals(null)) {
      // assign waiter with first available table
      sqlAssignWaiterTable(waiterFreeTables[0], id);
    } else {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("No Free Tables");
      alert.setContentText("Cannot be assigned a table so I will log you in "
          + "but you will not have a table to look after!");
      alert.showAndWait();
    }
  }

  private static String getStaffId(Staff thisUser) {
    // using the object get return the user name
    httpService = new HttpRequester();

    String toReturn = null;
    String sqlCommand = "SELECT id FROM staff WHERE username='"
        + thisUser.getUserName() + "' AND email='" + thisUser.getEmail() + "'";

    try {
      toReturn = httpService.sendPost("/database/select", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error ID");
    }

    return toReturn;
  }

  private static void sqlAssignWaiterTable(String tableId, String waiterId) {
    System.out.println(tableId + " " + waiterId);
    httpService = new HttpRequester();
    String sqlCommand = "UPDATE restourant_table SET waiter_id=" + waiterId
        + " WHERE id=" + tableId;

    try {
      httpService.sendPost("/database/updateRow", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error here");
    }

  }

  private static String[] waiterFreeTables() {
    httpService = new HttpRequester();
    String sqlCommand = "SELECT id FROM restourant_table WHERE waiter_id IS NULL";

    String[] toReturn = null;

    try {
      toReturn = httpService.sendPost("/database/select", sqlCommand)
          .split("\n");
    } catch (IOException ioE) {
      System.out.println("Error 3");
    }

    return toReturn;
  }

  /**
   * This method is used so when a waiter signs out they will get unassigned
   * from all their tables under their control.
   * 
   * @param thisWaiter
   *          The waiter which is logging out.
   */
  public static void unassignWaiter(Staff thisWaiter) {
    httpService = new HttpRequester();

    // get staff id
    String id = Users.getStaffId(thisWaiter);

    // update database
    String sqlCommand = "UPDATE restourant_table SET waiter_id=NULL WHERE waiter_id="
        + id;

    try {
      httpService.sendPost("/database/updateRow", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error");
    }
  }

  /**
   * This method is used to check the entered password with the stored
   * passwords.
   * 
   * @param username
   *          String the username of the login identity
   * @param password
   *          String the password trying to log in with
   * @param table
   *          String the database table with correct password
   * @return Boolean true upon the input and database password being the same
   */
  public static Boolean passwordAuthentication(String username, String password,
      String table) {
    httpService = new HttpRequester();
    String sqlCommand = "SELECT pass FROM " + table + " WHERE username='"
        + username + "'";
    String[] dbPassword;

    try {
      dbPassword = httpService.sendPost("/database/select", sqlCommand)
          .split(" \n");

      if (dbPassword[0].equals(password)) {
        return true;
      }

    } catch (IOException ioE) {
      System.out.println("Error with Log in");
    }

    return false;
  }

}
