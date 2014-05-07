package controllers.v1;


import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreUtils;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import play.db.DB;
import play.mvc.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by ata on 5/1/14.
 */
public class PublicFeed extends Controller {


    public static Result index()
    {
        return ok("lel");
    }
}
