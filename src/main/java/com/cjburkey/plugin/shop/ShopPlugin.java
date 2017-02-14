package com.cjburkey.plugin.shop;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.plugin.shop.cmd.ShopCommand;
import com.cjburkey.plugin.shop.guis.GuiShopHandler;
import net.milkbowl.vault.economy.Economy;

public class ShopPlugin extends JavaPlugin {
	
	public static final String v = "0.0.7";

	private static ShopPlugin plugin;
	private static Economy econ = null;

	public static final ShopPlugin getPlugin() { return plugin; }
	public static final Economy getEconomy() { return econ; }
	
	public void onEnable() {
		plugin = this;
		
		if(setupEconomy()) {
			Util.log("&a&l&nVault found!  Starting BurkeyShop.");
		} else {
			Util.log("&4&l&nVault is not installed.  Please install it for BurkeyShop to work.");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		if(!IO.getShopFile().exists()) ShopHandler.saveShop();
		
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getServer().getPluginManager().registerEvents(new GuiShopHandler(), this);
		
		ShopHandler.loadShop();
	}
	
	private boolean setupEconomy() {
		if(this.getServer().getPluginManager().getPlugin("Vault") == null) return false;
		RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null) return false;
		econ = rsp.getProvider();
		return econ != null;
	}
	
}