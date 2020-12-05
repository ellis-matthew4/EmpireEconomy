package io.github.ellismatthew4.empireeconomy.cmd;

import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Stats extends PluginCommand {
    private ZoneHandler zh;

    public Stats() {
        super("stats");
        this.zh = new ZoneHandler();
    }

    @Override
    public boolean onCommand(SenderContainer senderContainer, CommandCall commandCall) {
        Player p = senderContainer.getPlayer();
        Shop s = zh.getShop(p.getLocation(), commandCall.getArg(0).arg);
        if (s == null) {
            p.sendMessage("§4[SYSTEM] Shop not found. Did you spell it correctly?");
        } else {
            Integer[] stats = DataStoreService.getInstance().data.stats.get(s.name);
            int transactions = 0;
            int revenue = 0;
            int stockValue = 0;
            if (stats != null) {
                transactions = stats[1];
                revenue = stats[0];
                stockValue = (int) (revenue * 0.01);
            }
            p.sendMessage(new String[] {
                    "§e[SYSTEM] --------------------------------------------",
                    "§e[SYSTEM] Stats for " + s.name + ":",
                    "§e[SYSTEM] Daily transactions: " + transactions,
                    "§e[SYSTEM] Daily revenue: $" + revenue,
                    "§e[SYSTEM] Daily stock value: $" + stockValue,
                    "§e[SYSTEM] --------------------------------------------",
            });
        }
        return true;
    }

    public boolean validate(SenderContainer senderContainer, CommandCall commandCall) {
        CommandValidationHelper validationHelper = new CommandValidationHelper(this, senderContainer, commandCall);
        return validationHelper.isSenderPlayer() && validationHelper.isSenderPunished() && validationHelper.isValidArgCount(1);
    }
}
