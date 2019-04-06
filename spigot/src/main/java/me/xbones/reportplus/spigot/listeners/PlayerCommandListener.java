package me.xbones.reportplus.spigot.listeners;

import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    private ReportPlus main;

    public PlayerCommandListener(ReportPlus main){
        this.main = main;
    }

    @EventHandler
    public void onCommandChat(PlayerCommandPreprocessEvent e) {

        if (main.getConfig().getBoolean("Enabled-Modules.Command-log.Enabled")) {
            boolean PlayerIsInMCMode = main.getMinecraftChosen().contains(e.getPlayer().getName());
            boolean PlayerInDiscordMode = main.getDiscordChosen().contains(e.getPlayer().getName());
            boolean PlayerInBothMode = main.getBothChosen().contains(e.getPlayer().getName());
            for(String s : main.getConfig().getStringList("Enabled-Modules.Command-log.Dont-Log")){
            if (e.getMessage().startsWith("/" + s)) {
                return;
            }}
            if (PlayerIsInMCMode || PlayerInDiscordMode || PlayerInBothMode || main.getSendingMessage().contains(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(
                        ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " &cPlease type in a message!"));
                e.setCancelled(true);
            }
            main.getCore().getJda().getTextChannelById(main.getCMDChannelID()).sendMessage(e.getPlayer().getName() + " -> " + e.getMessage()).queue();

        }



    }

}
