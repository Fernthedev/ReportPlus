package me.xbones.reportplus.spigot.commands;


import me.xbones.reportplus.spigot.ReportPlus;
import me.xbones.reportplus.spigot.chatcomponentapi.ChatComponentMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportPlusCommand implements CommandExecutor {
    private ReportPlus main;

    public ReportPlusCommand(ReportPlus main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command name, String label, String[] args) {
        if (args.length == 0) {
            ChatComponentMessage message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cReportPlus &6by &aBonesJones"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&cVersion: &6" + main.getDescription().getVersion()));
            sender.spigot().sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cCommands:"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Here are the commands with description"));
            sender.spigot().sendMessage(message.getComponent());

            message = new ChatComponentMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &c/reload &7(Hover for information)"));
            message.addHover(ChatColor.translateAlternateColorCodes('&', "&7Reload the plugin & reports"));
            sender.spigot().sendMessage(message.getComponent());

        } else {
            if (args.length >= 1) {/*
				if(args[0].equalsIgnoreCase("report")){
				    if(args.length == 1){
				        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + "&cPlease enter a bug to report!"));
				        return true;
                    }
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++){
						sb.append(args[i]).append(" ");
					}

					String reportMessage = sb.toString().trim();

					main.getReportPlusBot().getTextChannelById("444809697409761281").get().sendMessage(new EmbedBuilder()
							.setTitle("New bug report!")
							.setDescription("You have received a new bug report!")
							.setColor(new Color(16711680))
							.setFooter("Report+ by xBones! Hope you like it!", "https://www.spigotmc.org/data/resource_icons/50/50455.jpg?1512679915")
							.setAuthor("Report+ by xBones", "https://www.spigotmc.org/resources/report-gui-%E2%98%86-discord-%E2%98%86-customizable-%E2%98%86-titles-%E2%98%86-discord-announcements-%E2%98%86-and-more.50455/", main.getConfig().getString("Server Icon Link"))
							.addField("**Reporter**", sender.getName(), false)
									.addField("**Server Name**", Bukkit.getServerName(), false)
									.addField("**Server IP**", Bukkit.getIp(), false)
							.addField("**Report**", reportMessage, false)
							);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aSuccess!"));

				} */
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("reportplus.reload")) {
                        main.reloadPluginConfig();
                        if (main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
                            main.setReportsList(main.getSqlManager().getReports());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &aPlugin reloaded!"));
                    } else {
                        sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',
                            main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("No-Permission")));

                    }
                }
            }
        }
        return true;
    }
}
