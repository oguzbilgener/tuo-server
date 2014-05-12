package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import co.uberdev.ultimateorganizer.server.utils.Validation;
import play.db.DB;
import play.mvc.Result;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

/**
 * Created by oguzbilgener on 08/04/14.
 */
public class User extends CoreUser implements CoreStorable
{
    protected static final String PUBLIC_KEY_SALT = "im-yiJ-iC-Wyn-Ya";
    protected static final String SECRET_TOKEN_SALT = "yiV-Vet-Dej-kaj-";
    protected static final String PASSWORD_SALT = "Ap-Fej-Faigs-Ad-E";

    /**
     * Empty constructor
     */
    public User()
    {

    }

    /**
     * Constructor for user login
     * @param emailAddress
     * @param password
     */
    public User(String emailAddress, String password)
    {
        this.emailAddress = emailAddress;
        this.password = password;
        hashPassword();
    }

    public void setPassword(String password)
    {
        this.password = password;
        hashPassword();
    }

    public void hashPassword()
    {
        passwordHashed = CoreCrypto.sha1(PASSWORD_SALT + password);
    }

    /**
     * Using Users class, connects to database and finds out if login is possible
     * @return
     */
    public boolean tryLogin()
    {
        // TODO: connect to database and see if the given username and password of this User matches a user.
        // Then load all the retrieved data to self
        Users matchingUsers = new Users();
        // actually make a query to the database and fill it into matchingUsers
        matchingUsers.loadFromDb(CoreDataRules.columns.users.emailAddress + "= ? AND "+CoreDataRules.columns.users.passwordHashed + " = ? ", new String[] { emailAddress, passwordHashed }, 1);
        if(matchingUsers.size() > 0)
        {
            User matchingUser = matchingUsers.get(0);

            if(matchingUser.getState() == User.STATE_BANNED)
                return false;
            // set the parameters from matching user
            set(matchingUser);
            setPassword(null);

            return true;
        }
        return false;
    }

    public boolean register() throws Validation.BadInputException
    {
        if(Validation.validateUser(this))
        {
            this.setCreated(CoreUtils.getUnixTimestamp());
            this.setPublicKey(CoreUtils.generatePublicKey(this.emailAddress, PUBLIC_KEY_SALT));
            this.setSecretToken(CoreUtils.generateSecretToken(this.emailAddress, this.password, SECRET_TOKEN_SALT));

            if(insert())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTableName()
    {
        return CoreDataRules.tables.users;
    }

    @Override
    public boolean insert()
    {
        try
        {
            int n = 1;
            String insertSql = "INSERT INTO "+getTableName()+" (" +
            CoreDataRules.columns.users.id+", "+
            CoreDataRules.columns.users.emailAddress+", "+
            CoreDataRules.columns.users.passwordHashed+", "+
            CoreDataRules.columns.users.firstName+", "+
            CoreDataRules.columns.users.lastName+", "+
            CoreDataRules.columns.users.publicKey+", "+
            CoreDataRules.columns.users.secretToken+", "+
            CoreDataRules.columns.users.state+", "+
            CoreDataRules.columns.users.resetKey+", "+
            CoreDataRules.columns.users.resetDue+", "+
            CoreDataRules.columns.users.schoolName+", "+
            CoreDataRules.columns.users.departmentName+", "+
            CoreDataRules.columns.users.created+", "+
            CoreDataRules.columns.users.birthday+" "+
            ") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ResultSet generatedKeys;

            PreparedStatement insertStatement = DB.getConnection().prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setString(n++, getEmailAddress());
            insertStatement.setString(n++, getPasswordHashed());
            insertStatement.setString(n++, getFirstName());
            insertStatement.setString(n++, getLastName());
            insertStatement.setString(n++, getPublicKey());
            insertStatement.setString(n++, getSecretToken());
            insertStatement.setInt(n++, getState());
            insertStatement.setString(n++, getResetKey());
            insertStatement.setInt(n++, getResetDue());
            insertStatement.setString(n++, getSchoolName());
            insertStatement.setString(n++, getDepartmentName());
            insertStatement.setInt(n++, getCreated());
            insertStatement.setInt(n++, getBirthday());

            insertStatement.execute();

            generatedKeys = insertStatement.getGeneratedKeys();
            if(generatedKeys.next())
                setId(generatedKeys.getLong(1));

            insertStatement.close();
            DB.getConnection().close();

            return true;
        }
        catch (SQLException e)
        {

             e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean update()
    {
        return false;
    }

    @Override
    public boolean remove()
    {
        return false;
    }

    public static User fromPublicKey(String publicKey)
    {
        Users users = new Users();
        users.loadFromDb(CoreDataRules.columns.users.publicKey + " = ?", new String[] { publicKey }, 0);
        if(users.size() > 0)
        {
            return users.get(0);
        }
        return null;
    }

    public static User fromEmail(String emailAddress)
    {
        Users users = new Users();
        users.loadFromDb(CoreDataRules.columns.users.emailAddress + " = ?", new String[] { emailAddress}, 0);
        if(users.size() > 0)
        {
            return users.get(0);
        }
        return null;
    }

    /**
     * sets the user itself from given user parameter
     * Keep an eye on this method. If new attributes are added to User or CoreUser class, this method should be updated as well
     * @param user
     */
    public void set(User user)
    {
        setId(user.getId());
        setEmailAddress(user.getEmailAddress());
        setPassword(user.getPassword());
        setPasswordHashed(user.getPasswordHashed());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPublicKey(user.getPublicKey());
        setSecretToken(user.getSecretToken());
        setResetKey(user.getResetKey());
        setResetDue(user.getResetDue());
        setDevices(user.getDevices());
        setSchoolName(user.getSchoolName());
        setDepartmentName(user.getDepartmentName());
        setCreated(user.getCreated());
        setBirthday(user.getBirthday());
    }


    public Tasks getFeed() throws SQLException
    {
            Courses coursesOfUser = new Courses();
            coursesOfUser.loadFromDb(CoreDataRules.columns.courses.ownerId + " = ?::integer", new String[]{String.valueOf(getId())}, 0);
            System.out.println(coursesOfUser.size());
            if(coursesOfUser.size() > 0)
            {
                Tasks publicTasks = new Tasks();
                String sqlCriteria = "";
                String[] fields = new String[coursesOfUser.size()+2];

                sqlCriteria += "(";
                for(int i=0; i< coursesOfUser.size(); i++)
                {
                    if(i>0)
                        sqlCriteria += " OR ";
                    sqlCriteria += CoreDataRules.columns.tasks.courseCodeCombined + " = ?";
                }
                sqlCriteria += ") AND "+CoreDataRules.columns.tasks.ownerId + " != ?::integer AND "+
                        CoreDataRules.columns.tasks.personal+" = ?::boolean";

                for(int i=0; i<coursesOfUser.size(); i++)
                {
                    fields[i] = coursesOfUser.get(i).getCourseCodeCombined();
                }
                fields[coursesOfUser.size()] = String.valueOf(getId());
                fields[coursesOfUser.size()+1] = "false";

                System.out.println(sqlCriteria);

                publicTasks.loadFromDb(sqlCriteria, fields, 0);

                return publicTasks;
            }
            else
            {
                return new Tasks();
            }
    }

}