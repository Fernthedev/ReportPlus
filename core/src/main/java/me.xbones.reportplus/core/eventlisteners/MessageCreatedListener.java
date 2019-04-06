package me.xbones.reportplus.core.eventlisteners;

import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.HashMap;

public class MessageCreatedListener implements EventListener {

    private Core core;
    private HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    public MessageCreatedListener(Core core){
        this.core=core;
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof MessageReceivedEvent){
            MessageReceivedEvent e = (MessageReceivedEvent)event;
            String id = e.getChannel().getId();
            if (id.equals(core.getReportPlus().getMCChannelID())) {
                if ((boolean)ConfigurationManager.get("Enabled-Modules.Chat-Sync")) {
                    int cooldownTime = 5;
                    if(cooldowns.containsKey(e.getMessage().getAuthor().getId())) {
                        long secondsLeft = ((cooldowns.get(e.getMessage().getAuthor().getId())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                        if(secondsLeft>0) {
                            // Still cooling down
                            e.getChannel().sendMessage("You cannot use Chat Sync for another " + secondsLeft + " seconds.");
                            return;
                        }
                    }
                    if(e.getMessage().getContentRaw().length() >= Long.valueOf((int)ConfigurationManager.get("Max-Chat-Sync-Characters"))){
                        e.getChannel().sendMessage("Too many characters!");
                        return;
                    }
                    String format = ChatColor
                            .translateAlternateColorCodes('&',
                                    core.getReportPlus().getMessage("Discord-Chat-Format").replace("%user%",
                                            e.getMessage().getAuthor().getName()))
                            .replace("%message%", e.getMessage().getContentRaw())
                            .replace("%id%", e.getMessage().getAuthor().getDiscriminator());
                    if (e.getMessage().getAuthor().isBot()) {
                        return;
                    }
                    core.getReportPlus().broadcast(format);
                    cooldowns.put(e.getMessage().getAuthor().getId(), System.currentTimeMillis());
                }
            }
            if((boolean)ConfigurationManager.get("Enabled-Modules.Console")) {
                if (id.equals((String)ConfigurationManager.get("Console-Channel-ID"))){
                    if (e.getMessage().getAuthor().isBot()) {
                        return;
                    }
                    core.getReportPlus().dispatchCommand(e.getMessage().getContentRaw());
                }
            }

        }
    }
}
