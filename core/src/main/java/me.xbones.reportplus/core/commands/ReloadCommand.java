package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ReloadCommand extends Command {
	
	private Core main;
	public ReloadCommand(Core main) {
	    super(main,"reload");
		this.main = main;
	}
    @Override
    public void execute(MessageReceivedEvent event) {

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage("You are not allowed to use this command!").queue();
            return;
        }

        main.getReportPlus().reloadPluginConfig();
        event.getChannel().sendMessage("Successfully reloaded!").queue();
    }
  }
