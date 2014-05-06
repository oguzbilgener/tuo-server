package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreSelectable;
import co.uberdev.ultimateorganizer.core.CoreTasks;

/**
 * Created by oguzbilgener on 14/04/14.
 */
public class Tasks extends CoreTasks implements CoreSelectable
{

    @Override
    public String getTableName()
    {
        return CoreDataRules.tables.tasks;
    }

    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit)
    {
        // TODO: implement loadFromDb
        return false;
    }

    @Override
    public boolean loadFromDb(int limit)
    {
        return false;
    }

    @Override
    public boolean loadFromDb()
    {
        return false;
    }
}
