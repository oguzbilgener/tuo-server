package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreStorable;
import co.uberdev.ultimateorganizer.core.CoreTask;

/**
 * Created by oguzbilgener on 29/03/14.
 */
public class Task extends CoreTask implements CoreStorable
{
    // Dummy Task class
    public Task()
    {
        super();
    }

    @Override
    public String getTableName()
    {
        return CoreDataRules.tables.tasks;
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
}
