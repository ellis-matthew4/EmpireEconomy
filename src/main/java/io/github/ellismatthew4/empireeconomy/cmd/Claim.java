package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.utils.CommandValidationHelper;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import io.github.ellismatthew4.empireeconomy.utils.ZoningCache;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Claim extends PluginCommand {
    private final JavaPlugin plugin;
    private ZoningCache cache;
    private ZoneHandler zoneHandler;
    private TransactionService ts;

    public Claim(JavaPlugin plugin) {
        super("claim");
        this.plugin = plugin;
        this.zoneHandler = new ZoneHandler();
        this.cache = ZoningCache.getInstance();
        this.ts = TransactionService.getInstance();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Location[] ls = cache.get(p);
        Zone z = new Zone(ls[0], ls[1], p.getDisplayName(), commandCall.getArg(0).arg);
        int zoningCost = data.zoningRate * z.area();
            boolean success = (data.currency.get(p.getDisplayName()) > zoningCost) && ts.transact(p, emperorService.getEmperor(), zoningCost);
            if (!success) {
                p.sendMessage("You cannot afford to zone this much land. It costs $" + zoningCost + ".");
                return true;
            }
            success = zoneHandler.addZone(p, z);
            if (!success) {
                p.sendMessage("Claim failed. Is this area or name taken already?");
                return true;
            }
            p.sendMessage("Claim successful!");
            return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isValidArgCount(1);
    }
}
