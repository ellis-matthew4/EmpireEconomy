package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import org.bukkit.entity.Player;

public class Pay extends PluginCommand {
    private TransactionService ts;

    public Pay() {
        super("pay");
        this.ts = TransactionService.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Player target = commandCall.getArg(0).asPlayer();
        int amountToPay = commandCall.getArg(1).asInt();

        boolean success = ts.transact(p, target, amountToPay, () -> {
                    p.sendMessage("§e[SYSTEM] You paid $" + amountToPay + " to " + target.getDisplayName());
                    target.sendMessage("§e[SYSTEM] You have been paid $" + amountToPay + " by " + p.getDisplayName());
                    return true;
                });
        if (!success) {
            p.sendMessage("§4[SYSTEM] You do not have enough money to do this.");
        }
        return true;

    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(2)
                && validationHelper.isValidPlayer(commandCall.getArg(0)) && validationHelper.isValidAmount(commandCall.getArg(1));
    }
}
