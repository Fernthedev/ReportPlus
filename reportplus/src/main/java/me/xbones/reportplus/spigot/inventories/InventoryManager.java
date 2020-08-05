package me.xbones.reportplus.spigot.inventories;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private ReportPlus main;
    private ReportInventory myInventory;
    private ListReportsInventory reportsList;
    private final Map<Report, CloseReportInventoy> closeReportInventory = new HashMap<>();
    private Inventory customCloseReportInventory;

    public InventoryManager(ReportPlus main) {
        this.main =  main;
    }

    public void initializeList() {
        ListReportsInventory inventory = new ListReportsInventory(main);
        inventory.InitializeList();
        reportsList = inventory;
    }

    public void initializeReports(Player p) {
        ReportInventory inventory = new ReportInventory(main);
        inventory.initializeReports(p);
        myInventory = inventory;
    }

    public void initializeCloseReportInventory(Report r){
        CloseReportInventoy inv = new CloseReportInventoy(main);
        inv.Initialize(r);
        closeReportInventory.put(r,inv);
    }
    public CloseReportInventoy getCloseReportInventory(Report r) {
        return closeReportInventory.get(r);
    }

    public ListReportsInventory getReportsList() {
        return reportsList;
    }

    public ReportInventory getReportInventory() {
        return myInventory;
    }

    public Inventory getCustomCloseReportInventory() {
        return customCloseReportInventory;
    }

    public void setCustomCloseReportInventory(Inventory customCloseReportInventory) {
        this.customCloseReportInventory = customCloseReportInventory;
    }


    public Map<Report, CloseReportInventoy> getCloseReportInventories() {
        return closeReportInventory;
    }
}
