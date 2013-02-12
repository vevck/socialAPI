package com.vivek.socialapi.scribe;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.Token;

/**
 * To get the LinkedIn REST API working for the *scope* parameters we have to override the
 * LinkedInApi#getRequestTokenEndpoint() method.
 *
 * Based on the scope chosen by the end user, the requestTokenEndpoint will be build
 * Ex 1: https://api.linkedin.com/uas/oauth/requestToken?scope=r_network+r_fullprofile
 * Ex 2: https://api.linkedin.com/uas/oauth/requestToken?scope=r_basicprofile+r_emailaddress
 *
 * @author: vivek
 * @since: 1.0
 */
public class LinkedInApiScope extends LinkedInApi {

    private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";

    private static String permissions = "";

    public static void addScopePermission(String permission)
    {
        if(!permissions.contains(permission))
        {
            if(permissions != "")
                permissions += "+";
            permissions += permission;
        }
    }

    public static void clearScopePermissions()
    {
        permissions = "";
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://api.linkedin.com/uas/oauth/accessToken";
    }

    @Override
    public String getRequestTokenEndpoint()
    {
        String requestTokenEndpointURL = "https://api.linkedin.com/uas/oauth/requestToken?scope=" + permissions;
        return requestTokenEndpointURL;

    }

    @Override
    public String getAuthorizationUrl(Token requestToken)
    {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
