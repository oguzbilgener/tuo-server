package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.CoreNote;
import co.uberdev.ultimateorganizer.core.CoreStorable;

/**
 * Created by oguzbilgener on 14/04/14.
 */
public class Note extends CoreNote implements CoreStorable
{
    public Note(long id)
    {
        super(id);
    }

    @Override
    public String getTableName() {
        return null;
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
