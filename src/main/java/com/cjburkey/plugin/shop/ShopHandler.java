package com.cjburkey.plugin.shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopHandler {
	
	private static final List<ShopItem> items = new ArrayList<ShopItem>();
	
	public static final void loadShop() {
		long start = System.nanoTime();
		ShopPlugin.getPlugin().reloadConfig();
		ShopPlugin.getPlugin().saveConfig();
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
							ShopItem item = new ShopItem(Util.fromString(split[0]), buy, sell);
							if(item.init()) items.add(item); else Util.log("&4&lThere was an error while initializing the shop item '" + split[0].trim() + "'.");
						}
					}
				}
				reader.close();
			} catch(Exception e) { Util.error(e, "An error occurred while reading your shop file."); }
		}
		long end = System.nanoTime();
		long time = end - start;
		Util.log("&2&lReloaded shop in " + time + " nanoseconds.");
	}
	
	public static final void saveShop() {
		IO.getShopFile().delete();
		try {
			IO.getShopFile().createNewFile();
			FileWriter writer = new FileWriter(IO.getShopFile());
			String add = "# V: " + ShopPlugin.v + "\n\n";
			add += "# Lines with # at the beginning are not loaded.\n";
			add += "# Format: ITEM_NAME[:DATA_VALUE];BUY_PRICE;SELL_PRICE\n";
			add += "# Example: WOOL:2;5;2\n";
			add += "# Example: STICK;5;2\n\n";
			for(ShopItem item : items) {
				add += item.getStack().getType() + ((item.getStack().getDurability() != 0) ? (":" + item.getStack().getDurability()) : "") + ";" + item.getBuyPrice() + ";" + item.getSellPrice() + "\n";
			}
			writer.write(add);
			writer.close();
		} catch(Exception e) {
			Util.error(e, "Couldn't create default shop file.");
		}
	}
	
	public static final void addItem(ShopItem item) {
		loadShop();
		items.add(item);
		saveShop();
	}
	
	public static final boolean removeItem(ItemStack stack) {
		loadShop();
		for(ShopItem item : items) {
			if(item.getStack().getType().equals(stack.getType())) {
				if(item.getStack().getDurability() == stack.getDurability()) {
					items.remove(item);
					saveShop();
					return true;
				}
			}
		}
		return false;
	}
	
	public static final boolean buyItem(Player player, ItemStack stack, double cost) {
		if(ShopPlugin.getEconomy().getBalance(player) >= cost) {
			ShopPlugin.getEconomy().withdrawPlayer(player, cost);
			player.getInventory().addItem(stack);
			return true;
		} else {
			Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langNotEnoughMoney"));
		}
		return false;
	}
	
	public static final boolean sellItem(Player player, ItemStack stahck, double cost) {
		HashMap<Integer, ? extends ItemStack> items = player.getInventory().all(stahck.getType());
		for(int i = 0; i < items.size(); i ++) {
			ItemStack tmp = items.get(i);
			if(tmp.getDurability() != stahck.getDurability()) {
				items.remove(i);
			}
		}
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		int total = 0;
		for(Entry<Integer, ? extends ItemStack> entry : items.entrySet()) { ItemStack s = (ItemStack) entry.getValue(); total += s.getAmount(); stacks.add(s); }
		int amtLeft = stahck.getAmount();
		if(total >= stahck.getAmount()) {
			for(ItemStack stack : stacks) {
				if(stack.getAmount() >= amtLeft) {
					stack.setAmount(stack.getAmount() - stahck.getAmount());
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