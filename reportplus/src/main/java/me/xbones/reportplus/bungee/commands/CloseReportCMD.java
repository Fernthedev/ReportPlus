package me.xbones.reportplus.bungee.commands;


import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.core.gson.LangConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class CloseReportCMD extends Command {
	private ReportPlus main;
	public CloseReportCMD(ReportPlus main) {
		super("closereport");
		this.main = main;
	}
	@Override
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("reportplus.closereport")) {
			if(sender instanceof ProxiedPlayer) {
				ProxiedPlayer p = (ProxiedPlayer) sender;
				if(args.length < 2){
					p.sendMessage(new TextComponent(translate(
							main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Not-Enough-Args"))));
				}else{
					String reportID = args[0];
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++){
						sb.append(args[i]).append(" ");
					}

					LangConfig lang = main.getLangConfig().getConfigData();
					
					String Message = sb.toString().trim();
Report r = perform(main.getReports(), Integer.parseInt(reportID));
					if(r == null){
						p.sendMessage(new TextComponent(translate(main.getPrefix() + lang.getReportCouldNotBeFound())));
						return;
					}

					main.closeReport(p.getName(), r,false, Message);
					p.sendMessage(new TextComponent(translate(main.getPrefix() +" " + main.getUtils().getMessagesConfig().getString("Success-Close-Report").replace("%id%",reportID))));
				}
			}
		} else {
			sender.sendMessage(new TextComponent(translate(
					main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("No-Permission"))));
		}
	}

	private String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public Report perform(final List<Report> list, final int name){
		return list.stream().filter(o -> o.getReportId() == name).findAny().get();
	}

}
