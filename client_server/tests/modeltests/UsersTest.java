package modeltests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.httprequest.HttpRequester;
import utilities.Users;


public class UsersTest {
  static HttpRequester requester;

  /**
   * Adds test data to database.
   * @throws MalformedURLException Error
   * @throws IOException Error
   */
  @BeforeClass
  public static void setUp() throws MalformedURLException, IOException {
    requester = new HttpRequester();
    String sqlCommand = "INSERT INTO staff "
        + "(username, email, pass, full_name, telephone_number, address, "
        + "job_position_id, permission_level) VALUES "
        + "('testUsername', 'testEmail', 'testPassword', 'testFullName',"
        + " 'testNumber', 'testAddress', 1, 1)";
    requester.sendPost("/database/insertRow", sqlCommand);
  }

  /**
   * Used to remove test data from db.
   * @throws MalformedURLException Error
   * @throws IOException Error
   */
  @AfterClass
  public static void tearDown() throws MalformedURLException, IOException {
    requester = new HttpRequester();
    String sqlCommand = "DELETE FROM staff WHERE username ='testUsername'";
    requester.sendPost("/database/deleteRow", sqlCommand);
  }

  @Test
  public void userNameIsThereTest() {
    String[] dbNames = { "connor", "geoff", "bill", "tommy", "lilo", "stitch" };
    String username = "connor";
    assertEquals("Test to see if can tell if username is there", true,
        Users.userNameIsThere(username, dbNames));
  }

  @Test
  public void userNameIsNotThereTest() {
    String[] dbNames = { "connor", "geoff", "bill", "tommy", "lilo", "stitch" };
    String username = "reggie";
    assertEquals("Test to see if can tell if username is there", false,
        Users.userNameIsThere(username, dbNames));
  }

  @Test
  public void getStaffUsernamesTest() {
    String username = "testUsername";
    assertEquals(
        "Test that uses the usernameThere method with a call to the database",
        true, Users.userNameIsThere(username, Users.getStaffUsernames()));
  }

  @Test
  public void getStaffUsernamesNotThereTest() {
    String username = "notPresent";
    assertEquals(
        "Test that uses the usernameThere method with a call to the database",
        false, Users.userNameIsThere(username, Users.getStaffUsernames()));
  }

  @Test
  public void staffObjectTest() {
    String address = "testAddress";
    assertEquals(
        "This test is used to make sure the object is created from the database information",
        true,
        address.equals(Users.getStaffObject("testUsername").getAddress()));
  }

  @Test
  public void staffObjectWrongTest() {
    String address = "wrongAddress";
    assertEquals(
        "This test is used to make sure the object is created from the database information",
        false,
        address.equals(Users.getStaffObject("testUsername").getAddress()));
  }

}
