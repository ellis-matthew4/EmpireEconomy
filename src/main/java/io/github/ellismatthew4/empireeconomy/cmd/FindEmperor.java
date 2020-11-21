package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FindEmperor extends PluginCommand {

    public FindEmperor() {
        super("findemperor");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Player e = emperorService.getEmperor();
        if (e == null) {
            p.sendMessage("Emperor is offline.");
            return true;
        }
        Location l = e.getLocation();
        p.sendMessage("Emperor " + e.getDisplayName() + "'s location is: "
                + (int) Math.floor(l.getX()) + ", "
                + (int) Math.floor(l.getY()) + ", "
                + (int) Math.floor(l.getZ())
        );
        return true;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isValidArgCount(0);
    }
}
