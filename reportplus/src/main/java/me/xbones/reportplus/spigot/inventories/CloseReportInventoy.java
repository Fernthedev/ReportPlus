package me.xbones.reportplus.spigot.inventories;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.core.gson.LangConfig;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CloseReportInventoy {

    private ReportPlus main;
    private Inventory reportInv;
    private Report report;
    private String name;

    public CloseReportInventoy(ReportPlus main) { this.main = main; }

    public void Initialize(Report report) {
        LangConfig lang = main.getLangConfig().getConfigData();
        this.report=report;
        this.name = translate( lang.getReportTextClose().replace("%id%", report.getReportId() + ""));
        reportInv = Bukkit.createInventory(null, 54, name);
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 1, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 2, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 3, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 4, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 5, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 6, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 7, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 9, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 17, lang.getReportTextClose().replace("%id%", report.getReportId() + ""), " ");
        createDisplay(Material.LEGACY_WOOL, reportInv, 20, translate(lang.getReportMessageOnCloseAndMessage()), translate(lang.getReportMessageOnCloseAndMessageDesc()), (short)14);
        createDisplay(Material.LEGACY_WOOL, reportInv, 24, translate( lang.getReportCancel()), translate(lang.getReportCancelDesc()), (short)5);
        createDisplay(Material.BARRIER, reportInv, 22, translate( lang.getReportMessageOnCloseNoMessage()), translate(lang.getReportMessageOnCloseNoMessageDesc()));
        List<String> lore = new ArrayList<>();
        LangConfig.ReportDesc reportDesc = lang.getReportDescription();
        lore.add(translate(reportDesc.getReporter().replace("%reporter%", report.getReporter())));
        lore.add(translate(reportDesc.getReporter().replace("%id%", report.getReportId() + "")));
        lore.add(translate(reportDesc.getReportContent().replace("%content%", report.getReportContent())));



        if(report.getType() == ReportType.DISCORD)
            lore.add(translate(reportDesc.getReportTypeDiscord()));
        else if(report.getType() == ReportType.MINECRAFT)
            lore.add(translate(reportDesc.getReportTypeMinecraft()));
        else if(report.getType() == ReportType.BOTH)
            lore.add(translate(reportDesc.getReportTypeBoth()));

        lore.add(translate(reportDesc.getDate().replace("%date%", report.getDate() + "")));
        createDisplay(Material.BOOK, reportInv, 31, translate(lang.getReportTextClose().replace("%id%", report.getReportId() + "")), lore);
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 36, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 44, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 46, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 47, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 48, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 49, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 50, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 51, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 52, translate( lang.getReportTextClose().replace("%id%", report.getReportId() + "")), " ");
//
//        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 54, translate( "&cReport &b#" + report.getReportId()), " ");

    }

    public String getName() {
        return name;
    }

    public Report getReport() {
        return report;
    }

    public Inventory getInventory() {
        return reportInv;
    }

    public void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        meta.setLore(Lore);
        item.setItemMeta(meta);

        inv.setItem(Slot, item);

    }
    public void createDisplay(Material material, Inventory inv, int Slot, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        inv.setItem(Slot, item);

    }
    public void createDisplay(Material material, Inventory inv, int Slot, String name, String lore, short durability) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        meta.setLore(Lore);
        item.setItemMeta(meta);
        item.setDurability(durability);

        inv.setItem(Slot, item);

    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
