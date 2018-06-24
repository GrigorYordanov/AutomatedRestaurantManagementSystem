package customer.paymentscreen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Navigation;
import utilities.Payment;

/**
 * This controller controls the payment GUI.
 * 
 * @author Darren
 *
 */
public class PaymentController {

  private int customerId;
  private int tableId;

  private String[] styles = { "-fx-border-color: #929599;",
      "-fx-border-color: #c40000;", "-fx-border-radius: 10 10 10 10;",
      "-fx-background-radius: 10 10 10 10;",
      "-fx-background-radius: 0 10 10 0;", "-fx-border-radius: 0 10 10 0;",
      "-fx-background-radius: 10 0 0 10;", "-fx-border-radius: 10 0 0 10;" };

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField nameInput;
  @FXML private TextField cardNumberInput;
  @FXML private TextField securityNumber;
  @FXML private TextField yearExp;
  @FXML private TextField monthExp;
  @FXML private Label userIdLbl;
  @FXML private Label confirmButton;
  @FXML private Label cancelButton;

  /**
   * This method occurs when the cancel button is clicked. It clears the screen
   * back to original form.
   * 
   * @param event
   *          MouseEvent click
   */
  @FXML
  void cancelTransaction(MouseEvent event) {
    Navigation.navigateToCustomer("/customer/customermainmenu/CustomerMainMenu.fxml",
        (Stage) cancelButton.getScene().getWindow(), this.customerId,
        this.tableId, "Customer Menu");
  }

  /**
   * This method occurs when the button is clicked. It checks for valid input
   * and then goes to receipt upon valid entry.
   * 
   * @param event
   *          MouseEvent click
   */
  @FXML
  void confirmPayment(MouseEvent event) {
    // gather information
    String name = nameInput.getText();
    String cardNumber = cardNumberInput.getText();
    String securityNum = securityNumber.getText();
    String[] expiry = { monthExp.getText(), yearExp.getText() };

    // check each bit of information
    checkInformation(name, cardNumber, securityNum, expiry);

    // go to receipt if correct info
    if (Payment.completedTransaction(name, cardNumber, securityNum,
        expiry) == true) {
      showReciept();
    }
  }

  /**
   * This method will load the receipt and pass in the Customer ID.
   */
  private void showReciept() {
    Navigation.navigateToCustomer("/customer/paymentscreen/ReceiptPage.fxml",
        (Stage) cancelButton.getScene().getWindow(), this.customerId,
        this.tableId, "Reciept");
  }

  /**
   * Reset the text values of the text fields to "".
   */
  /*
   * private void resetText() { nameInput.setText("");
   * cardNumberInput.setText(""); securityNumber.setText("");
   * monthExp.setText(""); yearExp.setText(""); }
   */

  /**
   * Resets the style of the text fields.
   */
  /*
   * private void resetStyles() { nameInput.setStyle(styles[0] + styles[2] +
   * styles[3]); cardNumberInput.setStyle(styles[0] + styles[2] + styles[3]);
   * securityNumber.setStyle(styles[0] + styles[2] + styles[3]);
   * monthExp.setStyle(styles[0] + styles[6] + styles[7]);
   * yearExp.setStyle(styles[0] + styles[4] + styles[5]); }
   */

  /**
   * Checks the name of the card holder.
   * 
   * @param name
   *          String card holder's name
   */
  private void nameCheck(String name) {
    if (Payment.validateName(name) == false) {
      // error style
      nameInput.setStyle(styles[1] + styles[2] + styles[3]);
    } else {
      // back to original colour
      nameInput.setStyle(styles[0] + styles[2] + styles[3]);
    }
  }

  /**
   * Checks the card number's information.
   * 
   * @param cardNumber
   *          String Number on the card
   */
  private void numberCheck(String cardNumber) {
    if (Payment.creditNumberCheck(cardNumber) == false) {
      cardNumberInput.setStyle(styles[1] + styles[2] + styles[3]);
    } else {
      cardNumberInput.setStyle(styles[0] + styles[2] + styles[3]);
    }
  }

  /**
   * Checks the security code of Card.
   * 
   * @param securityNum
   *          String CVC of card
   */
  private void securityCheck(String securityNum) {
    if (Payment.securityCheck(securityNum) == false) {
      securityNumber.setStyle(styles[1] + styles[2] + styles[3]);
    } else {
      securityNumber.setStyle(styles[0] + styles[2] + styles[3]);
    }
  }

  /**
   * Checks the date if it is correct and alters the textbox visuals
   * accordingly.
   * 
   * @param expiry
   *          String[] month, year of card
   */
  private void dateCheck(String[] expiry) {
    if (Payment.isDigits(expiry[0]) == false) {
      monthExp.setStyle(styles[1] + styles[6] + styles[7]);
    } else {
      monthExp.setStyle(styles[0] + styles[6] + styles[7]);
    }

    if (Payment.isDigits(expiry[1]) == false) {
      yearExp.setStyle(styles[1] + styles[4] + styles[5]);
    } else {
      yearExp.setStyle(styles[0] + styles[4] + styles[5]);
    }

    if (Payment.isValidDate(expiry[0], expiry[1]) == false) {
      monthExp.setStyle(styles[1] + styles[6] + styles[7]);
      yearExp.setStyle(styles[1] + styles[4] + styles[5]);
    } else {
      monthExp.setStyle(styles[0] + styles[6] + styles[7]);
      yearExp.setStyle(styles[0] + styles[4] + styles[5]);
    }
  }

  /**
   * This method checks the information for correctness.
   * 
   * @param name
   *          String name of cardholder
   * @param cardNumber
   *          String Card number
   * @param securityNum
   *          String CVC code
   * @param expiry
   *          String[] month, year of expiry
   */
  private void checkInformation(String name, String cardNumber,
      String securityNum, String[] expiry) {

    nameCheck(name);

    numberCheck(cardNumber);

    securityCheck(securityNum);

    dateCheck(expiry);
  }

  /**
   * This method is used by FXML loader to initialise the GUI.
   */
  @FXML
  void initialize() {
  }

  /**
   * This method sets the customer Id and updates the screen.
   * 
   * @param userId
   *          int The customer ID
   */
  private void setCustomerId(int userId) {
    this.customerId = userId;
    userIdLbl.setText(userIdLbl.getText() + customerId);
  }

  /**
   * This method allows you to pass in the customer ID number. This method will
   * also create the tool tips for each text box, so their required entry is
   * known.
   * 
   * @param customerId
   *          The value of customer ID
   */
  public void initData(int customerId, int tableId) {
    setCustomerId(customerId);
    this.tableId = tableId;

    // name tool tip
    makeToolTip("Name must not contain \n numbers or be empty.", nameInput);

    // make card number tool tip
    makeToolTip(
        "Card number must be 13 to 19 digits \n long and must only contain digits.",
        cardNumberInput);

    // expiry toolTip
    makeToolTip("Date must be in the future \n and only contain digits",
        monthExp);
    makeToolTip("Date must be in the future \n and only contain digits",
        yearExp);

    // security tooltip
    makeToolTip(
        "Security Code must be 3 digits \n long and only contain digits.",
        securityNumber);
  }

  /**
   * This method will create a tool tip with a message and will work at the
   * target control.
   * 
   * @param msg
   *          String Message of the tooltip
   * @param target
   *          TextField The control to connect the tooltip to
   */
  private void makeToolTip(String msg, TextField target) {
    Tooltip tip = new Tooltip();
    tip.setText(msg);
    target.setTooltip(tip);
  }
}
