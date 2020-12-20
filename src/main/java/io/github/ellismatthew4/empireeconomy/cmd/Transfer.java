package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Property;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;

public class Transfer extends PluginCommand {
    public Transfer() {
        super("transfer");
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        ZoneHandler zh = new ZoneHandler();
        WarpHandler wh = new WarpHandler();
        Player p = senderContainer.getPlayer();
        Player target = commandCall.getArg(0).asPlayer();
        String key = commandCall.getArg(1).arg;
        Property property = notNull(zh.getZone(key), wh.getWarp(key, p.getDisplayName()));
        if (property != null) {
            if ((property.owner.equals(p.getDisplayName()) && !property.repo)
                    || EmperorService.getInstance().isEmperor(p.getDisplayName())) {
                property.transfer(target.getDisplayName());
                p.sendMessage("§e[SYSTEM] Transfer successful.");
            } else {
                p.sendMessage("§4[SYSTEM] You can't transfer a property while it's repossessed.");
            }
        } else {
            p.sendMessage("§4[SYSTEM] You don't own a property with that name.");
        }
        return true;

    }

    public Property notNull(Property x, Property y) {
        return x == null ? y : x;
    }

    @Override
    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(2)
                && validationHelper.isValidPlayer(commandCall.getArg(0));
    }
}
