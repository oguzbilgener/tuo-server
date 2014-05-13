package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by guraybaydur on 15/04/14.
 */
public class Courses extends CoreCourses implements CoreSelectable
{
    @Override
    //Finds the course part from DataRules and returns its name
    public String getTableName() {
        return CoreDataRules.tables.courses;
    }

    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit)
    {
        Connection connection = DB.getConnection();
        try {
            int n = 1;

            //Preparing a sql string to load
            String loadSql = "SELECT * FROM "+getTableName();

            //Checking whether the string is empty or not
            if(sqlCriteria != null) {
                loadSql += " WHERE "+sqlCriteria;
            }

            PreparedStatement loadStatement = connection.prepareStatement(loadSql);

            if(params != null)
            {
                for(String param : params)
                {
                    //Takes the parameteres as String
                    // TODO: test for non-string types
                    loadStatement.setString(n++, param);
                }
            }

            ResultSet set = loadStatement.executeQuery();

            //if there is a course in statement and has properties then...
            while(set != null && set.next())
            {
                //Creating a new course and getting the info from database one by one
                Course course = new Course();

                course.setId(set.getLong(CoreDataRules.columns.courses.id));
                course.setOwnerId(set.getLong(CoreDataRules.columns.courses.ownerId));
                course.setCourseSemester(set.getString(CoreDataRules.columns.courses.semester));
                course.setDepartmentCode(set.getString(CoreDataRules.columns.courses.departmentCode));
                course.setCourseCode(set.getString(CoreDataRules.columns.courses.courseCode));
                course.setSectionCode(set.getInt(CoreDataRules.columns.courses.sectionCode));
                course.setCourseTitle(set.getString(CoreDataRules.columns.courses.title));
                course.setInstructorName(set.getString(CoreDataRules.columns.courses.instructor_name));
                course.setCourseColor(set.getInt(CoreDataRules.columns.courses.color));
                // TODO: set devices

                //Adding it to core arrayList
                add(course);
            }

            loadStatement.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
