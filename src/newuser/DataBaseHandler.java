/*******************************************
 *
 * @author - Ryan McGuire
 * Date: 10/30/2018
 * Allows a method to be passed as an argument.
 * Used to pass method that INSERTS into USERINFO.
 *
 *******************************************/

package newuser;

import java.sql.SQLException;

public interface DataBaseHandler {

  boolean checkDatabase(users.User user) throws SQLException;
}
