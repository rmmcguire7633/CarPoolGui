package newuser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator implements Validator{

  private Pattern pattern;
  private Matcher matcher;

  /**
   * must be between 6 and 20 charters long.
   * */
  private static final String USERNAME_FORMAT = "(.{6,20})";

  public UserNameValidator (){

    pattern = Pattern.compile(USERNAME_FORMAT);
  }

  @Override
  public boolean Validate(final String userName) {

    matcher = pattern.matcher(userName);
    return matcher.matches();
  }
}
