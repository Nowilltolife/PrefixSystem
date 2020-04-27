package com.nowilltolife.ranksystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nowilltolife.ranksystem.util.MySql;

public class CommandReconncet implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(sender instanceof Player) {
		Player p = (Player) sender;
		if(cmd.getName().equals("reconnectmysql")) {
			if(p.hasPermission("ranksystem.reconnect")) {
				MySql.onReconnect();
				p.sendMessage("§c§lRank §7Reconnected MySql");
			}
		}
	}
		return false;
	}

}
