package me.xbones.reportplus.bungee.listeners;

import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLeaveListener implements Listener {

    private Core main;
    private ReportPlus rp;

    public PlayerLeaveListener(Core main, ReportPlus rp){this.main=main;this.rp=rp;}

    @EventHandler
    public void PlayerLeft(PlayerDisconnectEvent e){
        ProxiedPlayer p = e.getPlayer();

        if((boolean)ConfigurationManager.get("Enabled-Modules.Log-leave-and-join")) {
            main.getJda().getTextChannelById(main.getReportPlus().getMCChannelID())
                    .sendMessage(rp.getUtils().getMessagesConfig().getString("Discord-Leave-Message").replace("%player%", p.getName())).queue();
        }
    }
}
