package com.cjburkey.plugin.shop;

import java.io.File;

public class IO {
	
	public static final File getShopFile() { return new File(ShopPlugin.getPlugin().getDataFolder(), "/shop.txt"); }
	
}