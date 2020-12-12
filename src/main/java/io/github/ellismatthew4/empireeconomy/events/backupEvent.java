package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class backupEvent {
    private JavaPlugin plugin;

    public backupEvent(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                DataStoreService.getInstance().writeData();
            }
        }, 0L, 6000);
    }
}
