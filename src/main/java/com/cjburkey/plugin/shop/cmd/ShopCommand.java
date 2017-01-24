package com.cjburkey.plugin.shop.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.plugin.shop.ShopHandler;
import com.cjburkey.plugin.shop.ShopPlugin;
import com.cjburkey.plugin.shop.Util;
import com.cjburkey.plugin.shop.guis.GuiShop;

public class ShopCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player && sender.hasPermission("burkeyshop.use")) {
				Player player = (Player) sender;
				ShopPlugin.getGuiHandler().open(player, new GuiShop(player));
				Util.chat(player, ShopPlugin.getPlugin().getConfig().getString("langShopOpen"));
			} else if(!sender.hasPermission("burkeyshop.use")) {
				Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
			} else {
				Util.log("&4&lOnly in-game players may use &6/shop&r");
			}
		} else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if(sender.hasPermission("burkshop.reload")) {
				ShopHandler.loadShop();
				Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langReloaded"));
			} else {
				Util.chat(sender, ShopPlugin.getPlugin().getConfig().getString("langNoPerm"));
			}
		} else if(args.length == 1) {
			sender.sendMessage(Util.color("&4&lUsage: /shop"));
		}
		return true;
	}
	
}