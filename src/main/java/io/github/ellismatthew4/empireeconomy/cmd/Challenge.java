package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Challenge extends PluginCommand {
    private final JavaPlugin plugin;

    public Challenge(JavaPlugin plugin) {
        super("challenge");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Player e = emperorService.getEmperor();
        if (e == null) {
            p.sendMessage("Emperor is offline.");
            return true;
        }
        if (data.challenger != null) {
            p.sendMessage("Someone has challenged the Emperor in the past 90 minutes. Please try again later.");
            return true;
        }
        data.challenger = p.getDisplayName();
        e.sendMessage("You have been challenged by " + p.getDisplayName() + "!");
        data.challengeActive = true;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            data.challenger = null;
            data.challengeActive = false;
            e.sendMessage("You are now vulnerable to challenges again.");
        }, 108000);
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(0);
    }
}
