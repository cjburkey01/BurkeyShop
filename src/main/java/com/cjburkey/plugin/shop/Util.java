package com.cjburkey.plugin.shop;

import java.text.NumberFormat;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
	
	public static final void log(Object msg) {
		Bukkit.getServer().getConsoleSender().sendMessage("[" + ShopPlugin.getPlugin().getName() + "] " + color("" + msg));
	}
	
	public static final String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void error(Throwable e, String msg) {
		log("&4&l" + msg);
		log("&4&l  " + e.getMessage());
	}
	
	public static final ItemStack nameItemStack(ItemStack stack, String name) {
		ItemMeta data = stack.getItemMeta();
		data.setDisplayName(color(name));
		stack.setItemMeta(data);
		return stack;
	}
	
	public static final ItemStack loreItemStack(ItemStack stack, List<String> lore) {
		ItemMeta meta = stack.getItemMeta();
		for(int i = 0; i < lore.size(); i ++) { lore.set(i, color(lore.get(i))); }
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static final String formatPrice(double price) {
		return "$" + NumberFormat.getInstance().format(price);
	}
	
	public static final void chat(CommandSender to, String msg) {
		to.sendMessage(color(ShopPlugin.getPlugin().getConfig().getString("langChatPrefix") + " " + msg));
	}
	
	public static final ItemStack fromString(String s) {
		int data = 0;
		String mat = s.trim();
		if(mat.contains(":")) {
			String[] spl = mat.split(":");
			mat = spl[0].trim();
			data = Integer.parseInt(spl[1].trim());
		}
		return new ItemStack(Material.getMaterial(mat), 1, (byte) data);
	}
	
	public static final String invName() {
		return color(ShopPlugin.getPlugin().getConfig().getString("langGuiName"));
	}
	
}