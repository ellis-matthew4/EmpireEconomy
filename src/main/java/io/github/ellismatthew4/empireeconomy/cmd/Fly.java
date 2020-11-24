package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Fly extends PluginCommand {
    private JavaPlugin plugin;

    public Fly(JavaPlugin plugin) {
        super("fly");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player player = senderContainer.getPlayer();
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage("§e[SYSTEM] Flight has been toggled.");
        if (data.challengeActive) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                player.setAllowFlight(!player.getAllowFlight());
            }, 0L, 40);
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(0);
    }
}
