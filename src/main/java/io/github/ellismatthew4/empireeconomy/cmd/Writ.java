package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Writ extends PluginCommand {

    public Writ() {
        super("writ");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.getType() == Material.WRITABLE_BOOK) {
            BookMeta bm = (BookMeta) item.getItemMeta();
            bm.setAuthor("Emperor " + p.getDisplayName());
            ArrayList<String> l = new ArrayList<String>();
            l.add("A project commissioned by the Emperor");
            l.add("§aPayment will be determined by the quality of work");
            bm.setLore(l);
            String title = commandCall.getArg(0).arg;
            bm.setTitle(title);

            ItemStack result = new ItemStack(Material.WRITTEN_BOOK);
            result.setItemMeta(bm);
            p.getInventory().setItemInMainHand(result);
            p.sendMessage("§e[SYSTEM] Writ created.");
        } else {
            p.sendMessage("§4[SYSTEM] Invalid object.");
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isChallengeInactive(data.challengeActive) && validationHelper.isSenderPlayer()
                && validationHelper.isValidArgCount(1) && validationHelper.isSenderEmperor();
    }
}
