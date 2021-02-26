package net.kunmc.lab.dimensionswitchplugin;

import net.kunmc.lab.dimensionswitchplugin.commands.DimensionSwitchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DimensionSwitchPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        DimensionSwitchCommand command = new DimensionSwitchCommand();

        getServer().getPluginCommand("DimensionSwitch").setExecutor(command);
        getServer().getPluginCommand("DimensionSwitch").setTabCompleter(command);
    }

    @Override
    public void onDisable() {
    }

}
