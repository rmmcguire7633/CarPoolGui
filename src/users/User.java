/*******************************************
 *
 * Author: Ryan McGuire
 * Date: 10/22/2018
 * This Class holds all the user info pulled from the USERINFO and SCHEDULEINFO table.
 *
 *******************************************/

package users;

import java.sql.Time;
import java.util.Date;

public class User {

  private int userId;
  private String userName;
  private String password;
  private String email;
  private boolean isADriver;
  private double rating;
  private String location;
  private String destination;
  private Date day;
  private Time time;

  /**
   * This constructor is used for passing the fields to the USERINFO table
   * from main.MainMenuContoller class.
   * @param username the username of the user.
   * @param location the location for them to be picked up.
   * @param destination the drop off location for the user.
   * @param day the day the user would like to bne picked up.
   * @param time the time the user would like to be picked up.
   **/
  public User(String username, String location, String destination, Date day, Time time) {

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
   **/
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
   **/
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
   **/
  public User(String userName, double rating, String location,
      String destination, Date day, Time time) {

    this.userName = userName;
    this.rating = rating;
    this.location = location;
    this.destination = destination;
    this.day = day;
    this.time = time;
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

  public boolean getIsAdriver() {

    return isADriver;
  }

  public void setADriver(boolean isADriver) {

    this.isADriver = isADriver;
  }

  public String getUserName() {

    return userName;
  }

  public void setUserName(String userName) {

    this.userName = userName;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {

    this.userId = userId;
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

  /**
   * This method is user to set the destination the user would like to be dropped off at.
   * @param destination the destination of the user.
   **/
  public void setDestination(String destination) {

    this.destination = destination;
  }

  public Date getDay() {

    return day;
  }

  public void setDay(Date date) {

    this.day = date;
  }

  public Time getTime() {

    return time;
  }

  public void setTime(Time time) {

    this.time = time;
  }
}
