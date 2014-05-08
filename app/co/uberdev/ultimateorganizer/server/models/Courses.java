package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import play.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oguzbilgener on 15/04/14.
 */
public class Courses extends CoreCourses implements CoreSelectable
{
    @Override
    public String getTableName() {
        return CoreDataRules.tables.courses;
    }

    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit) {
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

                add(course);
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
