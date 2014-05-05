package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public String getTableName() { return CoreDataRules.tables.notes; }

    @Override
    public boolean insert() {
        try
        {
            int n = 1;
            String insertSql = "INSERT INTO "+ getTableName() + " (" +
                    CoreDataRules.columns.notes.id+", "+
                    CoreDataRules.columns.notes.ownerId+", "+
                    CoreDataRules.columns.notes.attachment+", "+
                    CoreDataRules.columns.notes.content+", "+
                    CoreDataRules.columns.notes.dateCreated+", "+
                    CoreDataRules.columns.notes.lastModified+", "+
                    CoreDataRules.columns.notes.relatedTaskID+" "+
                    ") VALUES (default, ?, ?, ?, ?, ?, ?)";

            ResultSet generatedKeys;

            PreparedStatement insertStatement = DB.getConnection().prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(n++, getOwnerId());
            insertStatement.setString(n++, getAttachment().asJsonString());
            insertStatement.setString(n++, getContent());
            insertStatement.setInt(n++, getDateCreated());
            insertStatement.setInt(n++, getLastModified());
            insertStatement.setLong(n++, getRelatedTaskId());

            insertStatement.execute();

            generatedKeys = insertStatement.getGeneratedKeys();

            if(generatedKeys.next())
                setId(generatedKeys.getLong(1));

            return true;


        }catch(SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }

        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean remove() {

        try
        {
            String removeSql = "DELETE FROM " + getTableName() + " WHERE " + CoreDataRules.columns.notes.id + " = ?" ;

            PreparedStatement removeStatement = DB.getConnection().prepareStatement(removeSql);
            removeStatement.setLong(1, getId());

            removeStatement.execute();
            return true;
        }catch (SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
        return false;
    }
}
