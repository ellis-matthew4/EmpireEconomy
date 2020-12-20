package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class Fine extends PluginCommand {

    public Fine() {
        super("fine");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        DataStoreService ds = DataStoreService.getInstance();
        int value = ds.data.fines.get(p.getDisplayName()) == null ? 0 : ds.data.fines.get(p.getDisplayName());
        p.sendMessage("§e[SYSTEM] Outstanding fine: $" + value);
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}
