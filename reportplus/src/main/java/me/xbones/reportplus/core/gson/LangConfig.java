package me.xbones.reportplus.core.gson;

import lombok.Data;

import java.util.Arrays;
import java.util.List;


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
    private String reportID = "ReportID";
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


    private String reportClosedMessage = "&cYour report with the id %id% has been closed!";
    private String minecraftReportTitleMessageTitle = "&aNew Report!";
    private String minecraftReportTitleMessageSubtitle = "&e%player% has made a new report";
    private String discordReportTitleMessageTitle = "&aNew Report! Check your Discord!";
    private String discordReportTitleMessageSubtitle = "&e%player% has made a new report";
    private String minecraftChatFormat = "[%server%] %player% > %message%";
    private String commandlogFormat = "[%server%] %player% > %cmd%";

    private String discordChatFormat = "&7[Discord] &aUser %user% > %message%";
    private String buttonClickMessage = "&6What would you like to report about this player?";
    private String discordJoinMessage = ":heavy_plus_sign: Player %player% has joined the server!";
    private String discordLeaveMessage = ":heavy_minus_sign: Player %player% has left the server!";
    private String serverStartMessage = ":white_check_mark: Server has started!";
    private String serverStopMessage = ":skull_crossbones: Server has stopped!";

    private DiscordReportEmbed discordReportEmbed = new DiscordReportEmbed();

    @Data
    public static class DiscordReportEmbed {
        private String title = "New report";
        private String description = "You have received a new report! Information:";
        private String reporter = "%reporter%";
        private String reported = "%reported%";
        private String server = "%server%";
        private String reportID = "%reportid%";
        private String reportContent = "%reportcontent%";
    }



    private String successReport = "&aSucessfully reported! &cYour report id is &b#%id%'";
    private String enterMessage = "&aPlease enter the message to send.";
    private String messageNotificationFormat = "&c%sender% &7> &a%message%";

    private List<String> minecraftReportMessage = Arrays.asList(
            " ",
            "&8&m          I         ",
            " ",
            "          &6&lNew &c&lReport!         ",
            " ",
            "          &6Reporter: &c%reporter%          ",
            " ",
            "          &6Reported: &c%reported%          ",
            " ",
            "          &6Server: &c%server%          ",
            " ",
            "          &6Reason: &c%reportcontent%          ",
            " ",
            "&8&m          I         "
    );

    private List<String> minecraftReportReceiveFormat = Arrays.asList(
            "&8&m          I         ",
            " ",
            "          &6&lReport &c&lClosed!         ",
            " ",
            "          &6Closer: &c%player%          ",
            " ",
            "          &6Report ID: &c%id%",
            " ",
            "          &6Report reason: &c%reason%",
            " ",
            "&8&m          I         "
    );

    private String chatSyncBannedWord = "&c&lDo not ping everyone!";
    private String notEnoughArgs = "&cNot enough arguments! Use /rp for help!";
    private String cantReportSelf = "&c&lYou cannot report yourself!";
    private String successCloseReport = "&aSuccessfully closed report &c#%id%";


    private String enterName = "&c&lPlease enter the name of the player!";
}
