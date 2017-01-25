package com.cjburkey.plugin.shop.guis;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.shop.ShopHandler;
import com.cjburkey.plugin.shop.ShopItem;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.gui.IInvGuiScreen;
import com.cjburkey.plugin.shop.gui.InvGuiItem;

public class GuiShop implements IInvGuiScreen {

	private final int size = 6;
	private InvGuiItem[] items;
	private Player player;
	
	public GuiShop(Player ply) {
		this.player = ply;
	}
	
	public void open() {
		Inventory inv = Bukkit.createInventory(this.player, 9 * size);
		ShopItem[] item = ShopHandler.getShopItems();
		ItemStack[] toAdd = new ItemStack[item.length];
		items = new InvGuiItem[item.length];
		for(int i = 0; i < item.length; i ++) {
			ShopItem currentShopItem = item[i];
			toAdd[i] = new ItemStack(currentShopItem.getMaterial(), 1, (byte) item[i].getData());
			Util.log("Data: " + item[i].getData());
			
			List<String> lore = new ArrayList<String>();
			lore.add("&dBuy: " + Util.formatPrice(currentShopItem.getBuyPrice()));
			lore.add("&dSell: " + Util.formatPrice(currentShopItem.getSellPrice()));
			Util.loreItemStack(toAdd[i], lore);
			
			items[i] = new InvGuiItem(toAdd[i], (type) -> {
				int amt = (type.isShiftClick()) ? 64 : 1;
				if(type.isLeftClick()) {
					ShopHandler.buyItem(this.player, currentShopItem.getMaterial(), amt, amt * currentShopItem.getBuyPrice());
				} else if(type.isRightClick()) {
					ShopHandler.sellItem(this.player, currentShopItem.getMaterial(), amt, amt * currentShopItem.getSellPrice());
				}
			});
		}
		
		for(InvGuiItem igi : items) {
			inv.addItem(igi.getStack());
		}
		
		player.openInventory(inv);
	}

	public void click(InventoryClickEvent event) {
		event.setCancelled(true);
		int slot = event.getSlot();
		if(slot < items.length) {
			InvGuiItem item = items[slot];
			if(item != null) {
				item.click(event.getClick());
			}
		}
	}

	public InvGuiItem[] getItems() {
		return this.items;
	}
	
	public ItemStack atPos(int x, int y) {
		return items[x + 9 * y].getStack();
	}

	public Player getOpener() {
		return player;
	}
	
}