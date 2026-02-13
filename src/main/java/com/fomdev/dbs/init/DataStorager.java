package com.fomdev.dbs.init;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DataStorager extends JavaPlugin {
    public static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}