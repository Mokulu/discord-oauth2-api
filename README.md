# discord-oauth2-api
A small Discord OAuth2 API wrapper for Java.

## Features
* Generates the authorization URI.
* Code authorization for access token and refresh token.
* Refresh the access token with refresh token.
* Get the user, guilds, and connection info of a user from access token.

## Importing To Your Project
[Available on Jitpack](https://jitpack.io/#Mokulu/discord-oauth2-api/1.0.0)

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
	    <version>1.0.0</version>
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
	        implementation 'com.github.Mokulu:discord-oauth2-api:1.0'
	}
```

## Using the API
