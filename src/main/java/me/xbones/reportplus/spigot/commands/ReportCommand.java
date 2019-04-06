package me.xbones.reportplus.spigot.commands;


import com.github.fernthedev.fernapi.universal.UUIDFetcher;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ReportCommand implements CommandExecutor {
	private ReportPlus main;
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public ReportCommand(ReportPlus main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command name, String lable, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (main.getConfig().getBoolean("Enabled-Modules.Reporting")) {
				if ((p.hasPermission("reportplus.use") || p.isOp())
						|| main.getConfig().getBoolean("Allow-No-Permission-to-open-GUI") ) {

					// COOLDOWN //
					int cooldownTime = main.getConfig().getInt("Command-cooldown"); // Get number of seconds from wherever you want
					if(cooldowns.containsKey(sender.getName())) {
						long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
						if(secondsLeft>0) {
							// Still cooling down
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cYou cannot use this command for another " + secondsLeft + " seconds."));
							return true;
						}
					}
					// No cooldown found or cooldown has expired, save new cooldown
					cooldowns.put(sender.getName(), System.currentTimeMillis());

					// COOLDOWN END //

					if(args.length == 0) {
						if(!main.getConfig().getBoolean("Allow-Report-No-Name")){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Enter-Name")));
							return true;
						}
					main.showGUI(p);

					} else {

						Player target = Bukkit.getPlayer(args[0]);
	                    if(target == null)
	                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cThat player is not online!"));
	                    else {
	                    	if(target == p)
							{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Cant-Report-Self")));
								return true;
							}
	                        main.getReporting().put(p,target);
	    					main.showGUI(p);
	                    }
					}

				} else {
					main.NoPerm(p);	
				}
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cReporting is disabled!"));
			}
		} else {
			sender.sendMessage("Can only be run in game!");
		}
		return true;
	}
}
