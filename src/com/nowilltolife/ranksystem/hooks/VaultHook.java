package com.nowilltolife.ranksystem.hooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.nowilltolife.ranksystem.Main;
import com.nowilltolife.ranksystem.api.Database;
import com.nowilltolife.ranksystem.util.MySql;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class VaultHook {


	public static void sync(Player p) {
		String[] gr = Main.getPermissions().getPlayerGroups(p);
		if(Main.getPermissions().getName().equals("LuckPerms")) {
			LuckPerms api = LuckPermsProvider.get();
			User usr = api.getUserManager().getUser(p.getName());
			usr.getPrimaryGroup();
	    	if(PlayerExists(p.getName())) {
	    		Database.setRank(p, usr.getPrimaryGroup()
	    				);
	        	}else {
	        		registerPlayer(p.getName());
	        	}
	 
		}else {
    	if(PlayerExists(p.getName())) {
		Database.setRank(p, gr[0]
				);
    	}else {
    		registerPlayer(p.getName());
    	}
		}
	}
	
	  public static boolean PlayerExists(String name) {
		    boolean is = false;
		    
		    @SuppressWarnings("deprecation")
			OfflinePlayer p = Bukkit.getOfflinePlayer(name);
		    UUID uuid = p.getUniqueId();
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Players WHERE uuid = '" + uuid + "'");
		    try {
		      if (rs.next()) {
		        is = true;
		      }
		    } catch (SQLException sQLException) {}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return is;
		  }
		  
		  public static void registerPlayer(String name) {
			    @SuppressWarnings("deprecation")
				OfflinePlayer target = Bukkit.getOfflinePlayer(name);
			    UUID uuid = target.getUniqueId();
			    MySql.onUpdate("INSERT INTO "+MySql.prefix+"Players(name, uuid) VALUES('" + name + "', '" + uuid.toString() + "')");
			  }
		  
	public static void loop() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player p: Bukkit.getOnlinePlayers())
					sync(p);
			}
		}.runTaskTimerAsynchronously(Main.getInstance, 0L, 20L);
	}

}
