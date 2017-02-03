package com.cjburkey.plugin.shop.gui;

import java.util.HashMap;
import org.bukkit.entity.Player;
import com.cjburkey.plugin.shop.guis.GuiShop;

public class GuiHandler {
	
	private static final HashMap<Player, GuiShop> shops = new HashMap<Player, GuiShop>();
	
	public static final void open(Player player, GuiShop shop) {
		shops.put(player, shop);
		shop.open();
	}
	
	public static final void close(Player player) {
		if(isOpen(player)) {
			shops.remove(player);
			player.closeInventory();
		}
	}
	
	public static final boolean isOpen(Player player) {
		return get(player) != null;
	}
	
	public static final GuiShop get(Player player) {
		return shops.get(player);
	}
	
}