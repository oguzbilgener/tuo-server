package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreSelectable;
import co.uberdev.ultimateorganizer.core.CoreUsers;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oguzbilgener on 08/04/14.
 */
public class Users extends CoreUsers implements CoreSelectable
{
    public User get(int index)
    {
        return (User) get(index);
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public boolean loadFromDb() {
        return loadFromDb(null, 0);
    }

    @Override
    public boolean loadFromDb(int limit) {
        return loadFromDb(null, limit);
    }

    // TODO: use limit
    @Override
    public boolean loadFromDb(String sqlCriteria, int limit) {

        // TODO: fix. This probably does not work:
        try {
            int n = 1;
            String loadSql = "SELECT * FROM "+getTableName();
            if(sqlCriteria != null) {
                loadSql += " WHERE ?";
            }
            PreparedStatement loadStatement = DB.getConnection().prepareStatement(loadSql);
            loadStatement.setString(n++, sqlCriteria);
            ResultSet set = loadStatement.getResultSet();

            while(set.next())
            {
                // TODO: complete here for User class
                // use database column names
                User user = new User();
                user.setId(set.getInt("id"));
                user.setEmailAddress(set.getString("email_address"));
                user.setPasswordHashed(set.getString("passwd"));
                user.setFirstName(set.getString("first_name"));
                user.setLastName(set.getString("last_name"));
                user.setPublicKey(set.getString("public_key"));

                add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


}
