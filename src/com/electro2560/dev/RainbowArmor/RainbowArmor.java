package com.electro2560.dev.RainbowArmor;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.electro2560.dev.RainbowArmor.commands.RainbowCommand;
import com.electro2560.dev.RainbowArmor.updater.UpdateListener;
import com.electro2560.dev.RainbowArmor.utils.Utils;

public class RainbowArmor extends JavaPlugin {
	
	private static RainbowArmor instance;
	
	PluginManager pm = Bukkit.getPluginManager();
	
	@Override
	public void onEnable() {
		instance = this;
		
		if(!new File(getDataFolder() + File.separator + "config.yml").exists()) saveDefaultConfig();
		
		pm.registerEvents(new UpdateListener(instance), instance);
		getCommand("RainbowArmor").setExecutor(new RainbowCommand());
		
		Utils.createTimerTask();
		
		if(getConfig().getBoolean("useMetrics", true)) Utils.startMetrics();
	}
	
	@Override
	public void onDisable(){
		saveConfig();
		
		instance = null;
	}
	
	public static RainbowArmor get(){
		return instance;
	}
	
}