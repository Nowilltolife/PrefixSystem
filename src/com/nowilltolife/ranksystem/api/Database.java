package com.nowilltolife.ranksystem.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.nowilltolife.ranksystem.util.MySql;

public class Database {
	
	public static HashMap<Player, String> disguises = new HashMap<Player, String>();
	
	public static String getPrefix(Player p) {
		String prefix = "Test";
		ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + getRank(p) + "'");
		try {
			if(rs.next()) {
				prefix = rs.getString("prefix");
			}
		}catch (SQLException e) {			
		}
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix;
	}
	
	public static String getColor(Player p) {
		String color = "Test";
		ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + getRank(p) + "'");
		try {
			if(rs.next()) {
				color = rs.getString("color");
			}
		}catch (SQLException e) {
			
		}
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return color;
	}
	
	 public static List<String> getAllRanks() {
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks");
		    List<String> list = new ArrayList<String>();
		    try {
		      while (rs.next()) {
		        list.add(rs.getString("name"));
		      }
		    } catch (SQLException sQLException) {}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return list;
		  }
		  
	public static String getRank(Player p) {
		    String uuid = p.getUniqueId().toString();
		    String rank = "Error";
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Players WHERE uuid = '" + uuid + "'");
		    try {
		      if (rs.next()) {
		        rank = rs.getString("rank");
		      }
		    } catch (SQLException sQLException) {}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return rank;
		  }
	
	public static String getMeta(String key, String rank) {
	    String prefix = "Error";
	    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Meta WHERE (rank = '" + rank + "' AND meta = '" +key+"')");
	    try {
	      if (rs.next()) {
	        prefix = rs.getString("value");
	      }
	    } catch (SQLException sQLException) {}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return prefix;
	  }
	
	public static String getPlayerMeta(String key, Player p) {
	    String prefix = "Error";
	    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"PlayerMeta WHERE (uuid = '" + p.getUniqueId() + "' AND meta = '" +key+"')");
	    try {
	      if (rs.next()) {
	        prefix = rs.getString("value");
	      }
	    } catch (SQLException sQLException) {}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return prefix;
	  }
	
	 public static List<Map<String, String>> getAllMetadata(String rank) {
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Meta WHERE rank = '" + rank + "'");
		    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		    try {
		      while (rs.next()) {
		        Map<String, String> map = new HashMap<String, String>();
		        map.put(rs.getString("meta"), rs.getString("value"));
		        list.add(map);
		      }
		    } catch (SQLException sQLException) {}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return list;
		  }
	 
	 public static List<Map<String, String>> getAllPlayerMetadata(Player p) {
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"PlayerMeta WHERE uuid = '" + p.getUniqueId() + "'");
		    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		    try {
		      while (rs.next()) {
		        Map<String, String> map = new HashMap<String, String>();
		        map.put(rs.getString("meta"), rs.getString("value"));
		        list.add(map);
		      }
		    } catch (SQLException sQLException) {}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return list;
		  }
		  
	
	public static void setRank(Player p, String rank) {
	    String uuid = p.getUniqueId().toString();
	    MySql.onUpdate("UPDATE "+MySql.prefix+"Players SET rank = '" + rank + "' WHERE uuid LIKE '" + uuid + "'");
	  }
		  
	public static String getPrefix(String rank) {
		    String prefix = "Error";
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + rank + "'");
		    try {
		      if (rs.next()) {
		        prefix = rs.getString("prefix");
		      }
		    } catch (SQLException sQLException) {}

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return prefix;
		  }
		  
	public static String getColor(String rank) {
		    String color = "Error";
		    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + rank + "'");
		    try {
		      if (rs.next()) {
		        color = rs.getString("color");
		      }
		    } catch (SQLException sQLException) {}

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return color;
		  }
		  
	public static String getName(String rank) {
			    String name = "Error";
			    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + rank + "'");
			    try {
			      if (rs.next()) {
			        name = rs.getString("displayname");
			      }
			    } catch (SQLException sQLException) {}

				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			    return name;
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
	
	public static boolean KeyExists(String key, String rank) {
		boolean is = false;
		ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Meta WHERE (rank = '" + rank + "' AND meta = '" +key+"')");
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

	public static String getName(Player player) {
	    String name = "Error";
	    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + Database.getRank(player) + "'");
	    try {
	      if (rs.next()) {
	        name = rs.getString("displayname");
	      }
	    } catch (SQLException sQLException) {}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return name;

	}
	
	public static String getSuffix(Player player) {
	    String name = "Error";
	    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + Database.getRank(player) + "'");
	    try {
	      if (rs.next()) {
	        name = rs.getString("suffix");
	      }
	    } catch (SQLException sQLException) {}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return name;

	}
	
	public static String getSuffix(String rank) {
	    String name = "Error";
	    ResultSet rs = MySql.onQuery("SELECT * FROM "+MySql.prefix+"Ranks WHERE name = '" + rank + "'");
	    try {
	      if (rs.next()) {
	        name = rs.getString("suffix");
	      }
	    } catch (SQLException sQLException) {}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return name;

	}

}
