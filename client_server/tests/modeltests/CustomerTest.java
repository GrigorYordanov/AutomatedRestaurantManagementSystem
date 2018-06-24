package modeltests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utilities.Customer;

public class CustomerTest {

  private Customer thisUser;

  @Before
  public void setUp() {
    thisUser = new Customer();
  }

  @Test
  public void getIdTest() {
    thisUser.setId(4);
    assertEquals("Test for retrieval of ID", 4, thisUser.getId());
  }

  @Test
  public void getCorrectIdTest() {
    thisUser.setId(6);
    assertEquals("Test for retrieval of ID", 6, thisUser.getId());
  }

  @Test
  public void getUsernameTest() {
    thisUser.setUsername("user1");
    assertEquals("Test for username", "user1", thisUser.getUsername());
  }

  @Test
  public void getUsernameCorrectTest() {
    thisUser.setUsername("user5");
    assertEquals("Test for username", "user5", thisUser.getUsername());
  }

  @Test
  public void getPasswordTest() {
    thisUser.setPassword("pass");
    assertEquals("Test for password retrieval", "pass", thisUser.getPassword());
  }

  @Test
  public void getPasswordTestRepeat() {
    thisUser.setPassword("word");
    assertEquals("Test for password retrieval", "word", thisUser.getPassword());
  }

  @Test
  public void getNameTest() {
    thisUser.setFullName("Darren");
    assertEquals("Test for name retrieval", "Darren", thisUser.getFullName());
  }

  @Test
  public void getNameTestRepeat() {
    thisUser.setFullName("Matthews");
    assertEquals("Test for name retrieval", "Matthews", thisUser.getFullName());
  }

  @Test
  public void getTelephoneNumberTest() {
    thisUser.setTelephoneNumber("01234567890");
    assertEquals("Test for telephone number", "01234567890",
        thisUser.getTelephoneNumber());
  }

  @Test
  public void getTelephoneNumberTestRepeat() {
    thisUser.setTelephoneNumber("1111111111");
    assertEquals("Test for telephone number", "1111111111",
        thisUser.getTelephoneNumber());
  }
}
