package me.xbones.reportplus.api;

import me.xbones.reportplus.api.punishments.Punishment;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.List;

public interface IAPIHandler {

    void reportToDiscord(IRPlayer reporter, String reported, String content);

    void reportToMinecraft(IRPlayer reporter, String reported, String content);

    void reportToBoth(IRPlayer reporter, String reported, String content);

    List<Report> getReports();

    /*
    public void setReportClickInventory(Inventory customInv){
        main.getInventoryManager().setCustomCloseReportInventory(customInv);
    }
*/

    boolean sendPunishment(Punishment punishment);

    void sendMessageToChannel(String channelID, String message);

    void sendMessageToChannel(String channelID, EmbedBuilder embed);

}
