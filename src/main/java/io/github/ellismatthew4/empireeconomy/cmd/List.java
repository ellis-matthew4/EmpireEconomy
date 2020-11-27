package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class List extends PluginCommand {

    public List() {
        super("list");
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
                if (z.shop.listings.size() < 27) {
                    if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        int cost = commandCall.getArg(0).asInt();
                        ItemStack item = p.getInventory().getItemInMainHand();
                        z.shop.addListing(new Listing(cost, item));
                        p.getInventory().setItemInMainHand(null);
                        p.sendMessage("§e[SYSTEM] Listing added.");
                    } else {
                        p.sendMessage("You can't list that!");
                    }
                } else {
                    p.sendMessage("§4[SYSTEM] This Shop is full. Either de-list an item or open another Shop.");
                }
            } else {
                p.sendMessage("§4[SYSTEM] There is no shop in this zone.");
            }
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(1);
    }
}
