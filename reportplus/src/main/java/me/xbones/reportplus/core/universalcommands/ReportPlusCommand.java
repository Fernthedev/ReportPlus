package me.xbones.reportplus.core.universalcommands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.fernthedev.config.common.exceptions.ConfigLoadException;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.chatcomponentapi.UniversalChatComponentMessage;

@CommandAlias("reportplus|rp")
public class ReportPlusCommand extends BaseCommand {
    private IReportPlus main;

    public ReportPlusCommand(IReportPlus main) {
        this.main = main;
    }

    @Default
    public void execute(FernCommandIssuer sender, String[] args){
        if (args.length == 0) {
            UniversalChatComponentMessage message = new UniversalChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cReportPlus &6by &aBonesJones & Fernthedev"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&cVersion: &6" + main.getVersion()));
            sender.sendMessage(message.getComponent());

            message = new UniversalChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cCommands:"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Here are the commands with description"));
            sender.sendMessage(message.getComponent());

            message = new UniversalChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/reload &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Reload the plugin & reports"));
            sender.sendMessage(message.getComponent());

            message = new UniversalChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/closereport &b<id> <Message> &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Close the report with the specified ID and send a message (/closereport {id} {msg}"));
            sender.sendMessage(message.getComponent());

            message = new UniversalChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/report &b<player> <Reason> &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Reports the specified player with a reason (/report {name} {reason}"));
            sender.sendMessage(message.getComponent());

        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("reportplus.reload")) {
                    main.reloadPluginConfig();
                    try {
                        main.getLangConfig().load();
                    } catch (ConfigLoadException e) {
                        e.printStackTrace();
                        throw new InvalidCommandArgument("Unable to reload: " + e.getLocalizedMessage());
                    }
                    if (main.getBooleanFromConfig("Enabled-Modules.MySQL.Enabled"))
                        main.setReportsList(main.getSqlManager().getReports());
                    sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aPlugin reloaded!")));
                } else {
                    sender.sendMessage(new TextMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getPrefix() + " " + main.getStringFromMessages("No-Permission"))));

                }
            }
        }
    }
}
