package me.xbones.reportplus.core.universalcommands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.gson.LangConfig;

@CommandAlias("cmdcmd")
public class CmdCMD extends BaseCommand {

    private IReportPlus main;

    public CmdCMD(IReportPlus main) {
        this.main = main;
    }

    @Default
    public void execute(FernCommandIssuer sender, String[] args){
        if (sender.hasPermission("reportplus.addcmd")) {
            LangConfig lang = main.getLangConfig().getConfigData();
            if (args.length < 2) {
                sender.sendMessage(new TextMessage(translate( main.getPrefix() + " " + lang.getPleaseEnterCommandText())));

            } else {
                String cmd = args[0];
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }

                String Text = sb.toString().trim();
                main.AddCMDCMD(sender, cmd, Text);
            }
        } else {
            sender.sendMessage(new TextMessage(translate( main.getPrefix() + " &cYou don't have access to that command!")));
        }
    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
