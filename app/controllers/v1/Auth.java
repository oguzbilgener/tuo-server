package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.core.CoreTask;
import co.uberdev.ultimateorganizer.server.models.User;
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
            String userEmail = requestNode.findValue("email").asText();
            String userPassword = requestNode.findValue("password").asText();

            User loginUser = new User();
            loginUser.setEmailAddress(userEmail);
            loginUser.setPassword(userPassword);
            loginUser.hashPassword();


            // TODO: hmac-sha1 ile request body'i hashleyerek gonderilmis hash ile kiyasla.
            if(loginUser.tryLogin())
            {
                // basarili login
                // verileri cevap olarak ilet
                return ok("hello "+userEmail+", "+userPassword);
            }
            else
            {
                // basarisiz login
                return unauthorized();
            }
        }
        catch (Exception e)
        {
            return badRequest();
        }
    }

    public static Result verify(String public_key, String hashBody)
    {

        JsonNode requestNode = request().body().asJson();

        try
        {
            String secretKey = "null"; //
            String nakedBody = requestNode.findValue("body").asText();
            if(hashBody.equals(CoreCrypto.sha1(secretKey + nakedBody)))
            {
                //Login verified, process body
                return ok();
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
        return ok("reg");
    }

}
