package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreSelectable;

/**
 * Created by oguzbilgener on 14/04/14.
 */
public class Notes implements CoreSelectable {
    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit) {
        return false;
    }

    @Override
    public boolean loadFromDb(int limit) {
        return false;
    }

    @Override
    public boolean loadFromDb() {
        return false;
    }
}
