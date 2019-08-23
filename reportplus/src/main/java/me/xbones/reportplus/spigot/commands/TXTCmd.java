package me.xbones.reportplus.spigot.commands;


import me.xbones.reportplus.core.gson.LangConfig;
import me.xbones.reportplus.spigot.ReportPlus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TXTCmd implements CommandExecutor {

	private ReportPlus main;

	public TXTCmd(ReportPlus main) {
		this.main = main;
	}
    @Override
    public boolean onCommand(CommandSender sender, Command name, String lable, String[] args) {
		LangConfig lang = main.getLangConfig().getGsonConfigData();
    	if(sender.hasPermission("reportplus.addtxtcmd")) {
    		
    		if(args.length < 2) {
    			sender.sendMessage(translate( main.getPrefix() + " " + lang.getPleaseEnterCommandText()));
    			
    		} else {
    			String cmd = args[0];
    			StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++){
				sb.append(args[i]).append(" ");
				}
				 
				String Text = sb.toString().trim();
				main.AddTextCMD(sender, cmd, Text);
    		}
    	} else {
    		sender.sendMessage(translate( main.getPrefix() + " " + lang.getNoPerm()));
    	}
		return true;
    }

	private String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}
