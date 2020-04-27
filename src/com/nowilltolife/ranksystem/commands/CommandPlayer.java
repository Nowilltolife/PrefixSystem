package com.nowilltolife.ranksystem.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nowilltolife.ranksystem.Main;
import com.nowilltolife.ranksystem.api.Database;
import com.nowilltolife.ranksystem.util.MySql;

public class CommandPlayer implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg1.getName().equals("prefixplayer")) {
		String plprefix = Main.getInstance.getConfig().getString("prefix").replaceAll("&", "§");	
		if(arg3.length == 0) {
			arg0.sendMessage(plprefix + " §7/prefixplayer <player> [rank,meta]");
		}else if(arg3.length == 1) {
			if(Bukkit.getPlayer(arg3[0]) != null) {
				Player p = Bukkit.getPlayer(arg3[0]);
			arg0.sendMessage(plprefix + " §7Info about " + p.getName() + "\nRank: "+ Database.getRank(p));
			}else {
				arg0.sendMessage("§cError player does not exist");
			}
		}else {
			if(arg3[1].equals("rank")) {
			if(Bukkit.getPlayer(arg3[0]) != null) {
				Player p = Bukkit.getPlayer(arg3[0]);
				if(RankExists(arg3[1])) {
					Database.setRank(p, arg3[1]);
				}else {
					arg0.sendMessage("§cError rank does not exist");
				}
			}
				}else if(arg3[1].equals("meta")) {
					Player p = (Player)arg0;
						 if(arg3.length == 2) {
							 for(Map<String, String> map: Database.getAllPlayerMetadata(p)) {
								 for(Object Key: map.keySet()) {
								 p.sendMessage(plprefix + " §7Metadata from " + arg3[0]);
								 p.sendMessage("\nKey: " + (String)Key + " Value: " + map.get(Key));
								 }
							 }
							 return true;
						 }
						 String key = arg3[2];
						 String value;
							StringBuilder reasonBuilder = new StringBuilder(); 
							for (int i = 3; i < arg3.length; i++) 
							reasonBuilder.append(arg3[i]).append(' '); 
							value = reasonBuilder.toString().trim().replaceAll("&", "§");
					     if(KeyExists(key, p)) {
					    	 MySql.onUpdate("UPDATE "+MySql.prefix+"Meta SET value = '" + value + "' WHERE uuid = '" + arg3[0] + "' AND meta = '"+key+"'");
					    	 p.sendMessage(plprefix +" §7Updated meta §c"+ key + " §7with value of §c" + value);
					     }else {
					    	 MySql.onUpdate("INSERT INTO "+MySql.prefix+"PlayerMeta(uuid, meta, value) VALUES('" + p.getUniqueId() + "', '" + arg3[2] + "', '" + arg3[3] + "')");
					    	 p.sendMessage(plprefix +" §7Created meta §c"+ key + " §7with value of §c" + value);
					     }
					 }   
				 }
			}else {
				arg0.sendMessage("§cError player does not exist");
			}
		return false;
	}
	
	public static boolean KeyExists(String key, Player p) {
		boolean is = false;
		ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"PlayerMeta WHERE uuid = '" + p.getUniqueId() + "' AND meta = '" +key+"'");
		try {
			if(rs.next()) {
				is = true;
			}
		}catch (SQLException e) {}
		try {
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return is;
	}
	
	public static boolean RankExists(String name) {
		boolean is = false;
		ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + name + "'");
		try {
			if(rs.next()) {
				is = true;
			}
		}catch (SQLException e) {}
		try {
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return is;
	}


}
