/*******************************************
 *
 * @author - Ryan McGuire
 * Date: 10/30/2018
 * Holds the reg ex (correct format) for the password field.
 * Compares user input with correct format.
 *
 *******************************************/

package newuser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements Validator {

  private Pattern pattern;
  private Matcher matcher;

  /**
   * must have at least 1 number character, at least one Capital letter character and be between
   * 6 and 20 characters long.
   */
  private static final String PASSWORD_FORMAT = "((?=.*\\d)(?=.*[A-Z]).{6,20})";

  public PasswordValidator() {

    pattern = Pattern.compile(PASSWORD_FORMAT);
  }

  @Override
  public boolean validate(final String password) {

    matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
