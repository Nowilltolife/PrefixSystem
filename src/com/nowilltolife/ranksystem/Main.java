package com.nowilltolife.ranksystem;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.nowilltolife.ranksystem.commands.CommandPlayer;
import com.nowilltolife.ranksystem.commands.CommandRank;
import com.nowilltolife.ranksystem.hooks.PlaceholderApi;
import com.nowilltolife.ranksystem.hooks.VaultHook;
import com.nowilltolife.ranksystem.util.MySql;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;;

public class Main extends JavaPlugin implements Listener {
  public static Main getInstance = null;
  private static Economy econ = null;
  private static Permission perms = null;
  private static Chat chat = null;
  public void onEnable() {
    getInstance = this;
    getCommand("prefixsystem").setExecutor((CommandExecutor)new CommandRank());
    getCommand("prefixplayer").setExecutor((CommandExecutor)new CommandPlayer());
    if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
    (new PlaceholderApi()).register();
    saveDefaultConfig();
    if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
    setupPermissions();
    setupChat();
    }
    String ip = getConfig().getString("MySql.ip");
    int port = getConfig().getInt("MySql.port");
    String database = getConfig().getString("MySql.database");
    String username = getConfig().getString("MySql.username");
    String password = getConfig().getString("MySql.password");
    String prefix = getConfig().getString("MySql.table-prefix");
    MySql.onConnect(database, ip, username, password, port, prefix);
    saveDefaultConfig();
    if(MySql.connection == null) {
    	getLogger().fine("Error whilst connection to database. Are the credentials correct?");
    	Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(getName()));
    }
    if(getConfig().getBoolean("hooks.vault") == true)
    	if(Bukkit.getPluginManager().getPlugin("Vault") != null)
    	VaultHook.loop();
  }
  
  public static Economy getEconomy() {
      return econ;
  }
  
  public static Permission getPermissions() {
      return perms;
  }
  
  public static Chat getChat() {
      return chat;
  }
  
  private boolean setupChat() {
      RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
      chat = rsp.getProvider();
      return chat != null;
  }
  
  private boolean setupPermissions() {
      RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
      perms = rsp.getProvider();
      return perms != null;
  }

}