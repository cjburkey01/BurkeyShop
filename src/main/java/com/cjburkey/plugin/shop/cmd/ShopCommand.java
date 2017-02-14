package com.cjburkey.plugin.shop.cmd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.shop.ShopHandler;
import com.cjburkey.plugin.shop.ShopItem;
import com.cjburkey.plugin.shop.ShopPlugin;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.gui.GuiHandler;
import com.cjburkey.plugin.shop.guis.GuiShop;

public class ShopCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		/*if(sender instanceof Player) {
			player((Player) sender);
		} else {
			console(sender);
		}*/
		if(args.length == 0) {
			if(sender instanceof Player && sender.hasPermission("burkeyshop.use")) {
				Player player = (Player) sender;
				GuiHandler.open(player, new GuiShop(player, 0));
				Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langShopOpen"));
				return true;
			} else if(!sender.hasPermission("burkeyshop.use")) {
				Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
				return true;
			} else {
				Util.log("&4&lOnly in-game players may use &6/shop&r");
				return true;
			}
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("burkshop.admin")) {
					ShopHandler.loadShop();
					Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langReloaded"));
					return true;
				} else {
					Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
					return true;
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(sender.hasPermission("burkshop.admin")) {
					Player ply = (Player) sender;
					ItemStack hand = ply.getInventory().getItemInMainHand();
					if(hand != null && hand.getType() != null && !hand.getType().equals(Material.AIR)) {
						if(ShopHandler.removeItem(hand)) {
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langRemovedItem"));
							return true;
						} else {
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoRem"));
							return true;
						}
					}
				} else {
					Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
					return true;
				}
			}
		} else if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add")) {
				if(sender instanceof Player) {
					if(sender.hasPermission("burkshop.admin")) {
						Player ply = (Player) sender;
						ItemStack hand = ply.getInventory().getItemInMainHand();
						if(hand != null && !hand.getType().equals(Material.AIR)) {
							double buy = Double.parseDouble(args[1]);
							double sell = Double.parseDouble(args[2]);
							ShopHandler.addItem(new ShopItem(hand, buy, sell));
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langAddedItem"));
							return true;
						} else {
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoHand"));
							return true;
						}
					} else {
						Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
						return true;
					}
				} else {
					Util.log("&4&lOnly in-game players may add items to the shop.");
					return true;
				}
			}
		}
		
		sender.sendMessage(Util.color("&4&lUsage:"));
		sender.sendMessage(Util.color("&4&l  /shop"));
		sender.sendMessage(Util.color("&4&l  /shop reload"));
		sender.sendMessage(Util.color("&4&l  /shop add <buyPrice> <sellPrice>"));
		sender.sendMessage(Util.color("&4&l  /shop remove"));
		return true;
	}
	
	/*private void player(Player player) {
		
	}
	
	private void console(CommandSender sender) {
		
	}*/
	
}