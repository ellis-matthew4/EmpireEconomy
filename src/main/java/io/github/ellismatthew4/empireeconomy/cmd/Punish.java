package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

import java.util.List;

public class Punish extends PluginCommand {

    public Punish() {
        super("punish");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = commandCall.getArg(0).asPlayer();
        DataStoreService ds = DataStoreService.getInstance();
        if (commandCall.args.size() == 2) {
            String arg = commandCall.getArg(1).arg;
            int fine = (arg == null) ? null : Integer.parseInt(arg);
            ds.data.fines.put(
                    p.getDisplayName(),
                    ds.data.fines.get(p.getDisplayName()) == null ?
                            fine : ds.data.fines.get(p.getDisplayName()) + fine
            );
        }
        List<String> punished = ds.data.punished;
        WarpHandler wh = new WarpHandler();
        ZoneHandler zh = new ZoneHandler();
        wh.punish(p.getDisplayName());
        zh.punish(p.getDisplayName());
        punished.add(p.getDisplayName());
        p.sendMessage("§4[SYSTEM] The Emperor has ordered you punished. Please contact the Emperor to be pardoned.");
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isChallengeInactive(data.challengeActive) && validationHelper.isSenderPlayer()
                && validationHelper.isValidArgCount(1, 2) && validationHelper.isSenderEmperor();
    }
}
