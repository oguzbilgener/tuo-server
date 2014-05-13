package controllers.v1;
import co.uberdev.ultimateorganizer.core.CoreCourse;
import co.uberdev.ultimateorganizer.core.CoreUtils;
import co.uberdev.ultimateorganizer.server.models.Course;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by guraybaydur on 5/7/14.
 */
public class Courses extends Controller {

    public static Result list(String public_key, String signature)
    {
        //Gets the request body as JSON obj
        JsonNode requestBodyJson = request().body().asJson();
        String requestBody =  requestBodyJson.toString();

        //Creating a user
        User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
        if(authUser != null)
        {
            co.uberdev.ultimateorganizer.server.models.Courses list = new co.uberdev.ultimateorganizer.server.models.Courses();

            try {
                ObjectMapper mapper = new ObjectMapper();

                Map<String,String> map = mapper.readValue(requestBody, HashMap.class);

                Set<String> keys = map.keySet();
                Iterator<String> it = keys.iterator();

                String sqlCriteria = "";
                String[] fields = new String[keys.size()];

                int i = 0;

                while(it.hasNext())
                {
                    String key = it.next();
                    String value = String.valueOf(map.get(key));

                    sqlCriteria +=  StringEscapeUtils.escapeSql(key).replace("'","")+" = ? ";
                    if(it.hasNext())
                        sqlCriteria += " AND ";

                    fields[i++] = value;
                }                     System.out.println(sqlCriteria);

                list.loadFromDb(sqlCriteria, fields, 0);

                return ok(list.asJsonString());

            }
            catch(Exception e)
            {
                return internalServerError(CoreUtils.getStackTrace(e));
            }

        }else
            return unauthorized();
    }

    public static Result insert(String publicKey, String signature) throws SQLException
    {

        //TODO try catch ?
        String requestBody = request().body().asJson().toString();


        User authUser = Authentication.getAuthenticatedUser(publicKey, signature, requestBody);
        if(authUser != null)
        {

            Course toAdd = Course.fromJson(requestBody, Course.class);
            toAdd.setOwnerId(authUser.getId());

            if(toAdd.insert())
                return ok(toAdd.asJsonString());
            else
                return internalServerError();

        }else
            return unauthorized();

    }

    public static Result update(String publicKey, String signature)
    {

        String requestBody = request().body().asJson().toString();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestBody);

        if(authUser != null)
        {
            Course toUpdate = Course.fromJson(requestBody, Course.class);


            if(toUpdate.update())
                return ok();
            else
                return internalServerError();
        }else
            return unauthorized();

    }

    public static Result remove(String publicKey, String signature)
    {
        String requestBody = request().body().asJson().toString();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestBody);

        if(authUser != null)
        {
            Course toRemove = Course.fromJson(requestBody, Course.class);

            if(toRemove.remove())
                return ok();
            else
                return internalServerError();


        }else
            return unauthorized();
    }

    public static Result add(String publicKey, String signature)
    {
        String requestBody = request().body().asJson().toString();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestBody);

        if(authUser != null)
        {
            Course toChange = Course.fromJson(requestBody, Course.class);
            toChange.setOwnerId(authUser.getId());
            if(toChange.update())
                return ok();
            else
                return internalServerError();
        }else
            return unauthorized();
    }
}
