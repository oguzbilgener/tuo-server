package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreTask;
import co.uberdev.ultimateorganizer.core.CoreUtils;
import co.uberdev.ultimateorganizer.server.models.Task;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by ata on 5/1/14.
 */
public class Tasks extends Controller
{
    public static Result list(String public_key, String signature)
    {
        JsonNode requestBodyJson = request().body().asJson();
        String requestBody =  requestBodyJson.toString();


        User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
        if(authUser != null)
        {
            co.uberdev.ultimateorganizer.server.models.Tasks list = new co.uberdev.ultimateorganizer.server.models.Tasks();

            try {
                ObjectMapper mapper = new ObjectMapper();

                Map<String,String> map = mapper.readValue(requestBody, HashMap.class);

                Set<String> keys = map.keySet();
                Iterator<String> it = keys.iterator();

                String sqlCriteria = "";
                String[] fields = new String[keys.size()*2];

                int i = 0;

                while(it.hasNext())
                {
                    String key = it.next();
                    String value = map.get(key);

                    sqlCriteria += " ? = ? ";
                    if(it.hasNext())
                        sqlCriteria += " AND ";

                    fields[i++] = key;
                    fields[i++] = value;
                }

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

    public static Result insert(String public_key, String signature)
    {

        String requestBody =  request().body().asJson().toString();


        User authUser = Authentication.getAuthenticatedUser(public_key,signature,requestBody);
        if(authUser != null)
           {

               Task toAdd = Task.fromJson(requestBody, Task.class);

               try
               {
                   if(toAdd.insert())
                        return ok(toAdd.asJsonString());
                   else
                       return internalServerError();
               }
               catch(Exception e)
               {
                   return internalServerError(CoreUtils.getStackTrace(e));
               }

           }else
               return unauthorized();

    }

    public static Result update(String publicKey, String signature)
    {

        String requestBody = request().body().asJson().toString();

        User authUser = Authentication.getAuthenticatedUser(publicKey,signature,requestBody);

        if(authUser != null)
        {
            Task toUpdate = Task.fromJson(requestBody, Task.class);

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
            Task toRemove = Task.fromJson(requestBody, Task.class);

            if(toRemove.remove())
                return ok();
            else
                return internalServerError();


        }else
            return unauthorized();
    }



}
