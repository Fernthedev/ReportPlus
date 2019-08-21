package me.xbones.reportplus.core.universalcommands;

import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;


public class TXTCmd extends UniversalCommand {

	private IReportPlus main;

	public TXTCmd(IReportPlus main) {
		super("txtcmd");
		this.main = main;
	}


	@Override
	public void execute(CommandSender sender, String[] args) {
    	if(sender.hasPermission("reportplus.addtxtcmd")) {
    		if(args.length < 2) {
    			sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cPlease enter a command name and text!")));
    			
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
    		sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cYou don't have access to that command!")));
    	}
    }
}
