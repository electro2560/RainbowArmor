package com.electro2560.dev.RainbowArmor.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.electro2560.dev.RainbowArmor.RainbowArmor;
import com.electro2560.dev.RainbowArmor.utils.Perms;
import com.electro2560.dev.RainbowArmor.utils.Utils;

public class RainbowCommand implements CommandExecutor{

	final RainbowArmor ra = RainbowArmor.get();
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		
		//TODO: This is code over two years old. It is currently being rewritten to my new standards.
		
		/*if(args.length == 0){
			//TODO
			
			
			return false;
		}*/
		
		if(sender instanceof Player){
			Player player = (Player) sender;
				if(sender.hasPermission(Perms.canUseCommand) || sender.hasPermission(Perms.canUseAll)){
						if(args.length == 0){
							player.sendMessage("§c-§6-§e- §a§lR§b§la§d§li§c§ln§6§lb§e§lo§a§lw §b§lA§d§lr§c§lm§6§lo§e§lr §a-§b-§d- §cb§6y §e§lE§a§ll§b§le§d§lc§c§lt§6§lr§e§lo§a§lB§b§lo§d§ly§c§l2§6§l5§e§l6§a§l0");
							player.sendMessage(ChatColor.AQUA + "equip, get, give, reset, set");
							return false;
						}
							
						if(args[0].equalsIgnoreCase("equip") || args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("reset")){
							
						}else player.sendMessage(ChatColor.RED + "Error: Unknown Option!");
						
					if(args[0].equalsIgnoreCase("get")){
					if(player.hasPermission(Perms.canUseGet) || player.hasPermission(Perms.canUseAll)){
						if(args.length != 1){
							if(player.getServer().getPlayerExact(args[1]) != null){
								player.sendMessage(ChatColor.RED + "Error: You must use /RainbowArmor give to give a player rainbow armor!");
							}else player.sendMessage(ChatColor.RED + "Error: This command doesn't use any arguments!");
						}else{
							player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_HELMET, null, player, false));
							player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, player, false));
							player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, player, false));
							player.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_BOOTS, null, player, false));
									
							player.sendMessage(ChatColor.GREEN + "You received rainbow armor!");	
						}
						
					}else player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.give");
				}
					
					if(args[0].equalsIgnoreCase("give")){
						if(player.hasPermission(Perms.canUseGive) || player.hasPermission(Perms.canUseAll)){
							if(args.length != 2){
								player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor give [Player]");
								player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor give Steve");
							}else if(player.getServer().getPlayerExact(args[1]) != null){
								
								Player targetPlayer = player.getServer().getPlayerExact(args[1]);
									
								targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
								targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
								targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
								targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
									
								targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
								player.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
							}else player.sendMessage(ChatColor.RED + "Error: Player Not Found! Be sure to use the full name!");
						}else player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.give!");
						
					}
					
					if(args[0].equalsIgnoreCase("Set")){
						if(player.hasPermission(Perms.canUseSet) || player.hasPermission(Perms.canUseAll)){
							if(args.length == 1){
								player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set [Speed, Color] [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
								player.sendMessage(ChatColor.AQUA + "Examples:\n" + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
								player.sendMessage(ChatColor.GREEN + "/RainbowArmor set Speed 10");
							}else{
								
								if(args.length != 5 && !args[1].equalsIgnoreCase("Speed")){
									player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Color [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
									player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
									player.sendMessage(ChatColor.AQUA + "Current Value of " + args[3] + " for " + args[2] +": " + ChatColor.GREEN + ra.getConfig().getInt("Colors." + args[2].toUpperCase() + "." + args[3].toLowerCase()));
								}else{
									if(args[1].equalsIgnoreCase("Color")){
										if(args[2].equalsIgnoreCase("Red") || args[2].equalsIgnoreCase("Orange") || args[2].equalsIgnoreCase("Yellow") || args[2].equalsIgnoreCase("Lime") || args[2].equalsIgnoreCase("LightBlue") || args[2].equalsIgnoreCase("Magenta") || args[2].equalsIgnoreCase("Pink") || args[2].equalsIgnoreCase("White")) Utils.setterHandler(player, args[2], args[3], args[4]);
										else if(!args[1].equalsIgnoreCase("Speed")) player.sendMessage(ChatColor.RED + "Error: Unknow Color!");
									}else if(!args[1].equalsIgnoreCase("Speed")) player.sendMessage(ChatColor.RED + "Error: Not a Valid Argument!");
									
								}
							}
							
						}else player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.set!");
							
					}
					
					if(args[0].equalsIgnoreCase("Set") && args[1].equalsIgnoreCase("Speed")){
						if(player.hasPermission(Perms.canUseSet) || player.hasPermission(Perms.canUseAll)){
							if(args.length == 2){
								player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Speed [Value: 1-256]");
								player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Speed 10");
								player.sendMessage(ChatColor.AQUA + "Current Speed: " + ChatColor.GREEN + ra.getConfig().getInt("SPEED"));
								
							}else{
								if(args.length != 3){
									player.sendMessage(ChatColor.RED + "Error: Missing Speed Value");
								}else{
									if(Utils.isNumeric(args[2])){
										int INTspeed = Integer.parseInt(args[2]);
										if(Utils.isBetween(0, 257, INTspeed)){
											ra.getConfig().set("SPEED", INTspeed);
											ra.saveConfig();
											
											Utils.createTimerTask();

											player.sendMessage(ChatColor.GREEN + "Speed set to " + INTspeed);
										}else player.sendMessage(ChatColor.RED + "Error: Speed must be 1 to 256!");
									}else player.sendMessage(ChatColor.RED + "Error: Invalid Value!");
								}
							
							}
						}else player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.set!");
						
					}
					
					if(args[0].equalsIgnoreCase("Reset")){
					
						if(player.hasPermission(Perms.canUseReset) || player.hasPermission(Perms.canUseAll)){
							Utils.resetConfig();
							player.sendMessage(ChatColor.GREEN + "All changed values have been reset!");
						}else player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.reset!");
					}
					
					//TODO: Equip
					if(args[0].equalsIgnoreCase("equip")){
						if(player.hasPermission(Perms.canUseEquip) || player.hasPermission(Perms.canUseAll)){
							if(args.length != 3){
								player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
								player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
								player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
							}else{
							if(player.getServer().getPlayerExact(args[1]) != null){
								if(args[2].equalsIgnoreCase("Replace")){
									Player targetPlayer = player.getServer().getPlayerExact(args[1]);
									
									targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
									targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
									targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
									targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
										
									targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
									player.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
								}else if(args[2].equalsIgnoreCase("Move")){
									Player targetPlayer = player.getServer().getPlayerExact(args[1]);
									
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
									
									targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
									targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
									targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
									targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
										
									targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
									player.sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
								}else{
									player.sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
									player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
									player.sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
								}
								
							}else{
								player.sendMessage(ChatColor.RED + "Error: Player Not Found! Be sure to use the full name!");
							}
						}
						}else{
							player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.equip!");
						}
						
					}
					
					
				}else{
					player.sendMessage("§c-§6-§e- §a§lR§b§la§d§li§c§ln§6§lb§e§lo§a§lw §b§lA§d§lr§c§lm§6§lo§e§lr §a-§b-§d- §cb§6y §e§lE§a§ll§b§le§d§lc§c§lt§6§lr§e§lo§a§lB§b§lo§d§ly§c§l2§6§l5§e§l6§a§l0");
					player.sendMessage(ChatColor.RED + "Error: You must have the permission RainbowArmor.command.use!");
				}
			}else{
				//TODO: Console stuff
					if(args.length == 0){
						Bukkit.getConsoleSender().sendMessage("§c-§6-§e- §a§lR§b§la§d§li§c§ln§6§lb§e§lo§a§lw §b§lA§d§lr§c§lm§6§lo§e§lr §a-§b-§d- §cb§6y §e§lE§a§ll§b§le§d§lc§c§lt§6§lr§e§lo§a§lB§b§lo§d§ly§c§l2§6§l5§e§l6§a§l0");
						Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "equip, give, reset, set");
					}
						
					if(args[0].equalsIgnoreCase("equip") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("reset")){
						
					}else{
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Unknown Option!");
					}
					
				if(args[0].equalsIgnoreCase("get")){
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Only Players can get armor!");
						
					}
				
				if(args[0].equalsIgnoreCase("give")){
					
						if(args.length != 2){
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor give [Player]");
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor give Steve");
						}else{
						if(Bukkit.getConsoleSender().getServer().getPlayerExact(args[1]) != null){
							
							Player targetPlayer = Bukkit.getConsoleSender().getServer().getPlayerExact(args[1]);
								
							targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
							targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
							targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
							targetPlayer.getInventory().addItem(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
								
							targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
						}else{
							Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Player Not Found! Be sure to use the full name!");
						}
					}
					}
				
				
				if(args[0].equalsIgnoreCase("Set")){
					
						if(args.length == 1){
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set [Speed, Color] [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Examples:\n" + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "/RainbowArmor set Speed 10");
						}else{
							
							if(args.length != 5 && !args[1].equalsIgnoreCase("Speed")){
								
								Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Color [Red, Orange, Yellow, Lime, LightBlue, Magenta, Pink, White] [RGB: r, g, b] [Value: 0-255]");
								Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Color Red r 255");
								Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Current Value of " + args[3] + " for " + args[2] +": " + ChatColor.GREEN + ra.getConfig().getInt("Colors." + args[2].toUpperCase() + "." + args[3].toLowerCase()));
							}else{
								if(args[1].equalsIgnoreCase("Color")){
									if(args[2].equalsIgnoreCase("Red") || args[2].equalsIgnoreCase("Orange") || args[2].equalsIgnoreCase("Yellow") || args[2].equalsIgnoreCase("Lime") || args[2].equalsIgnoreCase("LightBlue") || args[2].equalsIgnoreCase("Magenta") || args[2].equalsIgnoreCase("Pink") || args[2].equalsIgnoreCase("White")){
										//TODO: SetterHandler
										Utils.setterHandler(null, args[2], args[3], args[4]);
										
									}else{
										if(!args[1].equalsIgnoreCase("Speed")){
											Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Unknow Color!");
										}	
									}
								}else{
									if(!args[1].equalsIgnoreCase("Speed")){
										Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Not a Valid Argument!");
									}
								}
								
							}
						}
						
					}

				if(args[0].equalsIgnoreCase("Set") && args[1].equalsIgnoreCase("Speed")){
					
						if(args.length == 2){
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor set Speed [Value: 1-256]");
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor set Speed 10");
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Current Speed: " + ChatColor.GREEN + ra.getConfig().getInt("SPEED"));
							
						}else{
							if(args.length != 3){
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Missing Speed Value");
							}else{
								if(Utils.isNumeric(args[2])){
									int INTspeed = Integer.parseInt(args[2]);
									if(Utils.isBetween(0, 257, INTspeed)){
										ra.getConfig().set("SPEED", INTspeed);
										ra.saveConfig();
										ra.reloadConfig();
										
										Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(ra.getDescription().getName());
										
										ra.getServer().getPluginManager().disablePlugin(plugin);
										ra.getServer().getPluginManager().enablePlugin(plugin);

										Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Speed set to " + INTspeed);
									}else Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Speed must be 1 to 256!");
								}else Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Invalid Value!");
							}
						
						}
					}

				if(args[0].equalsIgnoreCase("Reset")){
					Utils.resetConfig();
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "All changed values have been reset!");
				}
				
				//TODO: Equip
				if(args[0].equalsIgnoreCase("equip")){
						if(args.length != 3){
							ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
							ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
							ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
						}else{
						if(ra.getServer().getConsoleSender().getServer().getPlayerExact(args[1]) != null){
							if(args[2].equalsIgnoreCase("Replace")){
								Player targetPlayer = ra.getServer().getConsoleSender().getServer().getPlayerExact(args[1]);
								
								targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
								targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
								targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
								targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
									
								targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
								ra.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
							}else if(args[2].equalsIgnoreCase("Move")){
								Player targetPlayer = ra.getServer().getConsoleSender().getServer().getPlayerExact(args[1]);
								
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
								
								targetPlayer.getInventory().setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET, null, targetPlayer, false));
								targetPlayer.getInventory().setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, null, targetPlayer, false));
								targetPlayer.getInventory().setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, null, targetPlayer, false));
								targetPlayer.getInventory().setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, null, targetPlayer, false));
									
								targetPlayer.sendMessage(ChatColor.GREEN + "You received rainbow armor!");
								ra.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Given rainbow armor to " + args[1]);
							}else{
								ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: " + ChatColor.GREEN + "/RainbowArmor equip [Player] [Replace or Move]");
								ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Replace" + ChatColor.AQUA + " - Replaces The Target Player's Current Armor With Rainbow Armor");
								ra.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Example: " + ChatColor.GREEN + "/RainbowArmor equip Steve Move" + ChatColor.AQUA + " - Moves The Target Player's Current Armor into Their Inventory Before Setting Their Armor to Rainbow Armor");
							}
							
						}else ra.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error: Player Not Found! Be sure to use the full name!");
					}
				}
				
			}
		return false;
	}
	
}
