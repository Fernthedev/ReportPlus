package me.xbones.reportplus.spigot.inventories;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
import me.xbones.reportplus.spigot.ReportPlus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
this.report=report;
this.name = ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId());
        reportInv = Bukkit.createInventory(null, 54, name);
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 1, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 2, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 3, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 4, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 5, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 6, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 7, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 9, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 17, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_WOOL, reportInv, 20, ChatColor.translateAlternateColorCodes('&', "&cClose Report & Send message"), ChatColor.translateAlternateColorCodes('&',"&7Close permanently and send a message to owner."), (short)14);
        createDisplay(Material.LEGACY_WOOL, reportInv, 24, ChatColor.translateAlternateColorCodes('&', "&aCancel"), ChatColor.translateAlternateColorCodes('&',"&7Click to cancel and go back to the previous menu."), (short)5);
        createDisplay(Material.BARRIER, reportInv, 22, ChatColor.translateAlternateColorCodes('&', "&cClose Report"), ChatColor.translateAlternateColorCodes('&',"&7Close permanently without sending a message."));
        List<String>lore=new ArrayList<>();
        lore.add(ChatColor.GREEN + "Reporter: " + report.getReporter());
        lore.add(ChatColor.RED +"Report id: " + report.getReportId());
        lore.add(ChatColor.AQUA +"Report: " + report.getReportContent());
        if(report.getType() == ReportType.DISCORD)
            lore.add(ChatColor.GRAY + "Report Type: Discord");
        else if(report.getType() == ReportType.MINECRAFT)
            lore.add(ChatColor.GRAY + "Report Type: Minecraft");
        else if(report.getType() == ReportType.BOTH)
            lore.add(ChatColor.GRAY + "Report Type: Discord and Minecaft");
        lore.add(ChatColor.BLUE +"Date: " + report.getDate());
        createDisplay(Material.BOOK, reportInv, 31, ChatColor.translateAlternateColorCodes('&', "&7Report &b#" + report.getReportId()), lore);
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 36, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 44, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 46, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 47, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 48, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 49, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 50, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 51, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 52, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");
//
//        createDisplay(Material.LEGACY_STAINED_GLASS_PANE, reportInv, 54, ChatColor.translateAlternateColorCodes('&', "&cReport &b#" + report.getReportId()), " ");

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
}
