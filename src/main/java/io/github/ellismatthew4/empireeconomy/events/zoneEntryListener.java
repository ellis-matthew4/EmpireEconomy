package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class zoneEntryListener {
    private JavaPlugin plugin;
    private ZoneHandler zoneHandler;
    private HashMap<Player, Zone> cache;

    public zoneEntryListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.zoneHandler = new ZoneHandler();
        cache = new HashMap<Player, Zone>();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                onPlayerWalk(p);
            }
        }, 0L, 20);
    }

    public void onPlayerWalk(Player p) {
        Zone last = cache.get(p);
        Zone z = zoneHandler.getZone(p.getLocation());
        String message = "";
        if (z != null && last != z) {
            message = z.msg;
        }
        if (!message.isEmpty()) {
            p.sendMessage(message);
        }
        cache.put(p, z);
    }
}
