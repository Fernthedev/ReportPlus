package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AddAnnouncementCommand extends Command {
	
	private Core main;

	public AddAnnouncementCommand(Core main) {
        super(main, "addannounce");
        this.main = main;
	}


	@Override
	public void execute(MessageReceivedEvent event) {

        Member member = event.getGuild().getMember(event.getAuthor());
            if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                event.getChannel().sendMessage("You are not allowed to use this command!").queue();
                return;
            }

            String[] args = event.getMessage().getContentRaw().split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }

            String announcement = sb.toString().trim();
            main.getReportPlus().addAnnouncement(announcement);

            event.getChannel().sendMessage("```Announcement " + announcement + " has been added!```").queue();
    }

  }
