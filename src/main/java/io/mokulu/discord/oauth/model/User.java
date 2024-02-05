package io.mokulu.discord.oauth.model;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Data
@Setter(AccessLevel.PRIVATE)
public class User
{
    private String id;
    private String username;
    private String avatar;
    private String discriminator;
    @SerializedName("global_name")
    private String globalName;
    private Boolean bot;
    private Boolean system;
    @SerializedName("mfa_enabled")
    private Boolean mfaEnabled;
    private String banner;
    @SerializedName("accent_color")
    private String accentColor;
    private String locale;
    private Boolean verified;
    private String email;
    private Long flags;
    @SerializedName("premium_type")
    private Integer premiumType;
    @SerializedName("public_flags")
    private Integer publicFlags;
    @SerializedName("avatar_decoration")
    private String avatarDecoration;

    public String getFullUsername()
    {
        if (discriminator == null || discriminator.isEmpty() || discriminator.equals("0"))
        {
            return username;
        }
        return username + "#" + discriminator;
    }


    /**
     * Gets the user's flags.
     * @return List of flags
     */
    public List<Flag> getFlagList()
    {
        List<Flag> flagList = new LinkedList<>();
        for (Flag flag : Flag.values())
        {
            if (flag.isIn(flags))
            {
                flagList.add(flag);
            }
        }
        return flagList;
    }


    /**
     * Gets the user's public flags.
     * @return List of public flags
     */
    public List<Flag> getPublicFlagList()
    {
        List<Flag> flagList = new LinkedList<>();
        for (Flag flag : Flag.values())
        {
            if (flag.isIn(publicFlags))
            {
                flagList.add(flag);
            }
        }
        return flagList;
    }


    /**
     * Gets the user premium type.
     * @return Premium Type
     */
    public PremiumType getPremiumType()
    {
        for (PremiumType value : PremiumType.values()) {
            if (value.isType(premiumType)) {
                return value;
            }
        }
        return PremiumType.NONE;
    }
}
