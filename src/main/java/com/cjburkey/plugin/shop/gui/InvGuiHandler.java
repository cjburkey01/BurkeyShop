package com.cjburkey.plugin.shop.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.cjburkey.plugin.shop.Util;

public class InvGuiHandler implements Listener {
	
	private IInvGuiScreen current;
	
	public void open(IInvGuiScreen gui) {
		if(gui != null) {
			this.current = gui;
			this.current.open();
		}
	}
	
	public void close() {
		if(isGuiOpen()) {
			this.current.getOpener().closeInventory();
		}
		this.current = null;
	}
	
	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(Util.invName())) {
			this.current.click(e);
		}
	}
	
	public boolean isGuiOpen() {
		return this.current != null;
	}
	
	public IInvGuiScreen getOpenGui() {
		return this.current;
	}
	
}