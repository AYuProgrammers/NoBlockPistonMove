package pl.AYuPro.nbpm;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NBPM  extends JavaPlugin implements Listener{
	Plugin nbpm = this;
	public static List<Integer> blist;
	
	public void onEnable(){
		nbpm.getConfig().options().copyDefaults(true);
		nbpm.saveDefaultConfig();
		blist = this.getConfig().getIntegerList("itemid");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("nbpmlist")) {
			if (!(sender.isOp())){
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return false;
			} else {
				sender.sendMessage(blist.toString());
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void BlockMoveEvent1(BlockPistonExtendEvent event) {
		for (Block gb: event.getBlocks()){
			if (blist.contains(gb.getTypeId())){
				event.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void BlockMoveEvent2(BlockPistonRetractEvent event) {
		if (blist.contains(event.getRetractLocation().getBlock().getTypeId())){
			event.setCancelled(true);
		}
	}
}
