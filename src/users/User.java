package users;

public class User {

  private int UserID;
  private String userName;
  private String password;
  private String email;
  private boolean isADriver;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public String getEmail() {

    return email;
  }

  public void setEmail(String email) {

    this.email = email;
  }

  public boolean isADriver() {

    return isADriver;
  }

  public void setADriver(boolean ADriver) {

    isADriver = ADriver;
  }

  public String getUserName() {

    return userName;
  }

  public void setUserName(String userName) {

    this.userName = userName;
  }
}
