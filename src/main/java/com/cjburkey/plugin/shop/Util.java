package com.cjburkey.plugin.shop;

import java.text.NumberFormat;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import de.tr7zw.itemnbtapi.NBTItem;

public class Util {
	
	public static final void log(Object msg) {
		Bukkit.getServer().getConsoleSender().sendMessage("[" + ShopPlugin.getPlugin().getName() + "] " + color("" + msg));
	}
	
	public static final String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void error(Throwable e) {
		log("&4&lAn error occurred while reading your shop file:");
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
	
	public static final ItemStack setNBTDouble(ItemStack stack, String key, double value) {
		NBTItem item = new NBTItem(stack);
		item.setDouble(key, value);
		return item.getItem();
	}
	
	public static final double getNBTDouble(ItemStack stack, String key) {
		NBTItem item = new NBTItem(stack);
		return item.getDouble(key);
	}
	
	public static final String formatPrice(double price) {
		return "$" + NumberFormat.getInstance().format(price);
	}
	
	public static final void chat(CommandSender to, String msg) {
		to.sendMessage(color(ShopPlugin.getPlugin().getConfig().getString("langChatPrefix") + " " + msg));
	}
	
}