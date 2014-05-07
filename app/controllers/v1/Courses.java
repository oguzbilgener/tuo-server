package controllers.v1;
import co.uberdev.ultimateorganizer.core.CoreCourse;
import co.uberdev.ultimateorganizer.server.models.Course;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

/**
 * Created by ata, Edited by guraybaydur on 5/7/14.
 */
public class Courses extends Controller {

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
}
