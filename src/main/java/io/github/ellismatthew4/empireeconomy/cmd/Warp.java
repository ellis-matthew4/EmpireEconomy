package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import org.bukkit.Bukkit;
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
        Player owner = Bukkit.getPlayer(commandCall.getArg(0).arg);
        if (wp == null || !wp.canWarp(p)) {
            p.sendMessage("§4[SYSTEM] Error 404: Warp not found.");
        } else {
            if (!wp.repo) {
            boolean success = ts.transact(p, owner, wp.cost, () -> {
                wp.warp(p);
                p.sendMessage("§e[SYSTEM] Warp successful!");
                return true;
            });
            if (!success) {
                if ((data.currency.get(p.getDisplayName()) > wp.cost))
                    p.sendMessage("§4[SYSTEM] Warp Failed.");
                else
                    p.sendMessage("§4[SYSTEM] You don't have enough money to do this.");
            }
        } else {
                p.sendMessage("§4[SYSTEM] The owner of this Warp Point is currently punished. Please try again later.");
            }
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(2)
                && validationHelper.isValidPlayer(commandCall.getArg(0));
    }
}
