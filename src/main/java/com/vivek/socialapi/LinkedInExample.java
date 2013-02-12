package com.vivek.socialapi;

import com.vivek.socialapi.scribe.LinkedInApiScope;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.Scanner;

/**
 * This example builds upon the one which is written by Pablo Fernandez over here.
 * https://github.com/fernandezpablo85/scribe-java/blob/master/src/test/java/org/scribe/examples/LinkedInExample.java
 *
 * Wrote this example as I spent a lot of time debugging & exploring the community to figure out how to
 * get the LinkedIn API {https://developer.linkedin.com/documents/authentication#granting} permission *scope* working.
 *
 * @author: Vivek Kondur
 * @since: 1.0
 */
public class LinkedInExample {

    //If you want additional parameters then make the necessary changes to the PROTECTED_RESOURCE_URL
    private static String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~:(first-name,last-name,email-address,location,honors,skills)";

    //TODO: Substitute your LinkedIN apiKey & secretKey and are good test.
    private static final String apiKey = "";
    private static final String secretKey = "";



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        LinkedInApiScope.addScopePermission("r_network");
        LinkedInApiScope.addScopePermission("r_fullprofile");
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInApiScope.class)
                .apiKey(apiKey)
                .apiSecret(secretKey)
                .build();
        System.out.println("=== LinkedIn's OAuth Workflow ===");
        System.out.println();


        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println(" Secret  "+requestToken.getSecret());
        System.out.println(" Token   "+requestToken.getToken());
        System.out.println(" RAW response :::   "+requestToken.getRawResponse());
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");

        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
    }


}
