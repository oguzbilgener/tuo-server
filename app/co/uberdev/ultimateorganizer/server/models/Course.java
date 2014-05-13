package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import play.db.DB;
import java.sql.*;

/**
 * Created by guraybaydur on 06/05/14.
 */
public class Course extends CoreCourse implements CoreStorable
{

    @Override
    public String getTableName() {
        return CoreDataRules.tables.courses;
    }

    @Override
    public boolean insert() {
        Connection connection = DB.getConnection();
        try
        {
            int n = 1;
            String insertSql = "INSERT INTO "+ getTableName() + " (" +
            CoreDataRules.columns.courses.id+", "+
            CoreDataRules.columns.courses.ownerId+", "+
            CoreDataRules.columns.courses.semester+", "+
            CoreDataRules.columns.courses.departmentCode+", "+
            CoreDataRules.columns.courses.courseCode+", "+
            CoreDataRules.columns.courses.sectionCode+", "+
            CoreDataRules.columns.courses.title+", "+
            CoreDataRules.columns.courses.instructor_name+", "+
            CoreDataRules.columns.courses.color+" "+
            ") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)";

            ResultSet generatedKeys;

            PreparedStatement insertStatement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(n++, getOwnerId());
            insertStatement.setString(n++, getCourseSemester());
            insertStatement.setString(n++, getDepartmentCode());
            insertStatement.setString(n++, getCourseCode());
            insertStatement.setInt(n++, getSectionCode());
            insertStatement.setString(n++, getCourseTitle());
            insertStatement.setString(n++, getInstructorName());
            insertStatement.setInt(n++, getCourseColor());

            insertStatement.execute();

            generatedKeys = insertStatement.getGeneratedKeys();

            if(generatedKeys.next())
                setId(generatedKeys.getLong(1));

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
            CoreDataRules.columns.courses.id+" = ?, "+
            CoreDataRules.columns.courses.ownerId+" = ?, "+
            CoreDataRules.columns.courses.semester+" = ?, "+
            CoreDataRules.columns.courses.departmentCode+" = ?, "+
            CoreDataRules.columns.courses.courseCode+" = ?, "+
            CoreDataRules.columns.courses.sectionCode+" = ?, "+
            CoreDataRules.columns.courses.title+" = ?, "+
            CoreDataRules.columns.courses.instructor_name+" = ?, "+
            CoreDataRules.columns.courses.color+" "+
            " WHERE "+CoreDataRules.columns.courses.id+" = "  + getId();


            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setLong(n++, getOwnerId());
            updateStatement.setString(n++, getCourseSemester());
            updateStatement.setString(n++, getDepartmentCode());
            updateStatement.setString(n++, getCourseCode());
            updateStatement.setInt(n++, getSectionCode());
            updateStatement.setString(n++, getCourseTitle());
            updateStatement.setString(n++, getInstructorName());
            updateStatement.setInt(n++, getCourseColor());

            updateStatement.execute();

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
            String removeSql = "DELETE FROM " + getTableName() + " WHERE " + CoreDataRules.columns.courses.id + " = ?" ;

            PreparedStatement removeStatement = connection.prepareStatement(removeSql);
            removeStatement.setLong(1, getId());

            removeStatement.execute();

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
