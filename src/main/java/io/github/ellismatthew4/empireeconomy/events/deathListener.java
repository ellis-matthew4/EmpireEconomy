package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.EmpireEconomy;
import io.github.ellismatthew4.empireeconomy.data.Data;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class deathListener implements Listener {
    private final Data data = DataStoreService.getInstance().data;
    private final EmperorService emperorService = EmperorService.getInstance();
    private EmpireEconomy plugin;

    public deathListener(EmpireEconomy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (data.challengeActive && player.getDisplayName() == data.challenger) {
                data.challengeActive = false;
                emperorService.getEmperor().sendMessage("Congratulations on defeating your challenger!");
            }

            if (player.getKiller() instanceof Player) {
                Player killer = (Player) player.getKiller();
                if (emperorService.isEmperor(player.getDisplayName())) {

                    Server server = Bukkit.getServer();
                    ConsoleCommandSender sender = server.getConsoleSender();
                    emperorService.setEmperor(killer.getDisplayName());

                    server.dispatchCommand(sender, "title @a subtitle \"has been deposed as Emperor!\"");
                    server.dispatchCommand(sender, "title @a title \"§4" + player.getDisplayName() + "\"");
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        server.dispatchCommand(sender, "title @a subtitle \"has been crowned Emperor!\"");
                        server.dispatchCommand(sender, "title @a title \"§6" + killer.getDisplayName() + "\"");
                    }, 100);
                }
            }
        }
    }
}