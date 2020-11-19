package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CreateWarp extends PluginCommand {
    private WarpHandler warpHandler;
    private TransactionService ts;
    private DataStoreService ds;

    public CreateWarp() {
        super("createwarp");
        this.warpHandler = new WarpHandler();
        this.ts = TransactionService.getInstance();
        this.ds = DataStoreService.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        WarpPoint w = new WarpPoint(
                commandCall.getArg(0).arg,
                p.getDisplayName(),
                p.getLocation(),
                commandCall.getArg(1).arg.equalsIgnoreCase("private")
        );
        int creationFee = ds.data.warpCreationFee;
            boolean success = ts.transact(p, emperorService.getEmperor(), creationFee, "§e[SYSTEM] Claim successful!")
                    && warpHandler.addWarp(w);
            if (!success) {
                if ((data.currency.get(p.getDisplayName()) > creationFee))
                    p.sendMessage("§4[SYSTEM] Warp Creation Failed. Do you already have a warp with this name?");
                else
                    p.sendMessage("§4[SYSTEM] You don't have enough money to do this.");

            }
            return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(2);
    }
}
