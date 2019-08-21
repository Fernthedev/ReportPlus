package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DelAnnouncementCommand extends Command {

	private Core main;

	public DelAnnouncementCommand(Core main) {
		super(main,"delannouncement");
		this.main = main;
	}

	@Override
	public void execute(MessageReceivedEvent event) {

		if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			event.getChannel().sendMessage("You are not allowed to use this command!").queue();
			return;
		}
		String[] args = event.getMessage().getContentRaw().split(" ");


		if (args.length == 0) {
			event.getChannel().sendMessage("```Please enter id!```").queue();
			return;
		}

		int id = Integer.valueOf(args[1]);
		event.getChannel().sendMessage("```Announcement " + main.getReportPlus().getMessages().get(id - 1) + " has been removed!```").queue();
		main.getReportPlus().deleteAnnouncement(id);
	}
}

