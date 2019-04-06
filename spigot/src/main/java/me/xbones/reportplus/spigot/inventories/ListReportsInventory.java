package me.xbones.reportplus.spigot.inventories;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.api.ReportType;
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
        reportsList = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cRep&7ort&4s"));
        int slot =0;
        for(Report p : main.getReportsList()) {
            if(slot == 54) return;
            List<String>lore=new ArrayList<>();
            lore.add(ChatColor.GREEN + "Reporter: " + p.getReporter());
            lore.add(ChatColor.RED +"Report id: " + p.getReportId());
            lore.add(ChatColor.AQUA +"Report: " + p.getReportContent());
            if(p.getType() == ReportType.DISCORD)
                lore.add(ChatColor.GRAY + "Report Type: Discord");
            else if(p.getType() == ReportType.MINECRAFT)
                lore.add(ChatColor.GRAY + "Report Type: Minecraft");
            else if(p.getType() == ReportType.BOTH)
                lore.add(ChatColor.GRAY + "Report Type: Discord and Minecaft");
            lore.add(ChatColor.BLUE +"Date: " + p.getDate());
            lore.add(ChatColor.GOLD + "Click to show details.");
            createDisplay(Material.BOOK_AND_QUILL, reportsList, slot, ChatColor.GREEN + p.getReporter(), lore);
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
}
