package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Wand extends PluginCommand{

    public Wand() {
        super("wand");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        ItemStack is = new ItemStack(Material.STICK);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName("Zoning Wand");
        is.setItemMeta(ism);
        if (p.getInventory().getItemInMainHand() != null
            && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
            p.getInventory().setItemInMainHand(is);
        } else {
            p.getInventory().addItem(is);
        }
        p.sendMessage("A Zoning Wand has been placed in your inventory.");
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(0);
    }
}
