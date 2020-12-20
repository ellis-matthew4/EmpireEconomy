package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class zoneEntryListener {
    private JavaPlugin plugin;
    private ZoneHandler zoneHandler;
    private HashMap<Player, Zone> cache;

    public zoneEntryListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.zoneHandler = ZoneHandler.getInstance();
        cache = new HashMap<Player, Zone>();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                onPlayerWalk(p);
            }
        }, 0L, 8);
    }

    public void onPlayerWalk(Player p) {
        Zone last = cache.get(p);
        Zone z = zoneHandler.getZone(p.getLocation());
        String message = "";
        if (z != null && last != z) {
            message = z.msg;
        }
        if (!message.isEmpty()) {
            if (message.contains(";")) {
                String[] msgs = message.split(";");
                Server server = Bukkit.getServer();
                ConsoleCommandSender sender = server.getConsoleSender();

                server.dispatchCommand(sender, "title " + p.getDisplayName() + " subtitle \"" + msgs[1] +"\"");
                server.dispatchCommand(sender, "title " + p.getDisplayName() + " title \"" + msgs[0] + "\"");
            } else {
                p.sendMessage(message);
            }
        }
        cache.put(p, z);
    }
}
