package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import org.bukkit.entity.Player;

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
        boolean privacy = commandCall.getArg(1).arg.equalsIgnoreCase("private");
        WarpPoint w = new WarpPoint(
                commandCall.getArg(0).arg,
                p.getDisplayName(),
                p.getLocation(),
                privacy
        );
        int creationFee = ds.data.warpCreationFee * (privacy ? 2 : 1);
            boolean success = ts.transact(p, emperorService.getEmperor(), creationFee, () -> {
                    boolean res = warpHandler.addWarp(w);
                    if (res)
                        p.sendMessage("§e[SYSTEM] Warp Creation Successful!");
                    else
                        p.sendMessage("§4[SYSTEM] Warp Creation Failed. Do you already have a warp with this name?");
                    return true;
                });
            if (!success) {
                p.sendMessage("§4[SYSTEM] You don't have enough money to do this.");
            }
            return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(2);
    }
}
