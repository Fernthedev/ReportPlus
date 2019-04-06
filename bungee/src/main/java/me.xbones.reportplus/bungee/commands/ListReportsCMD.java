package me.xbones.reportplus.bungee.commands;


import me.xbones.reportplus.bungee.ReportPlus;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ListReportsCMD extends Command {
	private ReportPlus main;
	public ListReportsCMD(ReportPlus main) {
		super("reports");
		this.main = main;
	}
	@Override
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("reportplus.listreports")) {
			if(sender instanceof ProxiedPlayer) {
				ProxiedPlayer p = (ProxiedPlayer) sender;

				if(args.length == 0) {
					main.listReports(p, 1);
				}else{
					int i = Integer.parseInt(args[0]);
					main.listReports(p,i);
				}
			}
		} else {
			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
					main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("No-Permission"))));
		}
	}

}
