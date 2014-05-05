package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import com.google.gson.Gson;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        try
        {
            int n = 1;
            String insertSql = "INSERT INTO "+getTableName()+" (" +
                    CoreDataRules.columns.tasks.id+", "+
                    CoreDataRules.columns.tasks.ownerId+", "+
                    CoreDataRules.columns.tasks.beginDate+", "+
                    CoreDataRules.columns.tasks.courseId+", "+
                    CoreDataRules.columns.tasks.course+", "+
                    CoreDataRules.columns.tasks.courseCodeCombined+", "+
                    CoreDataRules.columns.tasks.dateCreated+", "+
                    CoreDataRules.columns.tasks.endDate+", "+
                    CoreDataRules.columns.tasks.lastModified+", "+
                    CoreDataRules.columns.tasks.personal+", "+
                    CoreDataRules.columns.tasks.relatedNotes+", "+
                    CoreDataRules.columns.tasks.relatedTasks+", "+
                    CoreDataRules.columns.tasks.status+", "+
                    CoreDataRules.columns.tasks.tags+", "+
                    CoreDataRules.columns.tasks.taskDesc+", "+
                    CoreDataRules.columns.tasks.taskName+", "+
                    CoreDataRules.columns.tasks.taskOwnerNameCombined+" "+
                    ") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

           System.out.println(insertSql);

            PreparedStatement insertStatement = DB.getConnection().prepareStatement(insertSql);
            insertStatement.setLong(n++, getOwnerId());
            insertStatement.setInt(n++, getBeginDate());
            insertStatement.setInt(n++, getCourseId());
            insertStatement.setString(n++, getCourse().asJsonString());
            insertStatement.setString(n++, getCourseCodeCombined());
            insertStatement.setInt(n++, getDateCreated());
            insertStatement.setInt(n++, getEndDate());
            insertStatement.setInt(n++, getLastModified());
            insertStatement.setBoolean(n++, isPersonal());
            insertStatement.setString(n++, new Gson().toJson(getRelatedNotes()));
            insertStatement.setString(n++, new Gson().toJson(getRelatedTasks()));
            insertStatement.setInt(n++, getStatus());
            insertStatement.setString(n++, getTags().asJsonString());
            insertStatement.setString(n++, getTaskDesc());
            insertStatement.setString(n++, getTaskName());
            insertStatement.setString(n++, getTaskOwnerNameCombined());

            insertStatement.execute();
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
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
        try {

            String removeSql    = "DELETE FROM " + CoreDataRules.tables.tasks + " WHERE id = " + getId();

            PreparedStatement removeStatement = DB.getConnection().prepareStatement(removeSql);

            removeStatement.execute();
            return true;

        }catch (SQLException e)
        {
            System.out.println(CoreUtils.getStackTrace(e));
        }
        return false;
    }
}
