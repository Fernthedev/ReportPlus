package me.xbones.reportplus.core.commands;
import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ListAnnouncementsCommand extends Command {
	
	private Core main;

	public ListAnnouncementsCommand(Core main) {
	    super(main,"listannouncements");
		this.main = main;
	}

    @Override
    public void execute(MessageReceivedEvent event) {

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage("You are not allowed to use this command!").queue();
            return;
        }
        event.getChannel().sendMessage("```Announcements:```");
        int count = 1;
        for (String s : main.getReportPlus().getMessages()) {
            event.getChannel().sendMessage("```" + count + " - " + s + "```").queue();
            count++;
        }
    }

  }
