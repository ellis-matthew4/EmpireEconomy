package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class OpenShop extends PluginCommand {

    public OpenShop() {
        super("openshop");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        ZoneHandler zh = new ZoneHandler();
        Zone z = zh.getZone(p.getLocation());
        if (z == null || !z.owner.equals(p.getDisplayName())) {
            p.sendMessage("§4[SYSTEM] You are not currently inside one of your zones.");
        } else {
            if (z.shop == null) {
                z.addShop(new Shop(commandCall.getArg(0).arg, p.getDisplayName()));
                p.sendMessage("§e[SYSTEM] Shop Opened.");
            } else {
                p.sendMessage("§4[SYSTEM] There is already an open shop in this zone.");
            }
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}
