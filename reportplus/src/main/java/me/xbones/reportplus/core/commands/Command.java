package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class Command implements EventListener {

    private String name;
    private Core core;

    Command(Core core, String name){
        this.core=core;
        this.name=name;
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof MessageReceivedEvent){
            MessageReceivedEvent e = (MessageReceivedEvent)event;

            if(e.getMessage().getContentRaw().toLowerCase().startsWith(core.getCommandPrefix().toLowerCase() + name)){
                execute(e);
            }
        }
    }


    public void execute(MessageReceivedEvent event){

    }
}
