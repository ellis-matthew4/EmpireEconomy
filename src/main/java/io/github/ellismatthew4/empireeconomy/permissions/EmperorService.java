package io.github.ellismatthew4.empireeconomy.permissions;

import io.github.ellismatthew4.empireeconomy.data.Data;
import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EmperorService {
    private static EmperorService instance = null;
    private final Data data = DataStoreService.getInstance().data;
    private String empName;

    public EmperorService() {
        this.empName = "";
    }

    public static EmperorService getInstance() {
        if (instance == null) {
            synchronized (EmperorService.class) {
                if (instance == null) {
                    instance = new EmperorService();
                }
            }
        }
        return instance;
    }

    private void _setEmperor(String playerName) {
        Player target = Bukkit.getPlayer(playerName);
        this.empName = target.getDisplayName();
    }

    public String getEmpName() {
        return empName;
    }

    public boolean isEmperor (String playerName) {
        if (data.emperor != null) {
            return playerName.equals(data.emperor);
        }
        return false;
    }

    public void setEmperor (String playerName) {
        data.emperor = playerName;
        this._setEmperor(playerName);
    }

    public Player getEmperor() {
        return (data.emperor == null) ? null : Bukkit.getPlayer(data.emperor);
    }

    public boolean isChallengeActive() {
        return data.challengeActive;
    }
}
