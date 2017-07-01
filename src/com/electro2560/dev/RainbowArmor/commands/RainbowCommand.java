package com.electro2560.dev.RainbowArmor.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.electro2560.dev.RainbowArmor.RainbowArmor;
import com.electro2560.dev.RainbowArmor.utils.Perms;
import com.electro2560.dev.RainbowArmor.utils.Utils;

public class RainbowCommand implements CommandExecutor{

	static RainbowArmor ra = RainbowArmor.get();
	final static String noPerm = ChatColor.RED + "No permission.";
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if(!sender.hasPermission(Perms.canUseCommand) && !sender.hasPermission(Perms.canUseAll)){
			sender.sendMessage(noPerm);
			return false;
		}
		
		if(args.length == 0){
			sender.sendMessage("§c-§6-§e- §a§lR§b§la§d§li§c§ln§6§lb§e§lo§a§lw §b§lA§d§lr§c§lm§6§lo§e§lr §a-§b-§d- §cb§6y §e§lE§a§ll§b§le§d§lc§c§lt§6§lr§e§lo§a§lB§b§lo§d§ly§c§l2§6§l5§e§l6§a§l0");
			sender.sendMessage(ChatColor.AQUA + "equip, get, give, reset, set");
			return false;
		}
		
		Player player = (sender instanceof Player) ? (Player) sender : null;
		
		switch (args[0].toLowerCase()) {
		case "get":
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Only Players can get armor!");
				return false;
			}
			
			if(sender.hasPermission(Perms.canUseGet) || sender.hasPermission(Perms.canUseAll)){
				if(args.length != 1){
					if(player.getServer().getPlayerExact(args[1]) != null){
						player.sendMessage(ChatColor.RED + "Error: You must use /RainbowArmor give to give a player rainbow armor!");
					}else player.sendMessage(ChatColor.RED + "Error: This command doesn't use any arguments!");
				}else{
					player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_HELMET));
					player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_CHESTPLATE));
					player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_LEGGINGS));
					player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_BOOTS));
								
					player.sendMessage(ChatColor.GREEN + "You received rainbow armor!");	
				}
				
			}else player.sendMessage(noPerm);
			break;
		case "give":
			if(sender.hasPermission(Perms.canUseGive) || sender.hasPermission(Perms.canUseAll)){
				if(args.length != 2){
					sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor give [Player]");
					sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor give Steve");
					break;
				}
				
				Player targetPlayer = Bukkit.getServer().getPlayerExact(args[1]);
				
				if(targetPlayer != null){
					targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_HELMET));
					targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_CHESTPLATE));
					targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_LEGGINGS));
					targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_BOOTS));
							
					targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
					sender.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
				}else sender.sendMessage(ChatColor.RED + "Error: Player not found! Be sure to use their full name!");
			}else sender.sendMessage(noPerm);
			break;
		case "set":
			if(sender.hasPermission(Perms.canUseSet) || sender.hasPermission(Perms.canUseAll)){
			
				if(args.length == 1){
					sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set [Speed, Color] [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
					sender.sendMessage(ChatColor.AQUA + "Examples:\n" + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
					sender.sendMessage(ChatColor.GREEN + "/RainbowArmor set Speed 10");
					break;
				}else{
					if(args.length != 5 && !args[1].equalsIgnoreCase("Speed")){
						sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Color [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
						sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
						sender.sendMessage(ChatColor.AQUA + "Current Value of " + args[3] + " for " + args[2] +": " + ChatColor.GREEN + ra.getConfig().getInt("Colors." + args[2].toUpperCase() + "." + args[3].toLowerCase()));
					}else{
						if(args[1].equalsIgnoreCase("Color")){
							if (args[2].equalsIgnoreCase("Red") || args[2].equalsIgnoreCase("Orange")
									|| args[2].equalsIgnoreCase("Yellow") || args[2].equalsIgnoreCase("Lime")
									|| args[2].equalsIgnoreCase("LightBlue") || args[2].equalsIgnoreCase("Magenta")
									|| args[2].equalsIgnoreCase("Pink") || args[2].equalsIgnoreCase("White"))
								Utils.setterHandler(sender, args[2], args[3], args[4]);
							else if(!args[1].equalsIgnoreCase("Speed")) sender.sendMessage(ChatColor.RED + "Error: Unknow Color!");
						}else if(!args[1].equalsIgnoreCase("Speed")) sender.sendMessage(ChatColor.RED + "Error: Not a Valid Argument!");
					}
				}
							
				if(args[1].equalsIgnoreCase("Speed")){
					if(args.length == 2){
						sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Speed [Value: 1-256]");
						sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Speed 10");
						sender.sendMessage(ChatColor.AQUA + "Current Speed: " + ChatColor.GREEN + ra.getConfig().getInt("SPEED"));
							
					}else{
						if(args.length != 3){
							sender.sendMessage(ChatColor.RED + "Error: Missing Speed Value");
						}else if(Utils.isNumeric(args[2])){
							int INTspeed = Integer.parseInt(args[2]);
							if(Utils.isBetween(0, 257, INTspeed)){
								ra.getConfig().set("SPEED", INTspeed);
								ra.saveConfig();
												
								Utils.createTimerTask();
											
								sender.sendMessage(ChatColor.GREEN + "Speed set to " + INTspeed);
							}else sender.sendMessage(ChatColor.RED + "Error: Speed must be 1 to 256!");
						}else sender.sendMessage(ChatColor.RED + "Error: Invalid Value!");
					}
							
				}
			}else sender.sendMessage(noPerm);
			break;
		case "reset":
			if(sender.hasPermission(Perms.canUseReset) || sender.hasPermission(Perms.canUseAll)){
				Utils.resetConfig();
				sender.sendMessage(ChatColor.GREEN + "All changed values have been reset!");
			}else sender.sendMessage(noPerm);
			break;
		case "equip":
			if(sender.hasPermission(Perms.canUseEquip) || sender.hasPermission(Perms.canUseAll)){
				if(args.length != 3){
					sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
					sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
					sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
				}else{
					if(sender.getServer().getPlayerExact(args[1]) != null){
						if(args[2].equalsIgnoreCase("Replace")){
							Player targetPlayer = Bukkit.getServer().getPlayerExact(args[1]);
							
							targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET));
							targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE));
							targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS));
							targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS));
								
							targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
							sender.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
						}else if(args[2].equalsIgnoreCase("Move")){
							Player targetPlayer = Bukkit.getServer().getPlayerExact(args[1]);
							
							try{
								targetPlayer.getInventory().addItem(targetPlayer.getInventory().getHelmet());
							}catch(Exception e){}
							
							try{
								targetPlayer.getInventory().addItem(targetPlayer.getInventory().getChestplate());
							}catch(Exception e){}
							
							try{
								targetPlayer.getInventory().addItem(targetPlayer.getInventory().getLeggings());
							}catch(Exception e){}
							
							try{
								targetPlayer.getInventory().addItem(targetPlayer.getInventory().getBoots());
							}catch(Exception e){}
							
							targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET));
							targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE));
							targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS));
							targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS));
								
							targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
							sender.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
						}else{
							sender.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
							sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
							sender.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
						}
						
					}else sender.sendMessage(ChatColor.RED + "Error: Player not found! Be sure to use their full name!");
				}
			}else sender.sendMessage(noPerm);
			break;
		default:
			sender.sendMessage(ChatColor.RED + "Error: Unknown Option! Try /rainbowarmor help");
			break;
		}
		
		return false;	
	}
	
}
