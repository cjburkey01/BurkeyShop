package com.cjburkey.plugin.shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopHandler {
	
	private static final List<ShopItem> items = new ArrayList<ShopItem>();
	
	public static final void loadShop() {
		items.clear();
		if(IO.getShopFile().exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(IO.getShopFile()));
				String line = "";
				while((line = reader.readLine()) != null) {
					if(!line.startsWith("#")) {
						String[] split = line.split(";");
						double buy = 0.0d;
						double sell = 0.0d;
						try { buy = Double.parseDouble(split[1].trim()); sell = Double.parseDouble(split[2].trim()); } catch(Exception e) {  }
						if(split.length == 3) {
							int data = 0;
							String mat = split[0].trim();
							if(mat.contains(":")) {
								String[] spl = mat.split(":");
								mat = spl[0].trim();
								data = Integer.parseInt(spl[1].trim());
							}
							ShopItem item = new ShopItem(mat, data, buy, sell);
							if(item.init()) items.add(item); else Util.log("&4&lThere was an error while initializing the shop item '" + split[0].trim() + "'.");
						}
					}
				}
				reader.close();
			} catch(Exception e) { Util.error(e, "An error occurred while reading your shop file."); }
		}
	}
	
	public static final boolean buyItem(Player player, Material item, int amount, double cost) {
		if(ShopPlugin.getEconomy().getBalance(player) >= cost) {
			ShopPlugin.getEconomy().withdrawPlayer(player, cost);
			player.getInventory().addItem(new ItemStack(item, amount));
			return true;
		} else {
			Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langNotEnoughMoney"));
		}
		return false;
	}
	
	public static final boolean sellItem(Player player, Material item, int amount, double cost) {
		HashMap<Integer, ? extends ItemStack> items = player.getInventory().all(item);
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		int total = 0;
		for(Entry<Integer, ? extends ItemStack> entry : items.entrySet()) { ItemStack s = (ItemStack) entry.getValue(); total += s.getAmount(); stacks.add(s); }
		int amtLeft = amount;
		if(total >= amount) {
			for(ItemStack stack : stacks) {
				if(stack.getAmount() >= amtLeft) {
					stack.setAmount(stack.getAmount() - amount);
					if(stack.getAmount() == 0) { player.getInventory().remove(stack); }
					amtLeft = 0;
					ShopPlugin.getEconomy().depositPlayer(player, cost);
					return true;
				} else {
					amtLeft -= stack.getAmount();
					player.getInventory().remove(stack);
				}
			}
		} else {
			Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langNotEnoughItems"));
		}
		return false;
	}
	
	public static final ShopItem[] getShopItems() {
		return items.toArray(new ShopItem[items.size()]);
	}
	
}