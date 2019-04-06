package me.xbones.reportplus.bungee.commands;


import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.bungee.chatcomponentapi.ChatComponentMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ReportPlusCommand extends net.md_5.bungee.api.plugin.Command {
    private ReportPlus main;

    public ReportPlusCommand(ReportPlus main) {
        super("reportplus", "", "rp");
    this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args){
        if (args.length == 0) {
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cReportPlus &6by &aBonesJones"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&cVersion: &6" + main.getDescription().getVersion()));
            sender.sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cCommands:"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Here are the commands with description"));
            sender.sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/reload &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Reload the plugin & reports"));
            sender.sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/closereport &b<id> <Message> &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Close the report with the specified ID and send a message (/closereport {id} {msg}"));
            sender.sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/report &b<player> <Reason> &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Reports the specified player with a reason (/report {name} {reason}"));
            sender.sendMessage(message.getComponent());

        } else {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("reportplus.reload")) {
                        main.reloadPluginConfig();
                        if (main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
                            main.setReportsList(main.getSqlManager().getReports());
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aPlugin reloaded!")));
                    } else {
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                                main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("No-Permission"))));

                    }
                }
            }
        }
    }
}
