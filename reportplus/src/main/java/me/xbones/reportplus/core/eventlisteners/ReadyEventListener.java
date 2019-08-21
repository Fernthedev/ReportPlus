package me.xbones.reportplus.core.eventlisteners;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.core.Utils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

public class ReadyEventListener implements EventListener {

    private Core core;
    public ReadyEventListener(Core core){
this.core=core;
    }

    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if(event instanceof ReadyEvent){
            ReadyEvent e = (ReadyEvent) event;

            core.getReportPlus().log(Utils.CCT("&c     &aBOT LOADED.    "));
        }
    }

}
