package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import play.db.DB;
import java.sql.*;


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
    public boolean insert()
    {
        Connection connection = DB.getConnection();
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

            PreparedStatement insertStatement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
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

            insertStatement.close();

            return true;


        }catch(SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean update() {

        Connection connection = DB.getConnection();
        try
        {

            int n = 1;
            String updateSql = "UPDATE "+ getTableName() + " SET " +
                    CoreDataRules.columns.notes.ownerId+" =  ? , " +
                    CoreDataRules.columns.notes.attachment+" = ? , "+
                    CoreDataRules.columns.notes.content+" = ? , " +
                    CoreDataRules.columns.notes.lastModified+" =  ? , "+
                    CoreDataRules.columns.notes.relatedTaskID+" = ? " +
                    " WHERE id = "  + getId();


            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setLong(n++,getOwnerId());
            updateStatement.setString(n++, getAttachment().asJsonString());
            updateStatement.setString(n++, getContent());
            updateStatement.setInt(n++, getDateCreated());
            updateStatement.setLong(n++, getRelatedTaskId());

            updateStatement.execute();

            updateStatement.close();

            return true;

        }catch(SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    @Override
    public boolean remove() {

        Connection connection = DB.getConnection();
        try
        {
            String removeSql = "DELETE FROM " + getTableName() + " WHERE " + CoreDataRules.columns.notes.id + " = ?" ;

            PreparedStatement removeStatement = connection.prepareStatement(removeSql);
            removeStatement.setLong(1, getId());

            removeStatement.execute();

            removeStatement.close();

            return true;
        }catch (SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
