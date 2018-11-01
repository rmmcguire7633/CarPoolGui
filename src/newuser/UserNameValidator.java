package newuser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator implements Validator{

  private Pattern pattern;
  private Matcher matcher;

  private static final String USERNAME_FORMAT = "()";

  @Override
  public boolean Validate(String password) {
    return false;
  }
}
