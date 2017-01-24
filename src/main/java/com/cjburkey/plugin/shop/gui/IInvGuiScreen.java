package com.cjburkey.plugin.shop.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IInvGuiScreen {
	
	void open();
	void click(InventoryClickEvent event);
	InvGuiItem[] getItems();
	ItemStack atPos(int x, int y);
	Player getOpener();
	
}