package com.vivek.socialapi;

import com.vivek.socialapi.scribe.LinkedInApiScope;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.Scanner;

/**
 * A stand alone test class to show case the Twitter Authentication & API request for User Timeline.
 * This also demonstrates the Twitter's API keys & secrets that have been compromised as of 8 March 2013
 * @link Visit here to get the list of API Keys https://gist.github.com/vevck/5118041
 *
 *
 * @author: vivek
 * @since: 1.0
 */
public class TwitterClient {

    private static final String REQUEST_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=twitter&count=2";
    //iPhone
    /*private static final String apiKey = "IQKbtAYlXLripLGPWd0HUA";
    private static final String secretKey = "GgDYlkSvaPxGxC4X8liwpUoqKwwr3lCADbz8A7ADU";*/

    //Android
    /*private static final String apiKey = "3nVuSoBZnx6U4vzUxf5w";
    private static final String secretKey = "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys";*/

    //iPad
    /*private static final String apiKey = "CjulERsDeqhhjSme66ECg";
    private static final String secretKey = "IQWdVyqFxghAtURHGeGiWAsmCAGmdW3WmbEx6Hck";*/

    //Tweetdeck
    /*private static final String apiKey = "yT577ApRtZw51q4NPMPPOQ";
    private static final String secretKey = "3neq3XqN5fO3obqwZoajavGFCUrC42ZfbrLXy5sCv8";*/

    //Google TV
    /*private static final String apiKey = "iAtYJ4HpUVfIUoNnif1DA";
    private static final String secretKey = "172fOpzuZoYzNYaU3mMYvE8m8MEyLbztOdbrUolU";*/

    //Mac
    private static final String apiKey = "3rJOl1ODzm9yZy63FACdg";
    private static final String secretKey = "5jPoQ5kQvMJFDYRNE8bQ4rHuds4xJqhvgNJM4awaE8";




    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(apiKey)
                .apiSecret(secretKey)
                .build();
        System.out.println("=== Twitter OAuth Workflow ===");
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
        OAuthRequest request = new OAuthRequest(Verb.GET, REQUEST_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");


    }
}
