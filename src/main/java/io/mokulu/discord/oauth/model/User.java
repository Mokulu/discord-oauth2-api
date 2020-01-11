package io.mokulu.discord.oauth.model;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class User
{
    private String id;
    private String username;
    private String avatar;
    private String discriminator;
    private Boolean bot;
    private Boolean system;
    @SerializedName("mfa_enabled")
    private Boolean mfaEnabled;
    private String locale;
    private Boolean verified;
    private String email;
    private Long flags;
    @SerializedName("premium_type")
    private Integer premiumType;

    public String getFullUsername()
    {
        return username + "#" + discriminator;
    }
}
