package co.uberdev.ultimateorganizer.server.utils;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.server.models.User;

public class Authentication
{
    public static User getAuthenticatedUser(String publicKey, String verification, String requestBodyText)
    {
        User relatedUser = User.fromPublicKey(publicKey);

        if(relatedUser == null || relatedUser.getId() <= 0)
        {
            return null;
        }

        String correctHash = CoreCrypto.hmacSha1(requestBodyText, relatedUser.getSecretToken());

        if(verification.equals(correctHash))
        {
            return relatedUser;
        }
        return null;
    }

}
