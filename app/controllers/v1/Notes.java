package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreNote;
import co.uberdev.ultimateorganizer.server.models.Note;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

/**
 * Created by ata on 5/2/14.
 */
public class Notes extends Controller{

    public static Result insert(String publicKey, String signature) throws SQLException
    {

        //TODO try catch ?
        String requestBody = request().body().asJson().toString();


        User authUser = Authentication.getAuthenticatedUser(publicKey, signature, requestBody);
        if(authUser != null)
        {

            Note toAdd = Note.fromJson(requestBody, Note.class);
            toAdd.setOwnerId(authUser.getId());

            if(toAdd.insert())
                return ok();
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
            Note toUpdate = Note.fromJson(requestBody, Note.class);

            if(toUpdate.update())
                return ok();
            else
                return internalServerError();
        }else
            return unauthorized();

    }


    public static Result remove(String publicKey, String signature)
    {
        String requestBody = request().body().asJson().asText();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestBody);

        if(authUser != null)
        {
            Note toRemove = Note.fromJson(requestBody, Note.class);

            if(toRemove.remove())
                return ok();
            else
                return internalServerError();


        }else
            return unauthorized();
    }
}
