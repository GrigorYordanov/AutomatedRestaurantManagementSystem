package utilities;

/**
 * This class encapsulates customer details into an object.
 * 
 * @author Darren
 */
public class Customer {

  private int id;
  private String username;
  private String password;
  private String fullName;
  private String telephoneNumber;

  /**
   * Constructor that takes in an array of the details about the customer and
   * fills in the details.
   * 
   * @param details
   *          String array of the customer details
   */
  public Customer(String[] details) {
    this.id = Integer.parseInt(details[0]);
    this.username = details[1];
    this.password = details[2];
    this.fullName = details[3];
    this.telephoneNumber = details[4];
  }

  public Customer() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

}
