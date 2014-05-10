package co.uberdev.ultimateorganizer.server.models;

import co.uberdev.ultimateorganizer.core.*;
import com.google.gson.reflect.TypeToken;
import play.db.DB;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.uberdev.ultimateorganizer.core.CoreSelectable;

/**
 * Created by oguzbilgener on 14/04/14.
 */
public class Notes extends CoreNotes implements CoreSelectable {
    @Override
    public String getTableName() {
        return CoreDataRules.tables.notes;
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
                Note note = new Note(45646464);
                note.setId(set.getInt(CoreDataRules.columns.notes.id));
                note.setOwnerId(set.getInt(CoreDataRules.columns.notes.ownerId));
                note.setDateCreated(set.getInt(CoreDataRules.columns.notes.dateCreated));
                note.setContent(set.getString(CoreDataRules.columns.notes.content));
                note.setAttachment(set.getString(CoreDataRules.columns.notes.attachment));
                note.setLastModified(set.getInt(CoreDataRules.columns.notes.lastModified));



                add(note);
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

