# discord-oauth2-api
A small Discord OAuth2 API wrapper for Java.

## Features
* Generates the authorization URL.
* Code authorization for access token and refresh token.
* Refresh the access token with refresh token.
* Get the user, guilds, and connection info of a user from access token.

## Importing To Your Project
[![](https://jitpack.io/v/Mokulu/discord-oauth2-api.svg)](https://jitpack.io/#Mokulu/discord-oauth2-api)

### Maven
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```
	<dependency>
	    <groupId>com.github.Mokulu</groupId>
	    <artifactId>discord-oauth2-api</artifactId>
	    <version>1.0.4</version>
	</dependency>
```

### Gradle
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
	dependencies {
	        implementation 'com.github.Mokulu:discord-oauth2-api:1.0.4'
	}
```

## Using the API
### Creating the OAuth handler
```java
import io.mokulu.discord.oauth.DiscordOAuth;

DiscordOAuth oauthHandler = new DiscordOAuth(clientID: String, clientSecret: String, redirectUri: String, scope: String[]);
```

#### Generating the OAuth URL
```java
String authURL = oauthHandler.getAuthorizationURL(state: String);
```
`state` will be ignored by passing null.

#### Authorizing the `code`
```java
import io.mokulu.discord.oauth.model.TokensResponse;

TokensResponse tokens = oauthHandler.getTokens(code: String);
String accessToken = tokens.getAccessToken();
String refreshToken = tokens.getRefreshToken();
```

#### Refreshing the Access Token
```java
TokensResponse tokens = oauthHandler.refreshTokens(refresh_token: String);
```

### Creating the API handler
```java
import io.mokulu.discord.oauth.DiscordAPI;

DiscordAPI api = new DiscordAPI(access_token: String);
```

The following API fetch calls will throw `IOException (HttpStatusException)` when access is denied due to invalid scope or expired token.

#### Fetching User
Scope `identity` is required.
Scope `email` is required for `email` and `verified` values.
```java
import io.mokulu.discord.oauth.model.User;

User user = api.fetchUser();
```

#### Fetching Guilds
Scope `guilds` is required.
```java
import io.mokulu.discord.oauth.model.Guild;

List<Guild> guilds = api.fetchGuilds();
```

#### Fetching Connections
Scope `connections` is required.
```java
import io.mokulu.discord.oauth.model.Connection;

List<Connection> connections = api.fetchConnections();
```
