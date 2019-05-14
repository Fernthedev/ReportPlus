package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.configuration.ConfigurationManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CustomTXTCMD extends Command {

    private Core main;
    private String CMD;
    public CustomTXTCMD(Core main, String CMD) {
        super(main,CMD);
        this.main = main;
        this.CMD=CMD;
    }


    @Override
    public void execute(MessageReceivedEvent commandEvent) {
        commandEvent.getChannel().sendMessage((String)ConfigurationManager.get("TXTCmds." + CMD + ".text")).queue();

    }
}
