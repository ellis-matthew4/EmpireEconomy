package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Emperor extends PluginCommand {

    public Emperor() {
        super("emperor");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player player = senderContainer.getPlayer();
        if (player.hasPermission("ee.*")) {
            Player target = Bukkit.getPlayer(commandCall.getArg(0).arg);
            emperorService.setEmperor(target.getDisplayName());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title @a subtitle \"has been crowned Emperor!\"");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title @a title \"§6" + target.getDisplayName() + "\"");
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}
