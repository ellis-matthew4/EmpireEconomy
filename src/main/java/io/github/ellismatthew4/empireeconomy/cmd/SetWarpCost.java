package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class SetWarpCost extends PluginCommand{
    private WarpHandler warpHandler;

    public SetWarpCost() {
        super("setwarpcost");
        this.warpHandler = new WarpHandler();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        WarpPoint wp = warpHandler.getWarp(commandCall.getArg(0).arg, p.getDisplayName());
        if (wp != null) {
            wp.setCost(commandCall.getArg(1).asInt());
            p.sendMessage("§e[SYSTEM] Changed Warp Cost");
        } else {
            p.sendMessage("§4[SYSTEM] Warp not found");
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(2);
    }
}
