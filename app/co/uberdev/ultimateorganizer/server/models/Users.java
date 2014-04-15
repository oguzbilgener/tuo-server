package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreSelectable;
import co.uberdev.ultimateorganizer.core.CoreUsers;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
    public String getTableName()
    {
        return CoreDataRules.tables.users;
    }

    @Override
    public boolean loadFromDb()
    {
        return loadFromDb(null, new String[] {}, 0);
    }

    @Override
    public boolean loadFromDb(int limit)
    {
        return loadFromDb(null, new String[] {}, limit);
    }

    // TODO: use limit
    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit)
    {

        try {
            int n = 1;
            String loadSql = "SELECT * FROM "+getTableName();
            if(sqlCriteria != null) {
                loadSql += " WHERE "+sqlCriteria;
            }
            PreparedStatement loadStatement = DB.getConnection().prepareStatement(loadSql);
            if(params != null)
            {
                for(String param : params)
                {
                    loadStatement.setString(n++, param);
                }
            }

            ResultSet set = loadStatement.getResultSet();

            while(set != null && set.next())
            {
                // TODO: complete here for User class
                // use database column names
                User user = new User();
                user.setId(set.getInt(CoreDataRules.columns.users.id));
                user.setEmailAddress(set.getString(CoreDataRules.columns.users.emailAddress));
                user.setPasswordHashed(set.getString(CoreDataRules.columns.users.passwordHashed));
                user.setFirstName(set.getString(CoreDataRules.columns.users.firstName));
                user.setLastName(set.getString(CoreDataRules.columns.users.lastName));
                user.setPublicKey(set.getString(CoreDataRules.columns.users.publicKey));
                user.setSecretToken(set.getString(CoreDataRules.columns.users.secretToken));
                user.setState(set.getInt(CoreDataRules.columns.users.state));
                user.setResetKey(set.getString(CoreDataRules.columns.users.resetKey));
                user.setResetDue(set.getInt(CoreDataRules.columns.users.resetDue));
                user.setCreated(set.getInt(CoreDataRules.columns.users.created));
                user.setBirthday(set.getInt(CoreDataRules.columns.users.birthday));
                user.setSchoolName(set.getString(CoreDataRules.columns.users.schoolName));
                user.setDepartmentName(set.getString(CoreDataRules.columns.users.departmentName));

                // TODO: set devices

                add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


}
