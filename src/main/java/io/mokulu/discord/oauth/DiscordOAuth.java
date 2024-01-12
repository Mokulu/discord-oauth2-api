package io.mokulu.discord.oauth;

import static io.mokulu.discord.oauth.DiscordAPI.BASE_URI;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hc.core5.net.URIBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.mokulu.discord.oauth.model.TokensResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DiscordOAuth
{
    private static final Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
    private static final String GRANT_TYPE_AUTHORIZATION = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private final String clientID;
    private final String clientSecret;
    private final String redirectUri;
    private final String[] scope;

    /**
     * Converts a JSON string to a TokensResponse object.
     * @param str The JSON string.
     * @return The TokensResponse object.
     */
    private static TokensResponse toObject(String str)
    {
        return gson.fromJson(str, TokensResponse.class);
    }

    /**
     * Generates a Discord OAuth2 authorization URL.
     * @return The URL.
     */
    public String getAuthorizationURL()
    {
        return getAuthorizationURL(null);
    }

    /**
     * Generates a Discord OAuth2 authorization URL.
     * @param state Optional state to pass to the callback.
     * @return The URL.
     */
    public String getAuthorizationURL(String state)
    {
        URIBuilder builder;
        try
        {
            builder = new URIBuilder(BASE_URI + "/oauth2/authorize");
        }
        catch (URISyntaxException e)
        {
            log.error("Failed to initialize URIBuilder", e);
            return null;
        }
        builder.addParameter("response_type", "code");
        builder.addParameter("client_id", clientID);
        builder.addParameter("redirect_uri", redirectUri);
        if (state != null && !state.isEmpty())
        {
            builder.addParameter("state", state);
        }

        // URI builder turns spaces into +, but Discord API doesn't support that in scope
        return builder + "&scope=" + String.join("%20", scope);
    }

    /**
     * Gets the access token and refresh token from the code.
     * @param code Authorization code.
     * @return The tokens.
     * @throws IOException Jsoup exception.
     */
    public TokensResponse getTokens(String code) throws IOException
    {
        Connection request = Jsoup.connect(BASE_URI + "/oauth2/token")
            .data("client_id", clientID)
            .data("client_secret", clientSecret)
            .data("grant_type", GRANT_TYPE_AUTHORIZATION)
            .data("code", code)
            .data("redirect_uri", redirectUri)
            .data("scope", String.join(" ", scope))
            .ignoreContentType(true);

        String response = request.post().body().text();

        return toObject(response);
    }

    /**
     * Refreshes the access token using the refresh token.
     * @param refreshToken The refresh token to use.
     * @return The new tokens.
     * @throws IOException Jsoup exception.
     */
    public TokensResponse refreshTokens(String refreshToken) throws IOException
    {
        Connection request = Jsoup.connect(BASE_URI + "/oauth2/token")
            .data("client_id", clientID)
            .data("client_secret", clientSecret)
            .data("grant_type", GRANT_TYPE_REFRESH_TOKEN)
            .data("refresh_token", refreshToken)
            .ignoreContentType(true);

        String response = request.post().body().text();

        return toObject(response);
    }
}
