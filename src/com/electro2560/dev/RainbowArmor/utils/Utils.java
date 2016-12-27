package com.electro2560.dev.RainbowArmor.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.electro2560.dev.RainbowArmor.RainbowArmor;

public class Utils {

	static RainbowArmor rainbowArmor = RainbowArmor.get(); 
	static FileConfiguration config = rainbowArmor.getConfig();
	
	public static final String rainbowLore = "§c§lR§6§la§e§li§a§ln§b§lb§d§lo§c§lw §6§lA§e§lr§a§lm§b§lo§d§lr";
	
	public static boolean isNumeric(String input) {
		try {
		    Integer.parseInt(input);
		    return true;
		}catch (NumberFormatException e) {
		    return false;
		}
	}
	
	public static boolean isBetween(int a, int b, int c) {
	    return b > a ? c > a && c < b : c > b && c < a;
	}
	
	//TODO: Surely there must be a better way to do this....
	public static void resetConfig(){
		config.set("checkForUpdates", true);
		
		//RED
		config.set("Colors.RED.r", 255);
		config.set("Colors.RED.g", 0);
		config.set("Colors.RED.b", 8);
		
		//ORANGE
		config.set("Colors.ORANGE.r", 255);
		config.set("Colors.ORANGE.g", 123);
		config.set("Colors.ORANGE.b", 0);
		
		//YELLOW
		config.set("Colors.YELLOW.r", 255);
		config.set("Colors.YELLOW.g", 255);
		config.set("Colors.YELLOW.b", 0);
		
		//LIME
		config.set("Colors.LIME.r", 68);
		config.set("Colors.LIME.g", 255);
		config.set("Colors.LIME.b", 0);
		
		//LIGHT BLUE
		config.set("Colors.LIGHTBLUE.r", 0);
		config.set("Colors.LIGHTBLUE.g", 247);
		config.set("Colors.LIGHTBLUE.b", 255);
		
		//MAGENTA
		config.set("Colors.MAGENTA.r", 204);
		config.set("Colors.MAGENTA.g", 0);
		config.set("Colors.MAGENTA.b", 255);
		
		//PINK
		config.set("Colors.PINK.r", 255);
		config.set("Colors.PINK.g", 0);
		config.set("Colors.PINK.b", 230);
		
		//WHITE
		config.set("Colors.WHITE.r", 255);
		config.set("Colors.WHITE.g", 255);
		config.set("Colors.WHITE.b", 255);
		
		config.set("Speed", 10);
				
		rainbowArmor.saveConfig();
		rainbowArmor.reloadConfig();
				
		
		
		//We need to restart the timer...
		createTimerTask();
	}
	
	public static ItemStack getColorArmor(Material m, Color c, Player p, boolean setEnchants){
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		
		List<String> lore = new ArrayList<String>();
		lore.add(rainbowLore);
		
		meta.setLore(lore);
		
		i.setItemMeta(meta);
		
		if(setEnchants){
			switch (m) {
			case LEATHER_HELMET:
				i.addUnsafeEnchantments(p.getInventory().getHelmet().getEnchantments());
				i.setDurability(p.getInventory().getHelmet().getDurability());
				break;
			case LEATHER_CHESTPLATE:
				i.addUnsafeEnchantments(p.getInventory().getChestplate().getEnchantments());
				i.setDurability(p.getInventory().getChestplate().getDurability());
				break;
			case LEATHER_LEGGINGS:
				i.addUnsafeEnchantments(p.getInventory().getLeggings().getEnchantments());
				i.setDurability(p.getInventory().getLeggings().getDurability());
				break;
			case LEATHER_BOOTS:
				i.addUnsafeEnchantments(p.getInventory().getBoots().getEnchantments());
				i.setDurability(p.getInventory().getBoots().getDurability());
				break;
			default: break;
			}
		}
		
		return i;
	}
	
	//TODO: This can be cleaned up quite a bit. I'll push an update to this soon. This code is around 2 years old...
	public static void setterHandler(Player player, String color, String RGB, String value){
		RGB = RGB.toLowerCase();
		color = color.toUpperCase();
		
		if((player instanceof Player) && !(player == null)){
			if(RGB.equals("r") || RGB.equals("g") || RGB.equals("b")){
				if(isNumeric(value)){
					int INTnumber = Integer.parseInt(value);
					if(isBetween(-1, 256, INTnumber)){
						config.set("Colors." + color + "." + RGB, INTnumber);
						rainbowArmor.saveConfig();
						player.sendMessage("§sColor value " + RGB + " of " + color + " set to " + INTnumber);
					}else player.sendMessage("§cError: Value must be between 0 and 255!");
				}else player.sendMessage("§cError: Invalid value!");
			}else player.sendMessage("§cError: Must be r, g, or b");
		}else{
			//Console
			if(RGB.equals("r") || RGB.equals("g") || RGB.equals("b")){
				if(isNumeric(value)){
					int INTnumber = Integer.parseInt(value);
					
					if(isBetween(-1, 256, INTnumber)){
						config.set("Colors." + color + "." + RGB, INTnumber);
						rainbowArmor.saveConfig();
						Bukkit.getConsoleSender().sendMessage("§aColor value " + RGB + " of " + color + " set to " + INTnumber);
					}else Bukkit.getConsoleSender().sendMessage("§cError: Value must be between 0 and 255!");
				}else Bukkit.getConsoleSender().sendMessage("§cError: Invalid value!");
			}else Bukkit.getConsoleSender().sendMessage("§cError: Must be r, g, or b");
		}
		
	}
	
	public static boolean isCheckForUpdates() {
		return config.getBoolean("checkForUpdates", true);
	}

	
	public static int value = 1;
	public static Color armorColor;
	
	public static void createTimerTask() {
		//Just incase a timer is already running. Specifically if someone uses the /ra reload command, we recall this method to start the new timer with the new speed 
		Bukkit.getScheduler().cancelTasks(RainbowArmor.get());
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(RainbowArmor.get(), new Runnable() {
			@Override
			public void run() {
				
				//Let's determine what color to use
				switch (value) {
				case 1:
					armorColor = Color.fromRGB(config.getInt("Colors.RED.r"), config.getInt("Colors.RED.g"), config.getInt("Colors.RED.b"));
					break;
				case 2:
					armorColor = Color.fromRGB(config.getInt("Colors.ORANGE.r"), config.getInt("Colors.ORANGE.g"), config.getInt("Colors.ORANGE.b"));
					break;
				case 3:
					armorColor = Color.fromRGB(config.getInt("Colors.YELLOW.r"), config.getInt("Colors.YELLOW.g"), config.getInt("Colors.YELLOW.b"));
					break;
				case 4:
					armorColor = Color.fromRGB(config.getInt("Colors.LIME.r"), config.getInt("Colors.LIME.g"), config.getInt("Colors.LIME.b"));
					break;
				case 5:
					armorColor = Color.fromRGB(config.getInt("Colors.LIGHTBLUE.r"), config.getInt("Colors.LIGHTBLUE.g"), config.getInt("Colors.LIGHTBLUE.b"));
					break;
				case 6:
					armorColor = Color.fromRGB(config.getInt("Colors.MAGENTA.r"), config.getInt("Colors.MAGENTA.g"), config.getInt("Colors.MAGENTA.b"));
					break;
				case 7:
					armorColor = Color.fromRGB(config.getInt("Colors.PINK.r"), config.getInt("Colors.PINK.g"), config.getInt("Colors.PINK.b"));
					break;
				case 8:
					armorColor = Color.fromRGB(config.getInt("Colors.WHITE.r"), config.getInt("Colors.WHITE.g"), config.getInt("Colors.WHITE.b"));
					break;
				default: break;
				}
				
				for (Player p : Bukkit.getServer().getOnlinePlayers()){
					if(p.hasPermission(Perms.canUse) || p.hasPermission(Perms.canUseAll)){
						PlayerInventory i = p.getInventory();
						
						if (i.getHelmet() != null && i.getHelmet().getType() == Material.LEATHER_HELMET && i.getHelmet().getItemMeta().getLore().contains(rainbowLore)) i.setHelmet(Utils.getColorArmor(Material.LEATHER_HELMET, armorColor, p, true));
						if (i.getChestplate() != null && i.getChestplate().getType() == Material.LEATHER_CHESTPLATE && i.getChestplate().getItemMeta().getLore().contains(rainbowLore)) i.setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, armorColor, p, true));
						if (i.getLeggings() != null && i.getLeggings().getType() == Material.LEATHER_LEGGINGS && i.getLeggings().getItemMeta().getLore().contains(rainbowLore)) i.setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, armorColor, p, true));
						if (i.getBoots() != null && i.getBoots().getType() == Material.LEATHER_BOOTS && i.getBoots().getItemMeta().getLore().contains(rainbowLore)) i.setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, armorColor, p, true));
					}
				}
				
				//Let's increment so we know which color to use next
				if(value != 8) value++;	
				else value = 1;
				
			}
		}, 0, RainbowArmor.get().getConfig().getInt("SPEED"));
	}
	
}
