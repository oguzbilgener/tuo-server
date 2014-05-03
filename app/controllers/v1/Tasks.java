package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreTask;
import co.uberdev.ultimateorganizer.server.models.Task;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;


import java.sql.SQLException;


/**
 * Created by ata on 5/1/14.
 */
public class Tasks extends Controller {

    public static Result insert(String public_key, String signature) throws SQLException
    {
        System.out.println("A");
        //TODO try catch ?
        Http.RequestBody reqBody = request().body();
        System.out.println("B");
        if(reqBody == null)
            System.out.println("null");
        else
            reqBody.toString();
        String requestBody =  request().body().asText();
        System.out.println("C");

        System.out.println("pkey, sig, respNode:"+ public_key+ ","+ signature +","+request().body().asJson().asText());
        System.out.println("D");
       User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
       if(authUser != null)
       {

           Task toAdd = (Task) CoreTask.fromJson(requestBody, CoreTask.class);
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

        JsonNode requestNode = request().body().asJson();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestNode.asText());

        if(authUser != null)
        {
            Task toUpdate = (Task) CoreTask.fromJson(requestNode.asText(), CoreTask.class);

            if(toUpdate.update())
                return ok();
            else
                return internalServerError();
        }else
            return unauthorized();

    }

    public static Result remove(String publicKey, String signature)
    {
        JsonNode requestNode = request().body().asJson();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestNode.asText());

        if(authUser != null)
        {
            Task toRemove = (Task) CoreTask.fromJson(requestNode.asText(), CoreTask.class);

            if(toRemove.remove())
                return ok();
            else
                return internalServerError();


        }else
            return unauthorized();
    }


}