package me.xbones.reportplus.core.universalcommands;


import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.gson.LangConfig;

import java.util.List;

public class CloseReportCMD extends UniversalCommand {
	private IReportPlus main;

	public CloseReportCMD(IReportPlus main) {
		super("closereport");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("reportplus.closereport")) {
			LangConfig lang = main.getLangConfig().getConfigData();
			if(sender instanceof IFPlayer<?>) {
				IFPlayer<?> p = (IFPlayer<?>) sender;
				if(args.length < 2){
					p.sendMessage(new TextMessage(translate(
							main.getPrefix() + " " + main.getStringFromMessages("Not-Enough-Args"))));
				}else{
					String reportID = args[0];
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++){
						sb.append(args[i]).append(" ");
					}

					String Message = sb.toString().trim();
Report r = perform(main.getReports(), Integer.parseInt(reportID));

					if(r == null){
						p.sendMessage(new TextMessage(translate(main.getPrefix() + " " + lang.getReportCouldNotBeFound())));
						return;
					}

					main.closeReport(p.getName(), r,false, Message);
					p.sendMessage(new TextMessage(translate(main.getPrefix() +" " + main.getStringFromMessages("Success-Close-Report").replace("%id%",reportID))));
				}
			}
		} else {
			sender.sendMessage(new TextMessage(translate(
					main.getPrefix() + " " + main.getStringFromMessages("No-Permission"))));
		}
	}
	public Report perform(final List<Report> list, final int name){
		return list.stream().filter(o -> o.getReportId() == name).findAny().get();
	}

	private String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
