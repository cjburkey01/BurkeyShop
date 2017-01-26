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
import com.cjburkey.plugin.shop.ShopPlugin;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.gui.IInvGuiScreen;
import com.cjburkey.plugin.shop.gui.InvGuiItem;

public class GuiShop implements IInvGuiScreen {

	private final int size = 6;
	private InvGuiItem[] items;
	private Player player;
	private int page;
	
	public GuiShop(Player ply, int page) {
		this.player = ply;
		this.page = page;
	}
	
	public void open() {
		Inventory inv = Bukkit.createInventory(this.player, 9 * size, Util.invName());
		ShopItem[] item = ShopHandler.getShopItems();
		ItemStack[] toAdd = new ItemStack[item.length];
		items = new InvGuiItem[this.size * 9];

		int start = this.page * ((this.size - 1) * 9);
		int listSize = Math.min(9 * (this.size - 1), item.length - start);
		for(int num = 0; num < listSize; num ++) {
			ShopItem currentShopItem = item[start + num];
			toAdd[start + num] = currentShopItem.getStack().clone();
			
			List<String> lore = new ArrayList<String>();
			lore.add("&dBuy: " + Util.formatPrice(currentShopItem.getBuyPrice()));
			lore.add("&dSell: " + Util.formatPrice(currentShopItem.getSellPrice()));
			Util.loreItemStack(toAdd[start + num], lore);
			
			items[num] = new InvGuiItem(toAdd[start + num], (type) -> {
				ItemStack toBuy = currentShopItem.getStack().clone();
				toBuy.setAmount((type.isShiftClick()) ? 64 : 1);
				if(type.isLeftClick()) {
					ShopHandler.buyItem(this.player, toBuy, toBuy.getAmount() * currentShopItem.getBuyPrice());
				} else if(type.isRightClick()) {
					ShopHandler.sellItem(this.player, toBuy, toBuy.getAmount() * currentShopItem.getSellPrice());
				}
			});
		}
		
		for(int i = 0; i < items.length; i ++) {
			InvGuiItem igi = items[i];
			if(igi != null) {
				//inv.addItem(igi.getStack());
				inv.setItem(i, igi.getStack());
			}
		}
		
		ItemStack backStack = Util.fromString(ShopPlugin.getPlugin().getConfig().getString("guiBackButton"));
		ItemStack nextStack = Util.fromString(ShopPlugin.getPlugin().getConfig().getString("guiNextButton"));
		
		Util.nameItemStack(backStack, ShopPlugin.getPlugin().getConfig().getString("langBackPage"));
		Util.nameItemStack(nextStack, ShopPlugin.getPlugin().getConfig().getString("langNextPage"));
		
		InvGuiItem back = new InvGuiItem(backStack, (type) -> {
			ShopPlugin.getGuiHandler().open(new GuiShop(this.player, this.page - 1));
		});
		
		InvGuiItem forw = new InvGuiItem(nextStack, (type) -> {
			ShopPlugin.getGuiHandler().open(new GuiShop(this.player, this.page + 1));
		});
		
		items[items.length - 3] = back;
		items[items.length - 2] = forw;
		
		if(this.page > 0) inv.setItem(45, backStack);
		if(this.page < this.numberOfPages()) inv.setItem(53, nextStack);
		
		player.openInventory(inv);
	}
	
	public int numberOfPages() {
		return (int) Math.ceil(ShopHandler.getShopItems().length / ((this.size - 1) * 9));
	}

	public void click(InventoryClickEvent event) {
		event.setCancelled(true);
		ItemStack stahck = event.getCurrentItem();
		for(InvGuiItem item : items) {
			if(item != null && item.getStack().equals(stahck)) {
				item.click(event.getClick());
				return;
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