package me.xbones.reportplus.spigot.listeners;

import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.spigot.ReportPlus;
import me.xbones.reportplus.spigot.SpigotUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerChatListener implements Listener {

    private ReportPlus main;

    public PlayerChatListener(ReportPlus main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        boolean PlayerIsInMCMode = main.getMinecraftChosen().contains(e.getPlayer().getName());
        boolean PlayerInDiscordMode = main.getDiscordChosen().contains(e.getPlayer().getName());
        boolean PlayerInBothMode = main.getBothChosen().contains(e.getPlayer().getName());
        Player p = e.getPlayer();
        String reported = "Not Specified";
        if(main.getReporting().containsKey(p))
            reported = main.getReporting().get(p).getName();

        if (PlayerIsInMCMode) {
            main.getCore().reportToStaff(new RPlayer(main.getCore(), p.getName(), p.getUniqueId()), reported, e.getMessage());

            main.getMinecraftChosen().remove(p.getName());

            if (main.getReporting().containsKey(p))
                main.getReporting().remove(p);
            e.setCancelled(true);
            return;
        } else if (PlayerInDiscordMode) {

                main.getCore().reportToDiscord(new RPlayer(main.getCore(), p.getName(), p.getUniqueId()), reported, e.getMessage());
            main.getDiscordChosen().remove(p.getName());
            if (main.getReporting().containsKey(p))
                main.getReporting().remove(p);
            e.setCancelled(true);
            return;
        } else if(PlayerInBothMode){
                main.getCore().reportToBoth(new RPlayer(main.getCore(), p.getName(), p.getUniqueId()), reported, e.getMessage());
            main.getBothChosen().remove(p.getName());
            if(main.getReporting().containsKey(p)) main.getReporting().remove(p);
            e.setCancelled(true);
            return;
        }else if(main.getSendingMessage().contains(p.getName())){
            Report selectedReport = main.getSelectedReports().get(p.getName());
            Player reportOwner = Bukkit.getPlayer(selectedReport.getReporter());
            if(reportOwner != null){
                reportOwner.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", e.getPlayer().getName()).replace("%message%", e.getMessage())));
            }else{
                List<String> messages = new ArrayList<>();
                messages.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Message-Notification-Format").replace("%sender%", e.getPlayer().getName()).replace("%message%", e.getMessage())));
                main.getConfig().set("User-Notifications." + p.getName(), messages);
            }
            main.getSendingMessage().remove(p.getName());
            e.setCancelled(true);
            main.getInventoryClickListener().CloseReport(e.getPlayer(), selectedReport);
            main.getSelectedReports().remove(p.getName());
            return;
        }

        if (main.getConfig().getBoolean("Enabled-Modules.Chat-Sync")) {
            String prefix = "[Player]";

            if(main.getConfig().getString("Chat-Sync-Rank-Value").equalsIgnoreCase("GROUP_NAME")) {
                try {

                    if (main.getPermission().isEnabled() && main.getPermission().hasGroupSupport()) {

                        String group = main.getPermission().getPrimaryGroup(p);
                        if (group != null) {
                            prefix = SpigotUtils.removeColorCodes(group);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("--------");
                    System.out.println("No permissions plugin. ignoring");
                    System.out.println("--------");
                }
            }

            else if(main.getConfig().getString("Chat-Sync-Rank-Value").equalsIgnoreCase("PREFIX")){
                try {

                    if (main.getChat().isEnabled()) {

                        String[] groups = main.getChat().getPlayerGroups(p);
                        String group = main.getChat().getGroupPrefix(p.getWorld(), groups[0]);
                        if (group != null) {
                            prefix = SpigotUtils.removeColorCodes(group);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("--------");
                    System.out.println("No chat plugin. ignoring");
                    System.out.println("--------");
                }
            }

            String message = main.getUtils().getMessagesConfig().getString("Minecraft-Chat-Format").replace("%player%", p.getName()).replace("%message%", e.getMessage()).replace("%rank%", prefix);
            for(String bannedWord : main.getConfig().getStringList("Chat-Sync-Banned-Words")){
                if ((message.toLowerCase().contains(bannedWord.toLowerCase()))){
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Chat-Sync-Banned-Word")));
                    return;
                }
            }

            main.getCore().getJda().getTextChannelById(main.getMCChannelID())
                    .sendMessage(message).queue();
        }

    }
}
