package me.xbones.reportplus.core.universalcommands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.entity.Player;

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

				if (Universal.getMethods().getServerType().isProxy()) {
					if (args.length == 0) {
						main.listReports(p, 1);
					} else {
						int i = Integer.parseInt(args[0]);
						main.listReports(p, i);

					}
				} else {
					if (!(main instanceof ReportPlus)) throw new IllegalStateException("Report plus on non-proxy state is running a non-spigot instance");

					ReportPlus spigotMain = (ReportPlus) main;


					Universal.debug("Opening reports GUI for " + p.getName());


					spigotMain.getRPInventoryManager().initializeList();
					((Player) p.getPlayer()).openInventory(spigotMain.getRPInventoryManager().getReportsList().getInventory());
				}
			}
		} else {
			sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&',
					main.getPrefix() + " " + main.getStringFromMessages("No-Permission"))));
		}
	}

}
