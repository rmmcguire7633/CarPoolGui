package newuser;

import java.sql.SQLException;

/**
 * Allows a method to be passed as an argument.
 * Used to pass method that INSERTS into the USERINFO table.
 * Date 10/30/2018
 * @author Ryan McGuire
 */
public interface DataBaseHandler {

  /**
   * pushed to the database.
   * @param user the type created from the newuser scene fields.
   * @return true if push is successful.
   * @throws SQLException the SQL exception.
   */
  boolean checkDatabase(users.User user) throws SQLException;
}
