package utilities;

import java.util.Calendar;

/**
 * This class is used to validate the payment option.
 * 
 * @author Darren
 *
 */
public class Payment {

  /**
   * This method is used to check is a credit card number is correct.
   * 
   * @param number
   *          the credit card number.
   * @return Boolean true if the number is accepted.
   */
  public static boolean creditNumberCheck(String number) {
    if (isDigits(number) && number.length() >= 13 && number.length() <= 19) {
      return true;
    }

    return false;
  }

  /**
   * This method checks the security digits for correctness.
   * 
   * @param code
   *          Security code.
   * @return Boolean true if correct.
   */
  public static boolean securityCheck(String code) {
    if (isDigits(code) && code.length() == 3) {
      return true;
    }
    return false;
  }

  /**
   * This method checks if the string can be converted into a number.
   * 
   * @param item
   *          String to try and convert
   * @return Boolean true if it is a number.
   */
  public static boolean isDigits(String item) {
    try {
      Long.parseLong(item);
    } catch (NumberFormatException nfe) {
      return false;
    }

    return true;
  }

  /**
   * This method checks if the card date is a valid date.
   * 
   * @param expMonth
   *          String of the month to check.
   * @param expYear
   *          String of the year to check.
   * @return Boolean true if correct date.
   */
  public static boolean isValidDate(String expMonth, String expYear) {
    Calendar now = Calendar.getInstance();
    int currentYear = now.get(Calendar.YEAR);
    int currentMonth = now.get(Calendar.MONTH);

    if ((isDigits(expMonth) && isDigits(expYear)) != true) {
      return false;
    }

    int cardYear = Integer.parseInt(expYear);
    int cardMonth = Integer.parseInt(expMonth);

    if (cardYear > currentYear) {
      return true;
    } else if (cardYear == currentYear & cardMonth > currentMonth) {
      return true;
    }

    return false;
  }

  /**
   * This method is used to check if the name of the cardholder is correct.
   * 
   * @param name
   *          The name on the card.
   * @return Boolean true if the name is validated.
   */
  public static boolean validateName(String name) {

    if (name.equals("")) {
      return false;
    }

    for (int count = 0; count < name.length(); count++) {
      if (Character.isDigit(name.charAt(count))) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method uses other functions to check if a whole transaction can take
   * place.
   * 
   * @param name
   *          The name of cardholder.
   * @param cardNumber
   *          Number on the card.
   * @param securityNum
   *          Security digits on the card.
   * @param expiry
   *          Date of expiry.
   * @return Boolean true if the transactions can take place.
   */
  public static boolean completedTransaction(String name, String cardNumber,
      String securityNum, String[] expiry) {

    if (validateName(name) == false) {
      return false;
    } else if (creditNumberCheck(cardNumber) == false) {
      return false;
    } else if (securityCheck(securityNum) == false) {
      return false;
    } else if (isValidDate(expiry[0], expiry[1]) == false) {
      return false;
    }

    return true;
  }

}
