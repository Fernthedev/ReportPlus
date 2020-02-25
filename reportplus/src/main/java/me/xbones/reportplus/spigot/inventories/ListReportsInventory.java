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

public class ListReportsInventory {

    private ReportPlus main;
    private Inventory reportsList;

    public ListReportsInventory(ReportPlus main) { this.main = main; }

    public void InitializeList() {
        LangConfig lang = main.getLangConfig().getConfigData();
        reportsList = Bukkit.createInventory(null, 54, translate( lang.getGuiGeneralTitle()));
        int slot =0;
        for(Report p : main.getReportsList()) {
            if(slot == 54) return;
            List<String>lore=new ArrayList<>();
            LangConfig.ReportDesc reportDesc = lang.getReportDescription();
            lore.add(translate(reportDesc.getReporter().replace("%reporter%", p.getReporter())));
            lore.add(translate(reportDesc.getReporter().replace("%id%", p.getReportId() + "")));
            lore.add(translate(reportDesc.getReportContent().replace("%content%", p.getReportContent())));
            if(p.getType() == ReportType.DISCORD)
                lore.add(translate(reportDesc.getReportTypeDiscord()));
            else if(p.getType() == ReportType.MINECRAFT)
                lore.add(translate(reportDesc.getReportTypeMinecraft()));
            else if(p.getType() == ReportType.BOTH)
                lore.add(translate(reportDesc.getReportTypeBoth()));
            lore.add(translate(reportDesc.getDate().replace("%date%", p.getDate() + "")));
            lore.add(translate(lang.getClickToShowDetails()));
            createDisplay(Material.WRITABLE_BOOK, reportsList, slot, ChatColor.GREEN + p.getReporter(), lore);
            slot++;
        }
    }
    public Inventory getInventory() {
        return reportsList;
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
