package com.electro2560.dev.RainbowArmor.utils;

import org.bukkit.permissions.Permission;

public class Perms {
	
	public static Permission canUseCommand = new Permission("rainbowarmor.command.use");
	public static Permission canUse = new Permission("rainbowarmor.use");
	public static Permission canUseGet = new Permission("rainbowarmor.command.get");
	public static Permission canUseGive = new Permission("rainbowarmor.command.give");
	public static Permission canUseSet = new Permission("rainbowarmor.command.set");
	public static Permission canUseReset = new Permission("rainbowarmor.command.reset");
	public static Permission canUseEquip = new Permission("rainbowarmor.command.equip");
	
	public static Permission canUseAll = new Permission("rainbowarmor.*");
	
	public static Permission canCheckForUpdates = new Permission("rainbowarmor.updates");
	
}
