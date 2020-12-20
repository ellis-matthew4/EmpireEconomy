package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;

public class DeleteZone extends PluginCommand {
    ZoneHandler zoneHandler;
    public DeleteZone() {
        super("deletezone");
        this.zoneHandler = ZoneHandler.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        String zone = commandCall.getArg(0).arg;
        String playername = senderContainer.getPlayer().getDisplayName();
        if (zoneHandler.zoneExists(zone)) {
            if (zoneHandler.deleteZone(zone, playername)) {
                senderContainer.getPlayer().sendMessage("Zone deleted.");
            }
            else {
                senderContainer.getPlayer().sendMessage("You don't own that zone.");
                return false;
            }
        } else {
            senderContainer.getPlayer().sendMessage("Zone not found.");
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(1);
    }
}
