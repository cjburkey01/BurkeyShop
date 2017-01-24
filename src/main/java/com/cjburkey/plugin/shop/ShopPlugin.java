package com.cjburkey.plugin.shop;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.plugin.shop.cmd.ShopCommand;
import com.cjburkey.plugin.shop.gui.InvGuiHandler;
import net.milkbowl.vault.economy.Economy;

public class ShopPlugin extends JavaPlugin {

	private static InvGuiHandler guiHandler;
	
	private static ShopPlugin plugin;
	private static Economy econ = null;

	public static final InvGuiHandler getGuiHandler() { return guiHandler; }
	public static final ShopPlugin getPlugin() { return plugin; }
	public static final Economy getEconomy() { return econ; }
	
	public void onEnable() {
		guiHandler = new InvGuiHandler();
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
		IO.initShopFile();
		
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getServer().getPluginManager().registerEvents(guiHandler, this);
		
		ShopHandler.loadShop();
	}
	
	public void onDisable() {
		
	}
	
	private boolean setupEconomy() {
		if(this.getServer().getPluginManager().getPlugin("Vault") == null) return false;
		RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null) return false;
		econ = rsp.getProvider();
		return econ != null;
	}
	
}