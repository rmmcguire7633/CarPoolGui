package newuser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the reg ex (correct format) for the username field.
 * Compares user input with correct format.
 * Date 10/30/2018
 * @author Ryan McGuire
 */
public class UserNameValidator implements Validator {

  private Pattern pattern;
  private Matcher matcher;

  /**
   * must be between 6 and 20 charters long.
   * */
  private static final String USERNAME_FORMAT = "(.{6,20})";

  /**
   * creates a pattern type from the regex.
   */
  public UserNameValidator() {

    pattern = Pattern.compile(USERNAME_FORMAT);
  }

  /**
   * Compares the the pattern type with the regex.
   * @param userName the username the user entered.
   * @return true if username matches the regex.
   */
  @Override
  public boolean validate(final String userName) {

    matcher = pattern.matcher(userName);
    return matcher.matches();
  }
}
