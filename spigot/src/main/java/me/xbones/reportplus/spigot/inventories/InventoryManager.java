package me.xbones.reportplus.spigot.inventories;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private ReportPlus main;
    private Inventory myInventory;
    private Inventory reportsList;
    private Map<Report, CloseReportInventoy> closeReportInventory;
    private Inventory customCloseReportInventory;

    public InventoryManager(ReportPlus main) {
        this.main =  main;
        closeReportInventory = new HashMap<>();
    }

    public void initializeList() {
        ListReportsInventory inventory = new ListReportsInventory(main);
        inventory.InitializeList();
        reportsList = inventory.getInventory();
    }

    public void initializeReports(Player p) {
        ReportInventory inventory = new ReportInventory();
        inventory.initializeReports(p);
        myInventory = inventory.getInventory();
    }

    public void initializeCloseReportInventory(Report r){
        CloseReportInventoy inv = new CloseReportInventoy(main);
        inv.Initialize(r);
        closeReportInventory.put(r,inv);
    }
    public CloseReportInventoy getCloseReportInventory(Report r) {
        return closeReportInventory.get(r);
    }

    public Inventory getReportsList() {
        return reportsList;
    }

    public Inventory getReportInventory() {
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
