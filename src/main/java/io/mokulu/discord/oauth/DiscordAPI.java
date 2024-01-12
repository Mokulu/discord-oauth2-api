package io.mokulu.discord.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.mokulu.discord.oauth.model.Connection;
import io.mokulu.discord.oauth.model.Guild;
import io.mokulu.discord.oauth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DiscordAPI
{
    public static final String BASE_URI = "https://discord.com/api";
    private static final Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
    private final String accessToken;

    private static <T> T toObject(String str, Class<T> clazz)
    {
        return gson.fromJson(str, clazz);
    }

    /**
     * Gets the version of the library.
     * @return Version
     * @throws IOException Properties exception
     */
    private String getVersion() throws IOException
    {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
        java.util.Properties prop = new java.util.Properties();
        prop.load(resourceAsStream);
        return prop.getProperty("version");
    }

    /**
     * Sets the headers for the request.
     * @param request Request
     * @throws IOException Properties exception from getVersion()
     */
    private void setHeaders(org.jsoup.Connection request) throws IOException
    {
        request.header("Authorization", "Bearer " + accessToken);
        request.header("User-Agent",
            String.format("Mokulu-Discord-OAuth2-Java, version %s, platform %s %s", getVersion(), System.getProperty("os.name"),
                System.getProperty("os.version")));
    }

    /**
     * Handles the GET request.
     * @param path Path
     * @return Response
     * @throws IOException Jsoup or Version exception
     */
    private String handleGet(String path) throws IOException
    {
        org.jsoup.Connection request = Jsoup.connect(BASE_URI + path).ignoreContentType(true);
        setHeaders(request);

        return request.get().body().text();
    }

    /**
     * Fetches the user's Discord profile.
     * @return User
     * @throws IOException Json exception
     */
    public User fetchUser() throws IOException
    {
        return toObject(handleGet("/users/@me"), User.class);
    }

    /**
     * Fetches the user's Discord guilds.
     * @return List of guilds
     * @throws IOException Json exception
     */
    public List<Guild> fetchGuilds() throws IOException
    {
        return Arrays.asList(toObject(handleGet("/users/@me/guilds"), Guild[].class));
    }

    /**
     * Fetches the user's Discord connections.
     * @return List of connections
     * @throws IOException Json exception
     */
    public List<Connection> fetchConnections() throws IOException
    {
        return Arrays.asList(toObject(handleGet("/users/@me/connections"), Connection[].class));
    }
}
