package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

import java.util.List;

public class Pardon extends PluginCommand {

    public Pardon() {
        super("pardon");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = commandCall.getArg(0).asPlayer();
        List<String> punished = DataStoreService.getInstance().data.punished;
        new ZoneHandler().pardon(p.getDisplayName());
        new WarpHandler().pardon(p.getDisplayName());
        DataStoreService.getInstance().data.fines.remove(p.getDisplayName());
        punished.remove(p.getDisplayName());
        p.sendMessage("§4[SYSTEM] The Emperor has pardoned you.");
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isChallengeInactive(data.challengeActive) && validationHelper.isSenderPlayer()
                && validationHelper.isValidArgCount(1) && validationHelper.isSenderEmperor();
    }
}
