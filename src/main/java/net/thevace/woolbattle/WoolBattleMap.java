package net.thevace.woolbattle;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WoolBattleMap {
    private final String name;
    private final int teamSize;
    private final int teamCount;
    private final Map<Integer, Location> teamSpawns;

    public WoolBattleMap(String name, int teamSize, int teamCount, Map<Integer, Location> teamSpawns) {
        this.name = name;
        this.teamSize = teamSize;
        this.teamCount = teamCount;
        this.teamSpawns = teamSpawns;
    }


    public String getName() {
        return name;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public Map<Integer, Location> getTeamSpawns() {
        return teamSpawns;
    }
}
