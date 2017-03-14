package com.electro2560.dev.RainbowArmor.updater;

import java.beans.ConstructorProperties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.electro2560.dev.RainbowArmor.RainbowArmor;
import com.electro2560.dev.RainbowArmor.utils.Perms;
import com.electro2560.dev.RainbowArmor.utils.Utils;

public class UpdateListener implements Listener {
	private final RainbowArmor plugin;

	@ConstructorProperties({ "plugin" })
	public UpdateListener(RainbowArmor plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(Perms.canCheckForUpdates) && Utils.isCheckForUpdates()) {
			UpdateUtil.sendUpdateMessage(e.getPlayer(), plugin);
		}
	}
}
