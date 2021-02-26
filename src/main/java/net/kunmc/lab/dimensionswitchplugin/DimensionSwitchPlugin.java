package net.kunmc.lab.dimensionswitchplugin;

import net.kunmc.lab.dimensionswitchplugin.commands.DimensionSwitchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DimensionSwitchPlugin extends JavaPlugin {
    public static DimensionSwitchPlugin instance;
    public static Timer timer;

    @Override
    public void onEnable() {
        instance = this;

        DimensionSwitchCommand command = new DimensionSwitchCommand();

        getServer().getPluginCommand("dimension").setExecutor(command);
        getServer().getPluginCommand("dimension").setTabCompleter(command);
    }

    @Override
    public void onDisable() {
    }

}
