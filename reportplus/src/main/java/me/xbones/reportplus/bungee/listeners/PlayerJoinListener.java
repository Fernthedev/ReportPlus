package me.xbones.reportplus.bungee.listeners;

import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerJoinListener implements Listener {

    private Core main;
    private ReportPlus rp;

    public PlayerJoinListener(Core main, ReportPlus rp){this.main=main;this.rp=rp;}

    @EventHandler
    public void PlayerJoined(PlayerHandshakeEvent e){
        if(!(e.getConnection() instanceof ProxiedPlayer)) return;
        ProxiedPlayer p = (ProxiedPlayer)e.getConnection();

        if((boolean)ConfigurationManager.get("Enabled-Modules.Log-leave-and-join")) {
            main.getJda().getTextChannelById(main.getReportPlus().getMCChannelID())
                    .sendMessage(rp.getUtils().getMessagesConfig().getString("Discord-Join-Message").replace("%player%", p.getName())).queue();
        }

        if(rp.getConfig().getStringList("User-Notifications." + p.getName()) != null){
            List<String> notifications =rp.getConfig().getStringList("User-Notifications." + p.getName());

           rp.getProxy().getScheduler().schedule(rp, new Runnable(){
                public void run(){
                    for(String s: notifications){
                        p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', s)));
                    }
                }
            }, 2, TimeUnit.SECONDS);

            rp.getConfig().set("User-Notifications." + p.getName(), null);
            rp.saveConfig();
            rp.reloadPluginConfig();
        }
    }
}
