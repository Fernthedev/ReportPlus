package me.xbones.reportplus.spigot;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LogAppender extends AbstractAppender {

    private Core core;

    public LogAppender(Core main) {
        super("MyLogAppender", null, null);
        this.core=main;
        start();
    }

    @Override
    public void append(LogEvent event) {

        String message = event.getMessage().getFormattedMessage();

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("HH:mm:ss");
        message = "[" +formatter.format(new Date(System.currentTimeMillis())) + " " + event.getLevel().toString() + "] " + message;
        if(core.getJda() != null)
        Objects.requireNonNull(core.getJda().getTextChannelById((String) ConfigurationManager.get("Console-Channel-ID")), "Cannot find console channel based on ID. Make sure it's typed correctly").sendMessage(ChatColor.stripColor(message)).queue();
    }

}
