package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.LoggerService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public class dailyEvent {
    private JavaPlugin plugin;

    public dailyEvent(JavaPlugin plugin) {
        this.plugin = plugin;
        clearStats();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long secs = (long) Math.ceil((c.getTimeInMillis() - System.currentTimeMillis()) / 1000) + 5;
        LoggerService.getInstance().info("Now: " + (int) (System.currentTimeMillis() / 1000));
        LoggerService.getInstance().info("Time until midnight: " + secs);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                clearStats();
            }
        }, secs * 20, 1728000);
    }

    private void clearStats() {
        DataStoreService.getInstance().data.clearStats();
    }
}
