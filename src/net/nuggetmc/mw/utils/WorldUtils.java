package net.nuggetmc.mw.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class WorldUtils {

    private static Random RANDOM = new Random();

    public static Location nearby(Player player) {
        Location loc = player.getLocation();
        loc.setY(255);
        loc.add(RANDOM.nextInt(60) - 30, 0, RANDOM.nextInt(60) - 30);

        for (int y = 255; y >= 1; y--) {
            loc.setY(y);

            if (loc.getBlock().getType().isSolid()) {
                break;
            }
        }

        return loc.add(0, 2, 0);
    }
}
