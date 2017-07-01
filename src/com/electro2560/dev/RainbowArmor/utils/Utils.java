package com.electro2560.dev.RainbowArmor.utils;

import java.io.File;
import java.io.IOException;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.mcstats.MetricsLite;

import com.electro2560.dev.RainbowArmor.RainbowArmor;

import de.tr7zw.itemnbtapi.NBTItem;

public class Utils {

	private static FileConfiguration config = RainbowArmor.get().getConfig();
	
	//Old. Used in version 1.5.0 and older. 1.6.0+ now uses NBT to keep track of rainbow armor. Keeping lore for legacy support checking
	private static final String rainbowLore = "§c§lR§6§la§e§li§a§ln§b§lb§d§lo§c§lw §6§lA§e§lr§a§lm§b§lo§d§lr";
	
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
	
	public static void resetConfig(){
		File conf = new File(RainbowArmor.get().getDataFolder() + File.separator + "config.yml");
		
		if(conf.exists()) conf.delete();
		
		RainbowArmor.get().saveDefaultConfig();

		RainbowArmor.get().reloadConfig();
		
		//We need to restart the timer...
		createTimerTask();
	}
	
	public static ItemStack getColorArmor(Material m){
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		
		meta.setColor(armorColor);
		
		//1.6.0+ now uses NBT rather than a lore
		///List<String> lore = new ArrayList<String>();
		///lore.add(rainbowLore);
		///meta.setLore(lore);
		
		i.setItemMeta(meta);
		
		//Only set the version tag if it's being created for the first time
		NBTItem nbti = new NBTItem(i);
		nbti.setString("RainbowArmor.version", getVersion());
		i = nbti.getItem();
		
		return i;
	}
	
	private static ItemStack setArmorColor(ItemStack item){
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

		meta.setColor(armorColor);
		
		item.setItemMeta(meta);

		return item;
	}
	
	public static void setterHandler(CommandSender sender, String color, String RGB, String value){
		RGB = RGB.toLowerCase();
		color = color.toUpperCase();
		
		if(RGB.equals("r") || RGB.equals("g") || RGB.equals("b")){
			if(isNumeric(value)){
				int INTnumber = Integer.parseInt(value);
				if(isBetween(-1, 256, INTnumber)){
					config.set("Colors." + color + "." + RGB, INTnumber);
					RainbowArmor.get().saveConfig();
					
					sender.sendMessage("§sColor value " + RGB + " of " + color + " set to " + INTnumber);
				}else sender.sendMessage("§cError: Value must be between 0 and 255!");
			}else sender.sendMessage("§cError: Invalid value!");
		}else sender.sendMessage("§cError: Must be r, g, or b");
		
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
				//TODO: Load the armor colors into memory rather than reading from the config each time?
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
						
						NBTItem nbti = null;
						
						if(i.getHelmet() != null) nbti = new NBTItem(i.getHelmet());
						if (i.getHelmet() != null && i.getHelmet().getType() == Material.LEATHER_HELMET){
							if((i.getHelmet().getItemMeta().getLore() != null && i.getHelmet().getItemMeta().getLore().contains(rainbowLore)) || nbti.hasKey("RainbowArmor.version")){
								i.setHelmet(Utils.setArmorColor(i.getHelmet()));
							}
						}
						
						if(i.getChestplate() != null) nbti = new NBTItem(i.getChestplate());
						if (i.getChestplate() != null && i.getChestplate().getType() == Material.LEATHER_CHESTPLATE){
							if((i.getChestplate().getItemMeta().getLore() != null && i.getChestplate().getItemMeta().getLore().contains(rainbowLore)) || nbti.hasKey("RainbowArmor.version")){
								i.setChestplate(Utils.setArmorColor(i.getChestplate()));
							}
						}
						
						if(i.getLeggings() != null) nbti = new NBTItem(i.getLeggings());
						if (i.getLeggings() != null && i.getLeggings().getType() == Material.LEATHER_LEGGINGS){
							if((i.getLeggings().getItemMeta().getLore() != null && i.getLeggings().getItemMeta().getLore().contains(rainbowLore)) || nbti.hasKey("RainbowArmor.version")){
								i.setLeggings(Utils.setArmorColor(i.getLeggings()));
							}
						}
						
						if(i.getBoots() != null) nbti = new NBTItem(i.getBoots());
						if (i.getBoots() != null && i.getBoots().getType() == Material.LEATHER_BOOTS){
							if((i.getBoots().getItemMeta().getLore() != null && i.getBoots().getItemMeta().getLore().contains(rainbowLore)) || nbti.hasKey("RainbowArmor.version")){
								i.setBoots(Utils.setArmorColor(i.getBoots()));
							}
						}
						
					}
				}
				
				//Let's increment so we know which color to use next
				if(value != 8) value++;
				else value = 1;
				
			}
		}, 0, RainbowArmor.get().getConfig().getInt("SPEED"));
	}

	public static String getVersion() {
		return RainbowArmor.get().getDescription().getVersion();
	}
	
	public static void startMetrics(){
		try {
	        MetricsLite metrics = new MetricsLite(RainbowArmor.get());
	        metrics.start();
	    } catch (IOException e) {}
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(RainbowArmor.get());
	}
	
}
