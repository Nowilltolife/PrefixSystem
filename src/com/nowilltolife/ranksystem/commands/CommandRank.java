package com.nowilltolife.ranksystem.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.nowilltolife.ranksystem.Main;
import com.nowilltolife.ranksystem.api.Database;
import com.nowilltolife.ranksystem.util.MySql;

public class CommandRank implements CommandExecutor{
	
	public static ArrayList<String> letters = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
			if(cmd.getName().equals("prefixsystem")) {
				if(p.hasPermission("PrefixSystem.rank")) {
					String plprefix = Main.getInstance.getConfig().getString("prefix").replaceAll("&", "§");				
					if(args.length == 0) {
						p.sendMessage(plprefix+ " §7/prefixsystem [ranks]<Rank> [create, prefix, suffix, color, displayname]");
					}
						if(args.length == 1) {
							if(args[0].equals("ranks")) {
								p.sendMessage(plprefix+ " §7displaying all ranks:");
								for(String rank:Database.getAllRanks()) {
									if(Database.getName(rank) != "" || Database.getName(rank) != " " || Database.getName(rank) != null)
									p.sendMessage("§7 - §f" + Database.getName(rank));	
									else 
										p.sendMessage("§7 - §f" + Database.getPrefix(rank));	
								}
							}
							else if(RankExists(args[0], p)) {
				    		p.sendMessage(plprefix+ " §7Info about " + args[0]);
				    		p.sendMessage("§7 - §fPrefix: " + Database.getPrefix(args[0]));
				    		p.sendMessage("§7 - §fColor: " + Database.getColor(args[0]) + "COLOR");
				    		p.sendMessage("§7 - §fDisplayName: " + Database.getName(args[0]));
				    	 }else {
				    		 p.sendMessage("§c§lRank §7The Rank §c" + args[0]+ " §7doesn't exist!");
				    	 }
						}
						else if(args.length >= 2) {
						if(args[1].equals("create")) {
							 if(!(RankExists(args[0], p))) {
								 MySql.onUpdate("INSERT INTO "+MySql.prefix+"Ranks(name) VALUES('" + args[0] + "')");
								 p.sendMessage(plprefix+ " §7The Rank §c" + args[0] + " §7was succesfully created");								 
							 }else {
								 p.sendMessage(plprefix+ " §7The Rank §c" +args[0] + " §7already exists!");
							 }
						 }
						 else if(args[1].equals("prefix")) {
					    	 if(RankExists(args[0], p)) {
					    		 if(args.length == 2) {
					    			 p.sendMessage(plprefix + " §7Prefix of " + args[0] + ": " + Database.getPrefix(args[0]));
								 return true;
				    		 }
					    		 String prefix = "";
									StringBuilder reasonBuilder = new StringBuilder(); 
									for (int i = 2; i < args.length; i++) 
									reasonBuilder.append(args[i]).append(' '); 									
									prefix = reasonBuilder.toString().trim().replaceAll("&", "§");
					    		 MySql.onUpdate("UPDATE "+MySql.prefix+"Ranks SET prefix = '" + prefix + "' WHERE name LIKE '" + args[0] + "'");
					    		 p.sendMessage(plprefix+ " §7Succesfully set the prefix for §c" + args[0]+ " §7to §c" + prefix);
					    	 }else {
					    		 p.sendMessage(plprefix+ " §7The Rank §c" + args[0]+ " §7doesn't exist!");
					    	 }           
					     }
						 else if(args[1].equals("suffix")) {
					    	 if(RankExists(args[0], p)) {
					    		 if(args.length == 2) {
					    			 p.sendMessage(plprefix + " §7Suffix of " + args[0] + ": " + Database.getPrefix(args[0]));
								 return true;
				    		 }
					    		 String suffix = "";
									StringBuilder reasonBuilder = new StringBuilder(); 
									for (int i = 2; i < args.length; i++) 
									reasonBuilder.append(args[i]).append(' '); 									
									suffix = org.bukkit.ChatColor.translateAlternateColorCodes('&', reasonBuilder.toString().trim()); 
					    		 MySql.onUpdate("UPDATE "+MySql.prefix+"Ranks SET suffix = '" + suffix + "' WHERE name LIKE '" + args[0] + "'");
					    		 p.sendMessage(plprefix+ " §7Succesfully set the prefix for §c" + args[0]+ " §7to §c" + suffix);
					    	 }else {
					    		 p.sendMessage(plprefix+ " §7The Rank §c" + args[0]+ " §7doesn't exist!");
					    	 }
						 }else if(args[1].equals("color")){
							 if(RankExists(args[0], p)) {
					    		 if(args.length == 2) {
					    			 p.sendMessage(plprefix + " §7Color of " + args[0] + ": " + Database.getColor(args[0]) + "COLOR");
								 return true;
				    		 }
								 String color = "";								 
									StringBuilder reasonBuilder = new StringBuilder(); 
									for (int i = 2; i < args.length; i++) 
									reasonBuilder.append(args[i]).append(' '); 
									color = reasonBuilder.toString().trim().replaceAll("&", "§");	
								 MySql.onUpdate("UPDATE "+MySql.prefix+"Ranks SET color = '"+color+"' WHERE name LIKE '" + args[0] + "'");
								 p.sendMessage(plprefix+ " §7Set the color for §c" + args[0] + " §7to §c" + color + "COLOR");
							 }else {
					    		 p.sendMessage(plprefix+ " §7The Rank §c" + args[0]+ " §7doesn't exist!");
					    	 }    
						 }else if(args[1].equals("displayname")){
							 if(RankExists(args[0], p)) {
					    		 if(args.length == 2) {
					    			 p.sendMessage(plprefix + " §7Displayname of " + args[0] + ": " + Database.getName(args[0]));
									 return true;
					    		 }
								 String name = "";								 
									StringBuilder reasonBuilder = new StringBuilder(); 
									for (int i = 2; i < args.length; i++) 
									reasonBuilder.append(args[i]).append(' '); 
									name = reasonBuilder.toString().trim().replaceAll("&", "§");	
								 MySql.onUpdate("UPDATE "+MySql.prefix+"Ranks SET displayname = '"+name+"' WHERE name LIKE '" + args[0] + "'");
								 p.sendMessage(plprefix+ " §7Set the displayname for §c" + args[0] + " §7to §c" + name);
							 }else {
					    		 p.sendMessage(plprefix+ " §7The Rank §c" + args[0]+ " §7doesn't exist!");
					    	 }    
						 }else if(args[1].equals("meta")) {
							 if(RankExists(args[0], p)) {
								 if(args.length == 2) {
									 for(Map<String, String> map: Database.getAllMetadata(args[0])) {
										 for(Object Key: map.keySet()) {
										 p.sendMessage(plprefix + " §7Metadata from " + args[0]);
										 p.sendMessage("\nKey: " + (String)Key + " Value: " + map.get(Key));
										 }
									 }
									 return true;
								 }
								 String key = args[2];
								 String value;
									StringBuilder reasonBuilder = new StringBuilder(); 
									for (int i = 3; i < args.length; i++) 
									reasonBuilder.append(args[i]).append(' '); 
									value = reasonBuilder.toString().trim().replaceAll("&", "§");
							     if(Database.KeyExists(key, args[0])) {
							    	 MySql.onUpdate("UPDATE "+MySql.prefix+"Meta SET value = '" + value + "' WHERE rank = '" + args[0] + "' AND meta = '"+key+"'");
							    	 p.sendMessage(plprefix +" §7Updated meta §c"+ key + " §7with value of §c" + value);
							     }else {
							    	 MySql.onUpdate("INSERT INTO "+MySql.prefix+"Meta(rank, meta, value) VALUES('" + args[0] + "', '" + args[2] + "', '" + args[3] + "')");
							    	 p.sendMessage(plprefix +" §7Created meta §c"+ key + " §7with value of §c" + value);
							     }
							 }else {
					    		 p.sendMessage(plprefix+ " §7The Rank §c" + args[0]+ " §7doesn't exist!");
					    	 }    
						 }
					 }
					 }
			}
		return false;	
	}
	
	public static boolean RankExists(String name, CommandSender sender) {
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
			sender.sendMessage("§cERROR SYSTEM FAILURE" + e.getErrorCode() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return is;
	}


}
