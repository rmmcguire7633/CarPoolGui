package users;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

/**
 * This Class holds all the user info pulled from the USERINFO and SCHEDULEINFO table.
 * Date 10/22/2018
 * @author Ryan McGuire
 */
public class User {

  private int userId;
  private String userName;
  private String password;
  private String email;
  private boolean isADriver;
  private double rating;
  private String location;
  private String destination;
  private LocalDate day;
  private Time time;

  /**
   * This constructor is used for passing the fields to the USERINFO table
   * from main.MainMenuContoller class.
   * @param username the username of the user.
   * @param location the location for them to be picked up.
   * @param destination the drop off location for the user.
   * @param day the day the user would like to bne picked up.
   * @param time the time the user would like to be picked up.
   */
  public User(String username, String location, String destination, LocalDate day, Time time) {

    this.userName = username;
    this.location = location;
    this.destination = destination;
    this.day = day;
    this.time = time;
  }

  /**
   * This constructor is used for passing the fields to the USERINFO table
   * from the newuser.NewUserController class.
   * @param username the username of the user.
   * @param password the password of the user.
   * @param email the email of the user.
   * @param isADriver true if user is also a driver.
   * @param rating the 5 star rating of the user.
   */
  public User(String username, String password, String email, boolean isADriver,
      double rating) {

    this.userName = username;
    this.password = password;
    this.email = email;
    this.isADriver = isADriver;
    this.rating = rating;
  }

  /**
   * This constructor is used for passing the fields to the USERINFO table
   * from the login.LoginController class.
   * @param userId the user's ID.
   * @param username the username of the user.
   * @param password the password of the user.
   * @param email the email of the user.
   * @param isADriver true if the user is also a driver.
   * @param rating the 5 star rating of the user.
   */
  public User(int userId, String username, String password, String email, boolean isADriver,
      double rating) {

    this.userId = userId;
    this.userName = username;
    this.password = password;
    this.email = email;
    this.isADriver = isADriver;
    this.rating = rating;
  }

  /**
   * This constructor is used for passing the fields to the SCHEDULEINFO table
   * from the main.MainMenuDriveController and main.MainMenuRideController.
   * @param userName the username of the user.
   * @param rating the 5 star rating of the user.
   * @param location the desired pickup location for the user.
   * @param destination the desired drop off location for the user.
   * @param day the desired day for pickup for the user.
   * @param time the desired time for pickup for the user.
   */
  public User(String userName, double rating, String location,
      String destination, LocalDate day, Time time) {

    this.userName = userName;
    this.rating = rating;
    this.location = location;
    this.destination = destination;
    this.day = day;
    this.time = time;
  }

  /**
   * Gets the users rating from 0-5.
   * @return the users rating.
   */
  public double getRating() {

    return rating;
  }

  /**
   * Sets the users rating 0-5.
   * @param rating the rating of the user.
   */
  public void setRating(double rating) {

    this.rating = rating;
  }

  /**
   * Gets the password of the user.
   * @return the password opf the user.
   */
  public String getPassword() {

    return password;
  }

  /**
   * Sets the user password.
   * @param password the password of the user.
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * Gets the email of the user.
   * @return the email of the user.
   */
  public String getEmail() {

    return email;
  }

  /**
   * Sets the email of the user.
   * @param email the email of the user.
   */
  public void setEmail(String email) {

    this.email = email;
  }

  /**
   * Gets the isAdriver value.
   * @return true if the user is a driver.
   */
  public boolean getIsAdriver() {

    return isADriver;
  }

  /**
   * Sets the value of isAdriver.
   * @param isADriver true if user is a driver false if not.
   */
  public void setADriver(boolean isADriver) {

    this.isADriver = isADriver;
  }

  /**
   * Gets the user name of the user.
   * @return the username.
   */
  public String getUserName() {

    return userName;
  }

  /**
   * Sets the username.
   * @param userName the username.
   */
  public void setUserName(String userName) {

    this.userName = userName;
  }

  /**
   * The user ID in the database.
   * @return the user ID.
   */
  public int getUserId() {

    return userId;
  }

  /**
   * Sets the user ID.
   * @param userId the user ID.
   */
  public void setUserId(int userId) {

    this.userId = userId;
  }

  /**
   * Gets the pick location of the user from the database.
   * @return the pick up location of the user.
   */
  public String getLocation() {

    return location;
  }

  /**
   * Sets the pick up location for the user.
   * @param location the pick up location of the user.
   */
  public void setLocation(String location) {

    this.location = location;
  }

  /**
   * Gets the destination of the user.
   * @return the destination of the user.
   */
  public String getDestination() {

    return destination;
  }

  /**
   * This method is user to set the destination the user would like to be dropped off at.
   * @param destination the destination of the user.
   **/
  public void setDestination(String destination) {

    this.destination = destination;
  }

  /**
   * Gets the date the user is to be picked up.
   * @return the date the user will be picked up.
   */
  public LocalDate getDay() {

    return day;
  }

  /**
   * Sets the date the user is to be picked up.
   * @param date the date the user will be picked up.
   */
  public void setDay(LocalDate date) {

    this.day = date;
  }

  /**
   * Gets the time the user is to be picked up.
   * @return the time the user is to be picked up.
   */
  public Time getTime() {

    return time;
  }

  /**
   * Sets the time the user is to be picked up.
   * @param time the time the user is to be picked up.
   */
  public void setTime(Time time) {

    this.time = time;
  }
}
