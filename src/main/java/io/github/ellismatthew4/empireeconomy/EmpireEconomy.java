package io.github.ellismatthew4.empireeconomy;

import io.github.ellismatthew4.empireeconomy.cmd.*;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import io.github.ellismatthew4.empireeconomy.events.playerClickListener;
import io.github.ellismatthew4.empireeconomy.events.zoneEntryListener;
import io.github.ellismatthew4.empireeconomy.utils.*;
import io.github.ellismatthew4.empireeconomy.events.deathListener;
import io.github.ellismatthew4.empireeconomy.events.joinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmpireEconomy extends JavaPlugin {
    private final LoggerService logger = LoggerService.getInstance(getLogger());
    private final DataStoreService dataStoreService = DataStoreService.getInstance();
    private final EmperorService emperorService = EmperorService.getInstance(this);
    private zoneEntryListener zel;
    public static EmpireEconomy plugin;

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("Activating gamer mode...");
        zel = new zoneEntryListener(this);
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
                .load(this);
        new EventLoader()
                .withEvent(new deathListener(this))
                .withEvent(new joinListener(this))
                .withEvent(new playerClickListener(this))
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