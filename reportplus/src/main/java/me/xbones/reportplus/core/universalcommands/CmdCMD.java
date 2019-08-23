package me.xbones.reportplus.core.universalcommands;


import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.gson.LangConfig;


public class CmdCMD extends UniversalCommand {

    private IReportPlus main;

    public CmdCMD(IReportPlus main) {
        super("cmdcmd");
        this.main = main;
    }


    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender.hasPermission("reportplus.addcmdcmd")) {
            LangConfig lang = main.getLangConfig().getGsonConfigData();
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
