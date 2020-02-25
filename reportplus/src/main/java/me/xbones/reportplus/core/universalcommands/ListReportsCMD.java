package me.xbones.reportplus.core.universalcommands;


import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;

public class ListReportsCMD extends UniversalCommand {
	private IReportPlus main;
	public ListReportsCMD(IReportPlus main) {
		super("reports");
		this.main = main;
	}
	@Override
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("reportplus.listreports")) {
			if(sender instanceof IFPlayer<?>) {
				IFPlayer<?> p = (IFPlayer<?>) sender;

				if(args.length == 0) {
					main.listReports(p, 1);
				}else{
					int i = Integer.parseInt(args[0]);
					main.listReports(p,i);
				}
			}
		} else {
			sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&',
					main.getPrefix() + " " + main.getStringFromMessages("No-Permission"))));
		}
	}

}
