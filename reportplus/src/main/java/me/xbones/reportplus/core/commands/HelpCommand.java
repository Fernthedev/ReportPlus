package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

public class HelpCommand extends Command {

    private Core main;

    public HelpCommand(Core main) {
        super(main,"help");
        this.main = main;
    }

    @Override
    public void execute(MessageReceivedEvent event) {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Hello, " + event.getMessage().getAuthor().getName() +"! Welcome to the server " + ConfigurationManager.get("Server-Name"))

                    .setDescription("Here are the commands with description!")
                    .setColor(new Color(16711680))
                    .setTimestamp(Instant.now())
                    .setFooter("Report+ by xBones! Hope you like it!", "https://www.spigotmc.org/data/resource_icons/50/50455.jpg?1512679915")
                    .setThumbnail((String)ConfigurationManager.get("Server-Icon-Link"))
               //     .setImage((String)ConfigurationManager.get("Server-Icon-Link"))
                    .setAuthor("Report+ by xBones", "https://www.spigotmc.org/resources/report-gui-%E2%98%86-discord-%E2%98%86-customizable-%E2%98%86-titles-%E2%98%86-discord-announcements-%E2%98%86-and-more.50455/", (String)ConfigurationManager.get("Server-Icon-Link"))
                    .addField("**help**", "The command that shows all...", false)
                    .addField("**reload**", "Reload the config", false)
                    .addField("**addannounce**", "Add announcement!", false)
                    .addField("**delannouncement**", "Delete an announcement!", false)
                    .addField("**listannouncements**", "List announcements!", false)
                    .addField("**closereport**", "Closes the report with the id specified and sends specified message to report owner!", false);
             main.getReportPlus().addCustomCommandsToEmbed(builder);
        event.getChannel().sendMessage(builder.build()).queue();
    }
}
