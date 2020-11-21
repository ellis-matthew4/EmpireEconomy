package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import org.bukkit.entity.Player;

public class DeleteWarp extends PluginCommand {
    WarpHandler warpHandler;
    public DeleteWarp() {
        super("deletewarp");
        this.warpHandler = new WarpHandler();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        String warp = commandCall.getArg(0).arg;
        Player p = senderContainer.getPlayer();
        boolean success = warpHandler.deleteWarp(warp, p.getDisplayName());
        if (success) {
            p.sendMessage("§e[SYSTEM] Warp deleted.");
        } else {
            p.sendMessage("§4[SYSTEM] Warp not found.");
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}
