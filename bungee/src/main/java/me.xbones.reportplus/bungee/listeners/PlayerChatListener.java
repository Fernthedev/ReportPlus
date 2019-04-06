package me.xbones.reportplus.bungee.listeners;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.dv8tion.jda.core.entities.TextChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    private Core main;
    public PlayerChatListener(Core main) {this.main=main;}

    @EventHandler
    public void onMessageCreated(ChatEvent e){
        String message = e.getMessage();
        if(e.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) e.getSender();

            if ((boolean)ConfigurationManager.get("Enabled-Modules.Chat-Sync")) {
                if(e.getMessage().startsWith("/"))  return;
                TextChannel channel = main.getJda().getTextChannelById((String) ConfigurationManager.get("Discord-MC-Channel-ID"));
                String format = main.getReportPlus().getMessage("Minecraft-Chat-Format");
                String newMessage = format.replace("%player%", player.getName()).replace("%message%", message).replace("%server%", main.getReportPlus().getServerName(new RPlayer(main, player.getName(),player.getUniqueId())));
                channel.sendMessage(newMessage).queue();
            }
        }
    }
}
