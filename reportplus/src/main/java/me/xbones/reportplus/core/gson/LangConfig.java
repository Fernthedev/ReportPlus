package me.xbones.reportplus.core.gson;

import lombok.Data;


@Data
public class LangConfig implements GsonConfigData {


    private String pleaseEnterCommandText = "&cPlease enter a command name and text!";
    private String coolDownText = "&cYou cannot use this command for another %secondsLeft% seconds.";
    private String canOnlyRunIngame = "&cCan only be run in game!";
    private String reportingIsDisabled = "&cReporting is disabled!";
    private String playerCannotBeFound = "&cThat player is not online!";


    private String titleLang = "Title";
    private String descriptionLang = "Description";
    private String reporter = "Reporter";
    private String reported = "Reported";
    private String server = "Server";
    private String reportID = "Report-ID";
    private String reportContent = "Report Content";

    private String guiGeneralTitle = "&cRep&7ort";
    private String glassMatMessage = "&cREPORT!";
    private String glassMatLore = "&4REPORT ANYTHING!";
    private String listReports = "&cList Reports!";
    private String noPerm = "&cYou do not have permission to use this.";
    private String hasPermToList = "&aView the current open reports!";

    private String reportCouldNotBeFound = "&cReport could not be found!";

    private String configInventoryEnableDisableReporting = "&cReporting";
    private String configInventoryEnableDisableReportingLore = "&7Enable/Disable Reporting";


    private String reportTextClose = "&cReport &b#%id%";
    private String clickToShowDetails = "&6Click to show details.";

    private String reportMessageOnCloseAndMessage = "&cClose Report & Send message";
    private String reportMessageOnCloseAndMessageDesc = "&7Close permanently and send a message to owner.";
    private String reportMessageOnCloseNoMessage = "&cClose Report";
    private String reportMessageOnCloseNoMessageDesc = "&7Close permanently without sending a message.";

    private String reportCancel = "&aCancel";
    private String reportCancelDesc = "&7Click to cancel and go back to the previous menu.";

    private ReportDesc reportDescription = new ReportDesc();

    @Data
    public static class ReportDesc {
        private String reporter = "&aReporter: %reporter%";
        private String reportId = "&cReport id: %id%";
        private String reportContent = "&bReport: %content%";

        private String reportTypeDiscord = "&7Report Type: Discord";
        private String reportTypeMinecraft = "&7Report Type: Minecraft";
        private String reportTypeBoth = "&7Report Type: Discord & Minecraft";

        private String date = "&9Date: %date%";
    }

    private String reportToStaff = "&6Report to online staff";
    private String reportToDiscord = "&6Report through Discord";
    private String reportToBoth = "&6Report through Discord and Minecraft";

}
