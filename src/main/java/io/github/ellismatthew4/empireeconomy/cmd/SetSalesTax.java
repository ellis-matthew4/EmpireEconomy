package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;

public class SetSalesTax extends PluginCommand {
    private TransactionService ts;

    public SetSalesTax() {
        super("setsalestax");
        this.ts = TransactionService.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        float value = commandCall.getArg(0).asDouble().floatValue();
        boolean success = ts.setSalesTax(value);
        if (success) {
            senderContainer.getPlayer().sendMessage("Sales tax set to " + commandCall.getArg(0).arg);
        } else {
            senderContainer.getPlayer().sendMessage(("Sales tax must be a decimal number between 0 and 1 to represent a percentage."));
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isChallengeInactive(data.challengeActive) && validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}