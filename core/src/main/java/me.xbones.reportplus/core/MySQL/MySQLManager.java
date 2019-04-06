package me.xbones.reportplus.core.MySQL;

import me.xbones.reportplus.core.Core;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.configuration.ConfigurationManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager {

    private Core core;

    private String host, database, username, password;
    private int port;

    public MySQLManager(Core core) {
        this.core = core;

        if ((boolean)ConfigurationManager.get("Enabled-Modules.MySQL.Enabled")) {
            try {
                host = (String)ConfigurationManager.get("Enabled-Modules.MySQL.Host");
                port = (int)ConfigurationManager.get("Enabled-Modules.MySQL.Port");
                database = (String)ConfigurationManager.get("Enabled-Modules.MySQL.Database");
                username = (String)ConfigurationManager.get("Enabled-Modules.MySQL.Username");
                password = (String)ConfigurationManager.get("Enabled-Modules.MySQL.Password");
                new MySQL(core.getReportPlus(),host,database,username,password,String.valueOf(port));
                MySQL.connect();

                createTable();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable(){
        try {
            MySQL.createTable("reports"," id INT PRIMARY KEY," +
                    " content LONGTEXT," +
                    " reporter TEXT," +
                    " reported TEXT," +
                    " type TEXT, " +
                    " date TEXT, " +
                    " server TEXT" );


            if(!MySQL.hasColumn("reports", "id"))
                MySQL.addColumn("reports", "id INT PRIMARY KEY");

            if(!MySQL.hasColumn("reports", "content"))
                MySQL.addColumn("reports", "content LONGTEXT");

            if(!MySQL.hasColumn("reports", "reporter"))
                MySQL.addColumn("reports", "reporter TEXT");

            if(!MySQL.hasColumn("reports", "reported"))
                MySQL.addColumn("reports", "reported TEXT");

            if(!MySQL.hasColumn("reports", "type"))
                MySQL.addColumn("reports", "type TEXT");

            if(!MySQL.hasColumn("reports", "date"))
                MySQL.addColumn("reports", "date TEXT");

            if(!MySQL.hasColumn("reports", "server"))
                MySQL.addColumn("reports", "server TEXT");

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addReportToDatabase(Report report){
        try {
            if (!(boolean)ConfigurationManager.get("Enabled-Modules.MySQL.Enabled")) return;

            MySQL.insertData("id, content, reporter, reported, type, date, server",
                    "'" + report.getReportId() + "'" +
                            " , '" + report.getReportContent() + "'" +
                            " , '" + report.getReporter() + "'" +
                            " , '" + report.getReported() + "'" +
                            " , '" + report.getType().toString() + "'" +
                            " , '" + report.getDate() + "'" +
                            " , '" + report.getServer() + "'",
                    "reports");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeReportFromDatabase(Report report){
        try{
            if (!(boolean)ConfigurationManager.get("Enabled-Modules.MySQL.Enabled")) return;

            MySQL.deleteData("id", "=", String.valueOf(report.getReportId()), "reports");
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Report> getReports(){
        List<Report> reports = new ArrayList<>();

        try {
            ResultSet rs = MySQL.query("SELECT * from reports");
            while(rs.next())
            {
                Report report = new Report(
                        rs.getInt("id"),
                        rs.getString("reporter"),
                        rs.getString("reported"),
                        rs.getString("content"),
                        ReportType.valueOf(rs.getString("type")),
                        rs.getString("server"));
                report.setDate(rs.getString("date"));
                reports.add(report);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return reports;
    }

}
