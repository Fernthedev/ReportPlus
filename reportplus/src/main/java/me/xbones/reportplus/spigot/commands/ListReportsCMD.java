package me.xbones.reportplus.spigot.commands;


import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListReportsCMD implements CommandExecutor {
	private ReportPlus main;
	public ListReportsCMD(ReportPlus main) {
		this.main = main;
	}
    @Override
    public boolean onCommand(CommandSender sender, Command name, String lable, String[] args) {
    	if(sender.hasPermission("reportplus.listreports")) {
    		if(sender instanceof Player) {
    				Player p = (Player)sender;
    				main.getRPInventoryManager().initializeList();
    				p.openInventory(main.getRPInventoryManager().getReportsList());
    		}
    	} else {
			sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',
					main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("No-Permission")));
    	}
		return true;
    }
}
