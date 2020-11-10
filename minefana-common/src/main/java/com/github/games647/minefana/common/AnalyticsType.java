package com.github.games647.minefana.common;

import org.influxdb.dto.Point;

public enum AnalyticsType {

    TPS,
    PLAYERS,
    JVM,
    PROTOCOL,

    USERS,
    BUNGEE_PLAYER_PER_SERVER,

    WORLD,

    FORGE_USER,
    FORGE_MODS,

    LOCALE,
    PING,
    COUNTRY;

    public Point.Builder newPoint() {
        return Point.measurement(name());
    }
}
