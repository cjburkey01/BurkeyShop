package com.cjburkey.plugin.shop;

import java.io.File;
import java.io.FileWriter;

public class IO {
	
	public static final File getShopFile() { return new File(ShopPlugin.getPlugin().getDataFolder(), "/shop.txt"); }
	
	public static final void initShopFile() {
		if(!getShopFile().exists()) {
			try {
				getShopFile().createNewFile();
				FileWriter writer = new FileWriter(getShopFile());
				String add = "# Lines with # at the beginning are not loaded.\n";
				add += "# Format: ITEM_NAME;BUY_PRICE;SELL_PRICE\n";
				add += "# An example is provided:\n";
				add += "DIRT;1;0.5\n";
				add += "# Damage-value items and blocks work too:\n";
				add += "WOOL;5;2.5\n";
				add += "WOOL:1;5;2.5\n";
				add += "WOOL:2;5;2.5\n";
				writer.write(add);
				writer.close();
			} catch(Exception e) {
				Util.error(e, "Couldn't create default shop file.");
			}
		}
	}
	
}