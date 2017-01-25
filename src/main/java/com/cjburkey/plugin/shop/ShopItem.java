package com.cjburkey.plugin.shop;

import org.bukkit.Material;

public class ShopItem {
	
	private Material mat;
	private int data;
	private double buy;
	private double sell;
	private boolean init = false;
	
	public ShopItem(String matname, int data, double buyPrice, double sellPrice) {
		this.mat = Material.valueOf(matname);
		if(this.mat != null) {
			this.data = data;
			this.buy = buyPrice;
			this.sell = sellPrice;
			init = true;
		}
	}
	
	public Material getMaterial() { return this.mat; }
	public int getData() { return this.data; }
	public double getBuyPrice() { return this.buy; }
	public double getSellPrice() { return this.sell; }
	public boolean init() { return this.init; }
	
}