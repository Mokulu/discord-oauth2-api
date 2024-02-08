package io.mokulu.discord.oauth.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PremiumType {
    NONE(0),
    NITRO_CLASSIC(1),
    NITRO(2),
    NITRO_BASIC(3)
    ;

    private final long value;

    boolean isType(long flag) {
        return flag == value;
    }
}
