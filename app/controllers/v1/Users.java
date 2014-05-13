package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreUtils;
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
 * Created by ata on 5/6/14.
 */
public class Users extends Controller {

    public static Result getPublicFeed(String publicKey, String signature)
    {
        String requestBody =  request().body().asJson().toString();

        User authUser = Authentication.getAuthenticatedUser(publicKey, signature, requestBody);



        if(authUser != null)
        {
            try
            {
                return ok(authUser.getFeed().asJsonString());
            }
            catch(SQLException e)
            {
                return internalServerError(CoreUtils.getStackTrace(e));
            }
            catch(Exception e)
            {
                return internalServerError(CoreUtils.getStackTrace(e));
            }
        }
        else
            return unauthorized();

    }

    public static Result courses(String public_key, String signature)
    {
        JsonNode requestBodyJson = request().body().asJson();
        String requestBody =  requestBodyJson.toString();


        User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
        if(authUser != null)
        {
            co.uberdev.ultimateorganizer.server.models.Courses list = new co.uberdev.ultimateorganizer.server.models.Courses();

            try {

                list.loadFromDb(CoreDataRules.columns.courses.ownerId, new String[] {String.valueOf(authUser.getId())}, 0);

                return ok(list.asJsonString());

            }
            catch(Exception e)
            {
                return internalServerError(CoreUtils.getStackTrace(e));
            }

        }else
            return unauthorized();
    }

    public static Result tasks(String public_key, String signature)
    {
        JsonNode requestBodyJson = request().body().asJson();
        String requestBody =  requestBodyJson.toString();


        User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
        if(authUser != null)
        {
            co.uberdev.ultimateorganizer.server.models.Tasks list = new co.uberdev.ultimateorganizer.server.models.Tasks();

            try {

                list.loadFromDb(CoreDataRules.columns.tasks.ownerId, new String[] {String.valueOf(authUser.getId())}, 0);

                return ok(list.asJsonString());

            }
            catch(Exception e)
            {
                return internalServerError(CoreUtils.getStackTrace(e));
            }

        }else
            return unauthorized();
    }
}
