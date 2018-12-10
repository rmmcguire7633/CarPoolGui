package newuser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the reg ex (correct format) for the password field.
 * Compares user input with correct format.
 * Date 10/30/2018
 * @author Ryan McGuire
 */
public class PasswordValidator implements Validator {

  private Pattern pattern;
  private Matcher matcher;

  /**
   * must have at least 1 number character, at least one Capital letter character and be between
   * 6 and 20 characters long.
   */
  private static final String PASSWORD_FORMAT = "((?=.*\\d)(?=.*[A-Z]).{6,20})";

  /**
   * Makes a pattern type from the regex.
   */
  public PasswordValidator() {

    pattern = Pattern.compile(PASSWORD_FORMAT);
  }

  /**
   * Compares the password field to the regex.
   * @param password the password entered by the user.
   * @return true of password and regex match.
   */
  @Override
  public boolean validate(final String password) {

    matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
