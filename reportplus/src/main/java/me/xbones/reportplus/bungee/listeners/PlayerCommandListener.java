package me.xbones.reportplus.bungee.listeners;

import me.xbones.reportplus.bungee.ReportPlus;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerCommandListener implements Listener {

    private Core main;
    private ReportPlus rp;
    public PlayerCommandListener(Core main, ReportPlus rp) {this.main=main;this.rp=rp;
    }

    @EventHandler
    public void onCommandSent(net.md_5.bungee.api.event.ChatEvent e){
        if(e.getMessage().startsWith("/")){

            if ((boolean) ConfigurationManager.get("Enabled-Modules.Command-log.Enabled")) {

                    for(String s : rp.getConfig().getStringList("Enabled-Modules.Command-log.Dont-Log")){
                    if (e.getMessage().startsWith("/" + s)) {
                        return;
                    }
                }

                    if(e.getSender() instanceof ProxiedPlayer)
                    {
                        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
                        String newText = rp.getUtils().getLangConfig().getConfigData().getCommandlogFormat().replace("%server%", main.getReportPlus().getServerName(new RPlayer(main, player.getName(),player.getUniqueId()))).replace("%player%", player.getName()).replace("%cmd%", e.getMessage());
                        main.getJda().getTextChannelById(rp.getCMDChannelID()).sendMessage(newText).queue();
                    }

            }
        }
    }
}
