package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.*;
import org.bukkit.entity.Player;
import java.util.List;

public class Properties extends PluginCommand {
    private ZoneHandler zoneHandler;
    private WarpHandler warpHandler;

    public Properties() {
        super("properties");
        this.zoneHandler = new ZoneHandler();
        this.warpHandler = new WarpHandler();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        String key = commandCall.getArg(1).arg.toLowerCase();
        Player p = senderContainer.getPlayer();
        if (key.equals("zones") || key.equals("all")) {
            List<Zone> zones = zoneHandler.zonesByPlayer(p);
            for (Zone z : zones) {
                p.sendMessage(z.toString());
            }
        }
        if (key.equals("warps") || key.equals("all")) {
            List<WarpPoint> warps = warpHandler.warpsByPlayer(p);
            for (WarpPoint w : warps) {
                p.sendMessage(w.toString());
            }
        }
        if (!(key.equals("zones") || key.equals("all") || key.equals("warps")))
            return false;
        else
            return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidPlayer(commandCall.getArg(0))
                && validationHelper.isValidArgCount(2);
    }
}
