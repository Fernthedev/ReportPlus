package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CustomCMD extends Command {

    private Core main;
private String CMD;
    public CustomCMD(Core main, String CMD) {
        super(main,CMD);
        this.main = main;
        this.CMD=CMD;
    }

    @Override
    public void execute(MessageReceivedEvent commandEvent) {
        commandEvent.getChannel().sendMessage((String) ConfigurationManager.get("Cmds." + CMD + ".say")).queue();
        main.getReportPlus().dispatchCommand((String) ConfigurationManager.get("Cmds." + CMD + ".targetcmd"));
    }
}
