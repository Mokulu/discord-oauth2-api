package io.mokulu.discord.oauth.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Flag {
    STAFF(0x1),
    PARTNER(0x2),
    HYPESQUAD(0x4),
    BUG_HUNTER_LEVEL_1(0x8),
    HYPESQUAD_ONLINE_HOUSE_1(0x40),
    HYPESQUAD_ONLINE_HOUSE_2(0x80),
    HYPESQUAD_ONLINE_HOUSE_3(0x100),
    PREMIUM_EARLY_SUPPORTER(0x200),
    TEAM_PSEUDO_USER(0x400),
    BUG_HUNTER_LEVEL_2(0x4000),
    VERIFIED_BOT(0x10000),
    VERIFIED_DEVELOPER(0x20000),
    CERTIFIED_MODERATOR(0x40000),
    BOT_HTTP_INTERACTIONS(0x80000),
    ACTIVE_DEVELOPER(0x400000),
    ;

    private final long value;

    boolean isIn(long flag) {
        return (flag & value) == value;
    }
}
