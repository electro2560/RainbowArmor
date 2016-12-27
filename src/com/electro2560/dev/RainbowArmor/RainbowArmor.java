package com.electro2560.dev.RainbowArmor;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.electro2560.dev.RainbowArmor.commands.RainbowCommand;
import com.electro2560.dev.RainbowArmor.updater.UpdateListener;
import com.electro2560.dev.RainbowArmor.utils.MetricsLite;
import com.electro2560.dev.RainbowArmor.utils.Utils;

public class RainbowArmor extends JavaPlugin {
	
	private static RainbowArmor instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		saveDefaultConfig();
		
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {}
		
		Bukkit.getPluginManager().registerEvents(new UpdateListener(), this);
		getCommand("RainbowArmor").setExecutor(new RainbowCommand());
		
		Utils.createTimerTask();
		
	}
	
	@Override
	public void onDisable(){
		saveConfig();
	}
	
	public static RainbowArmor get(){
		return instance;
	}
	
	public String getVersion(){
		return getDescription().getVersion();
	}
	
}