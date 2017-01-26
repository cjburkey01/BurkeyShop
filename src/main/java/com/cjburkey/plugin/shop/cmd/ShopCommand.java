package com.cjburkey.plugin.shop.cmd;

import java.io.FileWriter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.shop.IO;
import com.cjburkey.plugin.shop.ShopHandler;
import com.cjburkey.plugin.shop.ShopPlugin;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.guis.GuiShop;

public class ShopCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player && sender.hasPermission("burkeyshop.use")) {
				Player player = (Player) sender;
				ShopPlugin.getGuiHandler().open(new GuiShop(player, 0));
				Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langShopOpen"));
			} else if(!sender.hasPermission("burkeyshop.use")) {
				Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
			} else {
				Util.log("&4&lOnly in-game players may use &6/shop&r");
			}
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("burkshop.admin")) {
					ShopHandler.loadShop();
					Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langReloaded"));
				} else {
					Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
				}
			} else {
				sender.sendMessage(Util.color("&4&lUsage:"));
				sender.sendMessage(Util.color("&4&l  /shop"));
				sender.sendMessage(Util.color("&4&l  /shop reload"));
				sender.sendMessage(Util.color("&4&l  /shop add <buyPrice> <sellPrice>"));
			}
		} else if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add")) {
				if(sender instanceof Player) {
					if(sender.hasPermission("burkshop.admin")) {
						Player ply = (Player) sender;
						ItemStack hand = ply.getInventory().getItemInMainHand();
						if(hand != null) {
							double buy = Integer.parseInt(args[1]);
							double sell = Integer.parseInt(args[2]);
							try {
								FileWriter writer = new FileWriter(IO.getShopFile(), true);
								writer.write(hand.getType() + ((hand.getDurability() != 0) ? (":" + hand.getDurability()) : "") + ";" + buy + ";" + sell + "\n");
								writer.close();
							} catch(Exception e) {
								Util.error(e, "An error occurred while adding that item.");
							}
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langAddedItem"));
							ShopHandler.loadShop();
						} else {
							Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoHand"));
						}
					} else {
						Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
					}
				} else {
					Util.log("&4&lOnly in-game players may add items to the shop.");
				}
			}
		} else {
			sender.sendMessage(Util.color("&4&lUsage:"));
			sender.sendMessage(Util.color("&4&l  /shop"));
			sender.sendMessage(Util.color("&4&l  /shop reload"));
			sender.sendMessage(Util.color("&4&l  /shop add <buyPrice> <sellPrice>"));
		}
		return true;
	}
	
}