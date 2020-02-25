package me.xbones.reportplus.bungee.commands;

import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.core.gson.LangConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;

public class ReportCommand extends Command {

    private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    private ReportPlus main;
    public ReportCommand(ReportPlus main){
        super("report");
        this.main=main;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        LangConfig lang = main.getLangConfig().getConfigData();
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if (main.getConfig().getBoolean("Enabled-Modules.Reporting")) {
                if (p.hasPermission("reportplus.use")) {

                    // COOLDOWN //
                    int cooldownTime = main.getConfig().getInt("Command-cooldown"); // Get number of seconds from wherever you want
                    if(cooldowns.containsKey(p.getName())) {
                        long secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                        if(secondsLeft>0) {
                            // Still cooling down
                            p.sendMessage(new TextComponent(translate( main.getPrefix() + " &cYou cannot use this command for another " + secondsLeft + " seconds.")));
                            return;
                        }
                    }
                    // No cooldown found or cooldown has expired, save new cooldown
                    cooldowns.put(p.getName(), System.currentTimeMillis());

                    // COOLDOWN END //

                    if(args.length < 2) {
                            p.sendMessage(new TextComponent(translate( main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Not-Enough-Args"))));

                    } else {

                        ProxiedPlayer target = main.getProxy().getPlayer(args[0]);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++){
                            sb.append(args[i]).append(" ");
                        }

                        String Message = sb.toString().trim();

                        if(target == null)
                            p.sendMessage(new TextComponent(translate( main.getPrefix() + " " + lang.getPlayerCannotBeFound())));
                        else {
                            if(target == p && !p.hasPermission("reportplus.reportSelf"))
                            {
                                p.sendMessage(new TextComponent(translate( main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Cant-Report-Self"))));
                                return;
                            }
                            main.getCore().reportToBoth(new RPlayer(main.getCore(), p.getName(),p.getUniqueId()), target.getName(), Message);
                        }
                    }

                } else {
                    main.NoPerm(p);
                }
            } else {
                p.sendMessage(new TextComponent(translate( main.getPrefix() + " " + lang.getReportingIsDisabled())));
            }
        } else {
            commandSender.sendMessage(new TextComponent(translate(lang.getCanOnlyRunIngame())));
        }
    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
