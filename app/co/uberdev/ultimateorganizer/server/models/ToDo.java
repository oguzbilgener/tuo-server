package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreStorable;
import co.uberdev.ultimateorganizer.core.CoreToDo;

/**
 * Created by oguzbilgener on 14/04/14.
 */
public class ToDo extends CoreToDo implements CoreStorable
{
    @Override
    public String getTableName() {
        return null;
    }

    public void loadFromDb()
    {

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
