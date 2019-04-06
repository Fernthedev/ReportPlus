package me.xbones.reportplus.api;

public class Report {

    private int id;
    private String reporter;
    private String reported;
    private String content;
    private String date;
    private ReportType type;
    private String server;

    public Report(int id, String reporter, String reported, String content, ReportType type, String server) {
        this.id = id;
        this.reporter = reporter;
        this.reported=reported;
        this.content = content;
        this.type=type;
        this.server=server;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReportContent() {
        return content;
    }

    public int getReportId() {
        return id;
    }

    public String getReported() {
        return reported;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ReportType getType() {
        return type;
    }

    public String getServer() {
        return server;
    }
}
