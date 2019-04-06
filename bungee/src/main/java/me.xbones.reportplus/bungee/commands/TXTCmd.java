package me.xbones.reportplus.bungee.commands;

import me.xbones.reportplus.bungee.ReportPlus;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class TXTCmd extends Command {

	private ReportPlus main;

	public TXTCmd(ReportPlus main) {
		super("txtcmd");
		this.main = main;
	}


	@Override
	public void execute(CommandSender sender, String[] args) {
    	if(sender.hasPermission("reportplus.addtxtcmd")) {
    		if(args.length < 2) {
    			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cPlease enter a command name and text!")));
    			
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
    		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cYou don't have access to that command!")));
    	}
    }
}
