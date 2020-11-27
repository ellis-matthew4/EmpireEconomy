package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.entity.Player;

public class DeleteMoney extends PluginCommand{

    public DeleteMoney() {
        super("deletemoney");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        int amount = commandCall.getArg(0).asInt();
        int balance = data.currency.get(p.getDisplayName());
        data.currency.put(p.getDisplayName(), balance - amount);
        p.sendMessage("$" + amount + " deleted.");
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isChallengeInactive(data.challengeActive) && validationHelper.isSenderPlayer()
                && validationHelper.isValidArgCount(1)  && validationHelper.isValidAmount(commandCall.getArg(0))
                && validationHelper.isSenderEmperor();
    }
}
