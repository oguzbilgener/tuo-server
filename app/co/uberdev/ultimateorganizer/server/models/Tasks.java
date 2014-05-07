package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try {
            int n = 1;
            String loadSql = "SELECT * FROM "+getTableName();
            if(sqlCriteria != null) {
                loadSql += " WHERE "+sqlCriteria;
            }

            PreparedStatement loadStatement = DB.getConnection().prepareStatement(loadSql);
            if(params != null)
            {
                for(String param : params)
                {
                    // TODO: test for non-string types
                    loadStatement.setString(n++, param);
                }
            }

            ResultSet set = loadStatement.executeQuery();

            while(set != null && set.next())
            {
                Task task = new Task();
                task.setId(set.getInt(CoreDataRules.columns.tasks.id));
                task.setOwnerId(set.getInt(CoreDataRules.columns.tasks.ownerId));
                task.setBeginDate(set.getInt(CoreDataRules.columns.tasks.beginDate));
                task.setCourseId(set.getInt(CoreDataRules.columns.tasks.courseId));
                task.setCourseCodeCombined(set.getString(CoreDataRules.columns.tasks.courseCodeCombined));
                task.setDateCreated(set.getInt(CoreDataRules.columns.tasks.dateCreated));
                task.setEndDate(set.getInt(CoreDataRules.columns.tasks.endDate));
                task.setLastModified(set.getInt(CoreDataRules.columns.tasks.lastModified));
                task.setPersonal(set.getBoolean(CoreDataRules.columns.tasks.personal));
                task.setRelatedNotes(set.getString(CoreDataRules.columns.tasks.relatedNotes)); //TODO
                task.setRelatedTasks(set.getString(CoreDataRules.columns.tasks.relatedTasks));
                task.setStatus(set.getInt(CoreDataRules.columns.tasks.status));
                task.setTags(fromJson(set.getString(CoreDataRules.columns.tasks.tags), CoreTags.class)); //TODO
                task.setTaskDesc(set.getString(CoreDataRules.columns.tasks.taskDesc));
                task.setTaskName(set.getString(CoreDataRules.columns.tasks.taskName));
                task.setTaskOwnerNameCombined(set.getString(CoreDataRules.columns.tasks.taskOwnerNameCombined));


                // TODO: set devices

                add(task);
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
