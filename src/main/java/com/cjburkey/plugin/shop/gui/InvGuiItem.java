package com.cjburkey.plugin.shop.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class InvGuiItem {
	
	private ItemStack stack;
	private ClickEvent e;
	
	public InvGuiItem(ItemStack stack, ClickEvent e) {
		this.stack = stack;
		this.e = e;
	}
	
	public void click(ClickType type) {
		this.e.click(type);
	}
	
	public ItemStack getStack() {
		return this.stack;
	}
	
}