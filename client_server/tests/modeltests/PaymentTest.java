package modeltests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utilities.Payment;


public class PaymentTest {

  // Test 1 - Test the detection of correct number of digits in credit number
  @Test
  public void creditNumberLengthCheckTest() {
    assertEquals("Test for length of number input", true,
        Payment.creditNumberCheck("9875172384571236"));
  }

  // Test 2 - Test the detection of incorrect number of digits
  @Test
  public void creditNumberWrongLengthTest() {
    assertEquals("Test for wrong length", false,
        Payment.creditNumberCheck("345"));
  }

  // Test 3 - Test correct security code length
  @Test
  public void securityCodeLengthTest() {
    assertEquals("Test for correct security code", true,
        Payment.securityCheck("123"));
  }

  // Test 4 - Test for incorrect security code length
  @Test
  public void securityCodeWrongTest() {
    assertEquals("Test for incorrect security code", false,
        Payment.securityCheck("1234567890"));
  }

  // Test 5 - Test for digit input
  @Test
  public void digitCheckTest() {
    assertEquals("Test for digits", true, Payment.isDigits("12345"));
  }

  // Test 6 - Test for non digit input
  @Test
  public void incorrectDigitCheck() {
    assertEquals("Test for wrong digits", false, Payment.isDigits("nope"));
  }

  // Test 7 - test for date entry
  @Test
  public void dateEntryTest() {
    assertEquals("Test for date", true, Payment.isValidDate("9", "2030"));
  }

  // Test 8 - test for wrong date entry
  @Test
  public void wrongDateEntryTest() {
    assertEquals("Test for date in the past", false,
        Payment.isValidDate("4", "2006"));
  }

  // Test 9 - Test name
  @Test
  public void nameCheckTest() {
    assertEquals("Test for numbers in the name", true,
        Payment.validateName("John"));
  }

  // Test 10 - Test name for a number
  @Test
  public void nameNumberCheck() {
    assertEquals("Test if the name contains a number", false,
        Payment.validateName("J0hn"));
  }
}
