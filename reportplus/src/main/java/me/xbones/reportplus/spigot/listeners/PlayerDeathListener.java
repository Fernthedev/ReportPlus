package me.xbones.reportplus.spigot.listeners;

import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private ReportPlus main;

    public PlayerDeathListener(ReportPlus main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(main.getConfig().getBoolean("Enabled-Modules.Death-log.Enabled")){
            String id= main.getConfig().getString("Enabled-Modules.Death-log.Channel-ID");
            main.getCore().getJda().getTextChannelById(id).sendMessage(event.getDeathMessage()).queue();
        }
    }

}
