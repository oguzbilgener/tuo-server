package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreTask;
import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.mvc.*;

public class Auth extends Controller
{

    public static Result login()
    {
        JsonNode requestNode = request().body().asJson();

        try
        {
            String userEmail = requestNode.get("email").toString();
            String userPassword = requestNode.get("password").toString();

            return ok("hello "+userEmail+", "+userPassword);
        }
        catch (Exception e)
        {
            return badRequest();
        }
    }

    public static Result register()
    {
        return ok("reg");
    }

}
