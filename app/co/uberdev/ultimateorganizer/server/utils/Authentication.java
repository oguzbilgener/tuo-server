package co.uberdev.ultimateorganizer.server.utils;

import co.uberdev.ultimateorganizer.core.CoreCrypto;
import co.uberdev.ultimateorganizer.server.models.User;

public class Authentication
{
    public static User getAuthenticatedUser(String publicKey, String verification, String requestBodyText)
    {
        // Connect to database and get the related user from public key
        User relatedUser = User.fromPublicKey(publicKey);

        // There must exist a related user with given public key.
        // Moreover, the request body text must not be empty or null
        if(relatedUser == null || relatedUser.getId() <= 0
            || requestBodyText == null || requestBodyText.length() == 0 )
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
