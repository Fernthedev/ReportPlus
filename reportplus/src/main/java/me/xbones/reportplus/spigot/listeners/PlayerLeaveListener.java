package me.xbones.reportplus.spigot.listeners;

import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private ReportPlus main;

    public PlayerLeaveListener(ReportPlus main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(main.getConfig().getBoolean("Enabled-Modules.Log-leave-and-join")) {
            main.getCore().getJda().getTextChannelById(main.getMCChannelID())
                    .sendMessage(main.getUtils().getMessagesConfig().getString("Discord-Leave-Message").replace("%player%", p.getName())).queue();
        }
    }
}
