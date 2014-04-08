package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreSelectable;
import co.uberdev.ultimateorganizer.core.CoreUser;

/**
 * Created by oguzbilgener on 08/04/14.
 */
public class Users extends CoreUser implements CoreSelectable
{
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

    @Override
    public boolean loadFromDb(String sqlCriteria, int limit) {
        return false;
    }


}
