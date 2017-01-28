package com.cjburkey.plugin.shop.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.gui.GuiHandler;

public class GuiShopHandler implements Listener {
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(Util.invName())) {
			GuiShop shop = GuiHandler.get((Player) e.getWhoClicked());
			shop.click(e);
		}
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		if(e.getInventory().getName().equals(Util.invName())) {
			Player p = (Player) e.getPlayer();
			if(GuiHandler.isOpen(p)) {
				GuiHandler.close(p);
			}
		}
	}
	
}