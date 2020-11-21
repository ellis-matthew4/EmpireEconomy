package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.cmd.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandLoader {
    private Map<String, PluginCommand> commandMap;

    public CommandLoader() {
        commandMap = new HashMap<>();
    }

    public CommandLoader withCommand(PluginCommand pluginCommand) {
        this.commandMap.put(pluginCommand.command, pluginCommand);
        return this;
    }

    public CommandLoader removeCommand(String command) {
        this.commandMap.remove(command);
        return this;
    }

    public void load(JavaPlugin javaPlugin) {
        for (String key : commandMap.keySet()) {
            javaPlugin.getCommand(key).setExecutor(commandMap.get(key));
        }
    }
}
