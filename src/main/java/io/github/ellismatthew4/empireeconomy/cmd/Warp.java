package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import org.bukkit.entity.Player;

public class Warp extends PluginCommand {
    private WarpHandler warpHandler;
    private DataStoreService ds;
    private TransactionService ts;

    public Warp() {
        super("warp");
        this.ds = DataStoreService.getInstance();
        this.ts = TransactionService.getInstance();
        this.warpHandler = new WarpHandler();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        WarpPoint wp = warpHandler.getWarp(
                commandCall.getArg(1).arg,
                commandCall.getArg(0).arg
        );
        if (wp == null) {
            p.sendMessage("§4[SYSTEM] Error 404: Warp not found.");
        }
            boolean success = ts.transact(p, emperorService.getEmperor(), wp.cost, "§e[SYSTEM] Warp successful!");
            if (!success) {
                if ((data.currency.get(p.getDisplayName()) > wp.cost))
                    p.sendMessage("§4[SYSTEM] Warp Failed.");
                else
                    p.sendMessage("§4[SYSTEM] You don't have enough money to do this.");
            } else {
                wp.warp(p);
            }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(2);
    }
}
