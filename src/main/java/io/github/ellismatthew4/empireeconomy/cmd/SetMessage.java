package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class SetMessage extends PluginCommand{
    private ZoneHandler zoneHandler;

    public SetMessage() {
        super("setmessage");
        this.zoneHandler = ZoneHandler.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Zone z = zoneHandler.getZone(commandCall.getArg(0).arg);
        if (z != null && z.owner.equals(p.getDisplayName())) {
            z.msg = commandCall.getArg(1).arg;
            p.sendMessage("§e[SYSTEM] Changed message");
        } else if (z == null) {
            p.sendMessage("§4[SYSTEM] Zone not found");
        } else {
            p.sendMessage("§4[SYSTEM] You do not own that zone");
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(2);
    }
}
