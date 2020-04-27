package com.nowilltolife.ranksystem.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.nowilltolife.ranksystem.Main;
import com.nowilltolife.ranksystem.api.Database;

import de.myzelyam.api.vanish.VanishAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderApi extends PlaceholderExpansion{
	

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Nowilltolife";
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "prefixsystem";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "2.0v";
	}
	
    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        if(player == null){
            return "";
        }

        // %someplugin_placeholder1%
        if(identifier.equals("player_rank")){
            return Database.getRank(player);
        }

        // %someplugin_placeholder2%
        if(identifier.equals("player_prefix")){
        	if(Database.getPrefix(player).startsWith(" ")) {
        		return Database.getPrefix(player).replaceFirst(" ", "");
        	}
            return Database.getPrefix(player);
        }
        
        if(identifier.equals("player_suffix")){
            return "";
        }
        
        if(identifier.equals("player_color")){
            return Database.getColor(player);
        }
        
        if(identifier.equals("player_displayname")) {
        	return Database.getName(player);
        }
        
        if(identifier.startsWith("player_meta_")) {
        	String key = identifier.replaceAll("player_meta_", "");
        	if(Database.KeyExists(key, player)) {
        		return PlaceholderAPI.setPlaceholders(player, Database.getPlayerMeta(key, player));
        	}else {
        		return null;
        	}
        }
        
        	if(Main.getInstance.getConfig().getBoolean("hooks.supervanish.enabled")) {
        		String vanishprefix = Main.getInstance.getConfig().getString("hooks.supervanish.vanish-prefix");
        boolean vanished = VanishAPI.isInvisible(player);
        if(identifier.equals("vanish")) {
        	if(vanished == true) {
        		return vanishprefix;
        	}else {
        		return null;
        	}
        }
        	}
        
        for(String rank:Database.getAllRanks()) {
        	if(identifier.equals("_" + rank + "_prefix")) {
        		return Database.getPrefix(rank);
        	}
        	if(identifier.equals("_" + rank + "_color")) {
        		return Database.getColor(rank);
        	}
        	if(identifier.equals("_" + rank + "_name")) {
        		return Database.getName(rank);
        	}
        	if(identifier.equals("_" + rank + "_meta_")) {
        		String key = identifier.replaceAll(rank + "_meta_", "");
            	if(Database.KeyExists(key, Database.getRank(player))) {
            		return PlaceholderAPI.setPlaceholders(player, Database.getMeta(key, Database.getRank(player)));
            	}else {
            		return null;
            	}
        	}
        }
        
        if(identifier.startsWith("player_rank_meta_")) {
        	String key = identifier.replaceAll("player_rank_meta_", "");
        	if(Database.KeyExists(key, Database.getRank(player))) {
        		return PlaceholderAPI.setPlaceholders(player, Database.getMeta(key, Database.getRank(player)));
        	}else {
        		return null;
        	}
        }
 
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }

}
