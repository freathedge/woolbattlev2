package net.thevace.woolbattle.maps;

import net.thevace.woolbattle.WoolBattleMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class Blocks_2x1 extends WoolBattleMap {
    public Blocks_2x1() {
        Map<Integer, Location> spawns = new HashMap<>();
        World world = Bukkit.getWorld("world");
        spawns.put(1, new Location(world, 26.5, 95, 0.5, 90, 0));
        spawns.put(2, new Location(world, -25.5, 95, 0.5, -90, 0));
        super("Blocks-2x1", 1, 2, spawns);
    }
}
