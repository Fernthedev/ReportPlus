package me.xbones.reportplus.spigot.listeners;

import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    private ReportPlus main;

    public PlayerJoinListener(ReportPlus main){
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(main.getConfig().getBoolean("Enabled-Modules.Log-leave-and-join")) {
            main.getCore().getJda().getTextChannelById(main.getMCChannelID())
                    .sendMessage(main.getUtils().getLanguageConfig().getConfigData().getDiscordJoinMessage().replace("%player%", p.getName())).queue();
        }

        if(main.getConfig().getStringList("User-Notifications." + p.getName()) != null){
            List<String> notifications = main.getConfig().getStringList("User-Notifications." + p.getName());

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
                for(String s: notifications){
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                }
            });

            main.getConfig().set("User-Notifications." + p.getName(), null);
            main.saveConfig();
            main.reloadPluginConfig();
        }
    }
}
