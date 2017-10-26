package me.jazzbro.moneybar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	
	public static Economy econ = null;
	
	public void onEnable() {
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		getLogger().info("MoneyBar enabled");
		getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public void onDisable() {
		getLogger().info("MoneyBar disabled");
	}
	
	private static Main INSTANCE;
	  
	   public Main() {
	      INSTANCE = this;
	   }
	  
	   public static Main getInstance() {
	      return INSTANCE;
	   }
	   
	   private boolean setupEconomy() {
		   if (getServer().getPluginManager().getPlugin("Vault") == null) {
		   return false;
		   }
		   RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		   if (rsp == null) {
		   return false;
		   }
		   econ = rsp.getProvider();
		   return econ != null;
		   }
	   
	   @EventHandler
	   public void onMove(PlayerMoveEvent e) {
		   Player p = e.getPlayer();
		   
		   Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			   @Override
			   public void run() {
				   String m = ChatColor.AQUA + "Wallet: " + ChatColor.GREEN + "$" + econ.getBalance(p);
				   HotbarMessager.sendHotBarMessage(p, m);
		   
			
				}
			}, 0, 20 * 1);
	   
	   }

}
