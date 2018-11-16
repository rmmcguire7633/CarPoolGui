package users;

public class User {

  private int UserID;
  private String userName;
  private String password;
  private String email;
  private boolean isADriver;
  private double rating;
  private String location;
  private String destination;
  private String day;
  private String time;

  public User (String username, String location, String destination, String day, String time) {

    this.userName = username;
    this.location = location;
    this.destination = destination;
    this.day = day;
    this.time = time;
  }
  public User (String username, String password, String email, boolean isADriver,
      double rating) {

    this.userName = username;
    this.password = password;
    this.email = email;
    this.isADriver = isADriver;
    this.rating = rating;
  }

  public User (int userID, String username, String password, String email, boolean isADriver,
      double rating) {

    this.UserID = userID;
    this.userName = username;
    this.password = password;
    this.email = email;
    this.isADriver = isADriver;
    this.rating = rating;
  }

  public double getRating() {

    return rating;
  }

  public void setRating(double rating) {

    this.rating = rating;
  }

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

  public int getUserID() {
    return UserID;
  }

  public void setUserID(int userID) {
    UserID = userID;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String date) {
    this.day = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
