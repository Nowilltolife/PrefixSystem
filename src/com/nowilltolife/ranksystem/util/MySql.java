package com.nowilltolife.ranksystem.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import com.nowilltolife.ranksystem.Main;

public class MySql {
  public static Connection connection;
  private static String ip;
  private static int port;
  private static String database;
  private static String password;
  private static String username;
  public static String prefix;
  
  public static void onConnect(String database, String ip, String username, String password, int port, String prefix) {
	  MySql.ip = ip;
	  MySql.port = port;
	  MySql.username = username;
	  MySql.password = password;
	  MySql.database = database;
	  MySql.prefix = prefix;
	  String type = Main.getInstance.getConfig().getString("StorageMethode");
	  if(type.equals("mysql")) {
    try {
      connection = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+database+"?autoReconnect=true", 
          username, password);
      onCreate();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database-Connection could not be established");
      System.err.println("Database Deactivated");
    }
	  }else if(type.equals("sqlite")) {
		    try {
		        File dataFolder = new File(Main.getInstance.getDataFolder(), Main.getInstance.getConfig().getString("Sqlite.filename") + ".db");
		        if (!dataFolder.exists()){
		            try {
		                dataFolder.createNewFile();
		            } catch (IOException e) {
		                Main.getInstance.getLogger().log(Level.SEVERE, "File write error: "+ Main.getInstance.getConfig().getString("Sqlite.filename") + ".db");
		            }
		        }
		        connection = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance.getDataFolder() + "/" + Main.getInstance.getConfig().getString("Sqlite.filename") + ".db");
		        onCreate2();
		      } catch (SQLException e) {
		        e.printStackTrace();
		        System.err.println("Database-Connection could not be established");
		        System.err.println("Database Deactivated");
		      }
	  }
  }
  public static void onDisconect() {
    if (connection != null)
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Database-Connection could not be ended");
      }  
  }
  
  public static void onUpdate(String qry) {
    try {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(qry);
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public static void onCreate() {
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Ranks (id mediumint(9) NOT NULL AUTO_INCREMENT, name varchar(255) DEFAULT NULL, prefix varchar(255) DEFAULT '', suffix varchar(255) DEFAULT '', color varchar(255) DEFAULT '', displayname varchar(255) DEFAULT '', PRIMARY KEY (id))");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Players (id mediumint(9) NOT NULL AUTO_INCREMENT, name varchar(255) DEFAULT NULL, uuid varchar(50) DEFAULT NULL, rank varchar(255) DEFAULT NULL, prefix varchar(255) DEFAULT '', suffix varchar(255) DEFAULT '', color varchar(255) DEFAULT '', PRIMARY KEY (id))");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Meta (rank varchar(255) DEFAULT NULL, meta varchar(255) DEFAULT '', value varchar(255) DEFAULT '')");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"PlayerMeta (uuid varchar(255) DEFAULT NULL, meta varchar(255) DEFAULT '', value varchar(255) DEFAULT '')");
  }
  
  public static void onCreate2() {
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Ranks (id INTEGER PRIMARY KEY AUTOINCREMENT, name char(255) DEFAULT NULL, prefix char(255) DEFAULT '', suffix char(255) DEFAULT '', color char(255) DEFAULT '', displayname char(255) DEFAULT '')");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Players (id INTEGER PRIMARY KEY AUTOINCREMENT, name char(255) DEFAULT NULL, uuid char(50) DEFAULT NULL, rank char(255) DEFAULT NULL, prefix char(255) DEFAULT '', suffix char(255) DEFAULT '', color char(255) DEFAULT '')");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"Meta (rank char(255) DEFAULT NULL, meta char(255) DEFAULT '', value char(255) DEFAULT '')");
	  MySql.onUpdate("CREATE TABLE IF NOT EXISTS "+prefix+"PlayerMeta (uuid varchar(255) DEFAULT NULL, meta varchar(255) DEFAULT '', value varchar(255) DEFAULT '')");
  }
  
  public static ResultSet onQuery(String qry) {
    ResultSet rs = null;
    try {
      Statement stmt = connection.createStatement();
      rs = stmt.executeQuery(qry);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return rs;
  }
  
  public static void onReconnect() {
      onDisconect();
      onConnect(database, ip, username, password, port, prefix);
  }
}
