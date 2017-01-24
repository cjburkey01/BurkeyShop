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
				String add = "# Lines with # at the beginning are not loaded.\n# Format: ITEM_NAME;BUY_PRICE;SELL_PRICE\n# An example is provided:\nDIRT;1;0.5\n";
				writer.write(add);
				writer.close();
			} catch(Exception e) {
				Util.error(e);
			}
		}
	}
	
}