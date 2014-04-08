package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.core.CoreStorable;
import co.uberdev.ultimateorganizer.core.CoreUser;

/**
 * Created by oguzbilgener on 08/04/14.
 */
public class User extends CoreUser implements CoreStorable
{
    protected static final String PASSWORD_SALT = "Ap-Fej-Faigs-Ad-E";

    public void hashPassword()
    {
        passwordHashed = CoreCrypto.sha1(PASSWORD_SALT + password);
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
}
