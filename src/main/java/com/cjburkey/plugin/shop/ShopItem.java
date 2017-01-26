package com.cjburkey.plugin.shop;

import org.bukkit.inventory.ItemStack;

public class ShopItem {
	
	private ItemStack stack;
	private double buy;
	private double sell;
	private boolean init = false;
	
	public ShopItem(ItemStack stack, double buyPrice, double sellPrice) {
		this.stack = stack;
		if(this.stack != null) {
			this.buy = buyPrice;
			this.sell = sellPrice;
			init = true;
		}
	}
	
	public ItemStack getStack() { return this.stack; }
	public double getBuyPrice() { return this.buy; }
	public double getSellPrice() { return this.sell; }
	public boolean init() { return this.init; }
	
}