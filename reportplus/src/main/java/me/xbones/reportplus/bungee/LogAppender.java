package me.xbones.reportplus.bungee;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class LogAppender extends java.util.logging.Handler {

     private Core main;

     public LogAppender(Core main) {

         this.main = main;
     }

     @Override
     public void flush() {
     }

     @Override
     public void close() throws SecurityException {
     }

     @Override
     public void publish(LogRecord record) {

         if(ReportPlus.isDisable()) return;

         String message = record.getMessage();

         if (getFormatter() != null) message = getFormatter().formatMessage(record);

         SimpleDateFormat formatter;
         formatter = new SimpleDateFormat("HH:mm:ss");
         message = "[" + formatter.format(new Date(record.getMillis())) + " " + record.getLevel().toString() + "] " + message;
         if (main.getJda() != null)
             main.getJda().getTextChannelById((String) ConfigurationManager.get("Console-Channel-ID")).sendMessage(ChatColor.stripColor(message)).queue();

     }

 }
