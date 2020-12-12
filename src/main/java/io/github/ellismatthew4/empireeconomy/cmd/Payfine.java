package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import io.github.ellismatthew4.empireeconomy.utils.*;
import org.bukkit.entity.Player;

public class Payfine extends PluginCommand {
    private TransactionService ts;

    public Payfine() {
        super("payfine");
        this.ts = TransactionService.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        DataStoreService ds = DataStoreService.getInstance();
        Player target = commandCall.getArg(0).asPlayer();
        Player emp = EmperorService.getInstance().getEmperor();
        int amountToPay = commandCall.getArg(1).asInt();
        Integer fine = ds.data.fines.get(target.getDisplayName());
        if (fine == null) {
            p.sendMessage("§4[SYSTEM] That player does not have a fine to pay.");
            return true;
        } else {
            if (fine < amountToPay) {
                amountToPay = fine;
            }
        }

        int finalAmountToPay = amountToPay;
        boolean success = ts.transactTaxFree(p, emp, amountToPay, () -> {
                    int value = ds.data.fines.get(target.getDisplayName()) - finalAmountToPay;
                    ds.data.fines.put(target.getDisplayName(),
                            value > 0 ? value : null );
                    if (value <= 0) {
                        ds.data.punished.remove(target.getDisplayName());
                        new ZoneHandler().pardon(target.getDisplayName());
                        new WarpHandler().pardon(target.getDisplayName());
                        target.sendMessage("§e[SYSTEM] Your fine has been paid, and your punishment has been lifted.");
                    }
                    p.sendMessage("§e[SYSTEM] You paid $" + finalAmountToPay + " towards " + target.getDisplayName() + "'s fine.");
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
