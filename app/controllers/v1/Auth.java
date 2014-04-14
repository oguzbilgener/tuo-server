package controllers.v1;

import co.uberdev.ultimateorganizer.server.utils.Authentication;
import co.uberdev.ultimateorganizer.server.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;

public class Auth extends Controller
{

    public static Result login()
    {
        JsonNode requestNode = request().body().asJson();

        // Required parameters for login: email (String) & password (String)
        try
        {
            // try to get those parameters from json body
            String userEmail = requestNode.findValue("email").asText();
            String userPassword = requestNode.findValue("password").asText();

            User loginUser = new User();
            loginUser.setEmailAddress(userEmail);
            loginUser.setPassword(userPassword);

            if(loginUser.tryLogin())
            {
                // remove user's unencrypted password for security reasons
                loginUser.setPassword("");
                // Successful login. return user details, including secret token and public key
                return ok(loginUser.toString());
            }
            else
            {
                // unsuccessful login. 401 Unauthorized!
                return unauthorized();
            }
        }
        catch (Exception e)
        {
            // The json body does not contain these parameters. 400 Bad request!
            return badRequest(e.toString() + " | " + requestNode.asText());
        }
    }

    public static Result verify(String publicKey, String hashBody)
    {

        try
        {
            User verifiedUser = Authentication.getAuthenticatedUser(publicKey, hashBody, request().body().asText());
            if(verifiedUser != null)
            {
                return ok(verifiedUser.asJsonString());
            }else
            {
                return unauthorized();
            }
        }
        catch(Exception e)
        {
            return badRequest();
        }
    }

    public static Result register()
    {
        JsonNode requestNode = request().body().asJson();
        try
        {

        }
        catch(Exception e)
        {

        }
        return null;
    }

}
