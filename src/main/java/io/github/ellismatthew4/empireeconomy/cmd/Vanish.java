package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Vanish extends PluginCommand {
    private JavaPlugin plugin;

    public Vanish(JavaPlugin plugin) {
        super("vanish");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player player = senderContainer.getPlayer();
        player.setInvisible(!player.isInvisible());
        player.sendMessage("§e[SYSTEM] Vanish has been toggled.");
        if (data.challengeActive) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                player.setInvisible(!player.isInvisible());
            }, 0L, 40);
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(0) && validationHelper.isSenderEmperor();
    }
}
