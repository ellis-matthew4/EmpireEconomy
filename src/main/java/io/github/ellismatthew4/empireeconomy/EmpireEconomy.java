package io.github.ellismatthew4.empireeconomy;

import io.github.ellismatthew4.empireeconomy.cmd.*;
import io.github.ellismatthew4.empireeconomy.events.*;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import io.github.ellismatthew4.empireeconomy.utils.CommandLoader;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.EventLoader;
import io.github.ellismatthew4.empireeconomy.utils.LoggerService;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmpireEconomy extends JavaPlugin {
    private final LoggerService logger = LoggerService.getInstance(getLogger());
    private final DataStoreService dataStoreService = DataStoreService.getInstance();
    private final EmperorService emperorService = EmperorService.getInstance();
    private zoneEntryListener zel;
    private backupEvent be;
    private dailyEvent de;
    public static EmpireEconomy plugin;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Activating gamer mode...");
        zel = new zoneEntryListener(this);
        be = new backupEvent(this);
        de = new dailyEvent(this);
        new CommandLoader()
                .withCommand(new CreateMoney())
                .withCommand(new Balance())
                .withCommand(new Pay())
                .withCommand(new Emperor())
                .withCommand(new GodMode())
                .withCommand(new Challenge(this))
                .withCommand(new FindEmperor())
                .withCommand(new Wand())
                .withCommand(new Claim(this))
                .withCommand(new SetMessage())
                .withCommand(new SetZoningRate())
                .withCommand(new DeleteMoney())
                .withCommand(new DeleteZone())
                .withCommand(new SetSalesTax())
                .withCommand(new CreateWarp())
                .withCommand(new SetDefaultWarpFee())
                .withCommand(new SetWarpCost())
                .withCommand(new SetWarpCreationFee())
                .withCommand(new Warp())
                .withCommand(new DeleteWarp())
                .withCommand(new List())
                .withCommand(new Shop())
                .withCommand(new OpenShop())
                .withCommand(new CloseShop())
                .withCommand(new Transfer())
                .withCommand(new Writ())
                .withCommand(new Punish())
                .withCommand(new Pardon())
                .withCommand(new Vanish(this))
                .withCommand(new Fly(this))
                .withCommand(new Properties())
                .withCommand(new Batchlist())
                .withCommand(new Stats())
                .withCommand(new Payfine())
                .withCommand(new Fine())
                .load(this);
        new EventLoader()
                .withEvent(new deathListener(this))
                .withEvent(new joinListener(this))
                .withEvent(new playerClickListener(this))
                .withEvent(new shopClickListener())
                .withEvent(new shopCloseListener())
                .load(this);
    }

    @Override
    public void onDisable() {
        logger.info("Deactivating gamer mode...");
        dataStoreService.data.challenger = null;
        dataStoreService.data.challengeActive = false;
        dataStoreService.writeData();
    }
}