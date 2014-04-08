package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.core.CoreStorable;
import co.uberdev.ultimateorganizer.core.CoreUser;

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
        return false;
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean remove() {
        return false;
    }

    public static User fromPublicKey(String publicKey)
    {
        Users users = new Users();
        users.loadFromDb("public_key='"+publicKey+"'", 0);
        if(users.size() > 0)
        {
            return users.get(0);
        }
        return null;
    }
}
