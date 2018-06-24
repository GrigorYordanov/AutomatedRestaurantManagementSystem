package utilities;

import java.io.IOException;

import server.httprequest.HttpRequester;

/**
 * This class is used to register users to the database.
 * 
 * @author Darren
 *
 */
public class Registration {

  private static HttpRequester requester;

  /**
   * This method is used to register a staff member onto the databsse system.
   * 
   * @param username
   *          Username to be registered with.
   * @param email
   *          Email to be registered with.
   * @param password
   *          Password to be registered with.
   * @param fullName
   *          Full Name of staff member.
   * @param telephone
   *          Telephone to be registered with.
   * @param address
   *          Addresss of staff member.
   * @param chosenRole
   *          Role to be registered to.
   */
  public static void registerStaff(String username, String email,
      String password, String fullName, String telephone, String address,
      StaffRoles chosenRole) {
    String sqlCommand;
    requester = new HttpRequester();

    // add this user to database
    sqlCommand = "INSERT INTO staff (username, email, pass, full_name, "
        + "telephone_number, address, job_position_id, permission_level) "
        + "VALUES ('" + username + "', '" + email + "', '" + password + "', '"
        + fullName + "', '" + telephone + "', '" + address + "', "
        + chosenRole.getId() + ", " + chosenRole.getId() + ")";

    // String url =
    // "http://localhost:8080/client_server/cafe/database/insertRow";

    String url = "/database/insertRow";

    System.out.println(sqlCommand);

    // send request to database
    try {
      requester.sendPost(url, sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error sending request");
    }

  }

  /**
   * This method adds the customer to the database so they can order and pay.
   * 
   * @param username
   *          String desired username of the customer
   * @param email
   *          String customer's email address
   * @param password
   *          String customers password
   * @param fullName
   *          String their full name
   * @param number
   *          String telephone number
   */
  public static boolean registerCustomer(String username, String email,
      String password, String fullName, String number) {
    requester = new HttpRequester();
    
    //error checking
    if (!numberCheck(number)) {
      return false;
    }

    String sqlCommand = "INSERT INTO customer (username, email, pass, full_name, telephone_number)"
        + " VALUES ('" + username + "', '" + email + "', '" + password + "', '"
        + fullName + "', '" + number + "');";

    //insert into db
    try {
      requester.sendPost("/database/insertRow", sqlCommand);
    } catch (IOException ioE) {
      System.out.println("Error inserting Customer");
      ioE.printStackTrace();
      return false;
    }
    
    return true;

  }

  private static boolean numberCheck(String number) {
    for (int count = 0; count < number.length(); count++) {
      if (!Character.isDigit(number.charAt(count))) {
        return false;
      }
    }
    return true;
  }
}
