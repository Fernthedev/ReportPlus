package me.xbones.reportplus.api;

public class ReportPlusAPI {

    private static ReportPlusAPI instance = null;

    public static ReportPlusAPI getInstance() {
        return instance == null ? instance = new ReportPlusAPI() : instance;
    }

    private IAPIHandler api;

    public void setup(IAPIHandler reportPlusAPI) {
        api = reportPlusAPI;
    }

    public static IAPIHandler getApi() {
        return getInstance().api;
    }
}
