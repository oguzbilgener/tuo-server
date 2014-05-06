package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreUtils;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

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
                return badRequest();
            }
        }
        else
            return unauthorized();

    }
}
