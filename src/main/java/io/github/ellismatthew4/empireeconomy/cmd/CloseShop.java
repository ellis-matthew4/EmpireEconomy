package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class CloseShop extends PluginCommand {

    public CloseShop() {
        super("closeshop");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        ZoneHandler zh = new ZoneHandler();
        Zone z = zh.getZone(p.getLocation());
        if (z == null || !z.owner.equals(p.getDisplayName())) {
            p.sendMessage("§4[SYSTEM] You are not currently inside one of your zones.");
        } else {
            if (z.shop != null) {
                for (Listing l : z.shop.listings) {
                    p.getInventory().addItem(l.asItemStack());
                }
                z.addShop(null);
                p.sendMessage("§e[SYSTEM] Shop Closed.");
            } else {
                p.sendMessage("§4[SYSTEM] There is no shop in this zone.");
            }
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(0);
    }
}
