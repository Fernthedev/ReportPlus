package me.xbones.reportplus.core.universalcommands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;

@CommandAlias("reports")
public class ListReportsCMD extends BaseCommand {
	private IReportPlus main;
	public ListReportsCMD(IReportPlus main) {
		this.main = main;
	}

	@Default
	public void execute(FernCommandIssuer sender, String[] args){
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
