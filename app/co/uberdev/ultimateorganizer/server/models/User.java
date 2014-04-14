package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreStorable;
import co.uberdev.ultimateorganizer.core.CoreUser;

import java.lang.reflect.Field;

/**
 * Created by oguzbilgener on 08/04/14.
 */
public class User extends CoreUser implements CoreStorable
{
    protected long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean register()
    {
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
        // TODO: fix. This probably does not work:
        Users users = new Users();
        users.loadFromDb("public_key = ?", new String[] { publicKey}, 0);
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

}
