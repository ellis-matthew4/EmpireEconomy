package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.cmd.CommandArgument;
import io.github.ellismatthew4.empireeconomy.cmd.CommandCall;
import io.github.ellismatthew4.empireeconomy.cmd.PluginCommand;
import io.github.ellismatthew4.empireeconomy.cmd.SenderContainer;
import io.github.ellismatthew4.empireeconomy.data.Data;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandValidationHelper {
    private final LoggerService logger = LoggerService.getInstance();
    private final PluginCommand pluginCommand;
    private final SenderContainer sender;
    private final CommandCall commandCall;

    public CommandValidationHelper(PluginCommand pluginCommand, SenderContainer sender, CommandCall commandCall) {
        this.pluginCommand = pluginCommand;
        this.sender = sender;
        this.commandCall = commandCall;
    }

    public boolean isValidPlayer(CommandArgument commandArgument) {
        if (commandArgument.asPlayer() == null) {
            warnAndSend("Invalid player");
            return false;
        }
        return true;
    }

    public boolean isValidAmount(CommandArgument commandArgument) {
        Integer amount = commandArgument.asInt();
        if (amount == null) {
            warnAndSend("No amount provided");
            return false;
        }
        if (amount < 0) {
            warnAndSend("No negative values allowed");
            return false;
        }
        return true;
    }

    public boolean isSenderPlayer() {
        if (!sender.isPlayer()) {
            warn("Sender is not a Player");
            return false;
        }
        return true;
    }

    public boolean isSenderEmperor() {
        if (EmperorService.getInstance().isEmperor(sender.getPlayer().getDisplayName())) {
            return true;
        }
        warnAndSend("You must be Emperor to perform this command.");
        return false;
    }

    public boolean isSenderPunished() {
        List<String> p = DataStoreService.getInstance().data.punished;
        if (Arrays.stream(p.toArray()).anyMatch(sender.getPlayer().getDisplayName()::equals)) {
            warnAndSend("You cannot do this while Punished. See the Emperor to be Pardoned.");
            return false;
        }
        return true;
    }

    public boolean isValidArgCount(int count) {
        if (commandCall.args.size() != count) {
            warnAndSend("Incorrect argument count");
            return false;
        }
        return true;
    }

    public boolean isChallengeInactive(boolean challengActive) {
        if (challengActive) {
            warnAndSend("You must kill your challenger before using this command.");
            return false;
        }
        return true;
    }

    public void warnAndSend(String message) {
        Player p = sender.getPlayer();
        p.sendMessage(message);
        _warn(p.getDisplayName() + " - " + message);
    }

    public void warn(String message) {
        _warn(sender.commandSender.getName() + " - " + message);
    }

    private void _warn(String message) {
        logger.warning(pluginCommand.getClass().getSimpleName() + ": " + message);
    }
}
