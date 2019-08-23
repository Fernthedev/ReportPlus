package me.xbones.reportplus.spigot.inventories;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.gson.LangConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReportInventory {

    @NonNull
    private IReportPlus reportPlus;

    private Material glassMaterial;
    private short durability;
    private Inventory myInventory;

    public void initializeReports(Player p) {
        LangConfig lang = reportPlus.getLangConfig().getGsonConfigData();
        glassMaterial = Material.LEGACY_STAINED_GLASS_PANE;
        durability = 15;
        myInventory = Bukkit.createInventory(null, 54, translate( lang.getGuiGeneralTitle()));
        createDisplay(glassMaterial, myInventory, 2, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 6, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 10, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 16, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 18, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        if(!p.hasPermission("reportplus.listreports" )) {
            createDisplay(Material.BOOK, myInventory, 22, translate(lang.getListReports()), translate(lang.getNoPerm()));
        } else {
            createDisplay(Material.BOOK, myInventory, 22, translate(lang.getListReports()), translate(lang.getHasPermToList()));
        }
        createDisplay(glassMaterial, myInventory, 26, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 27, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);

        if(!p.hasPermission("reportplus.use")) {
            createDisplay(Material.GRASS, myInventory, 30, translate(lang.getReportToStaff()), translate(lang.getNoPerm()));
            createDisplay(Material.ENDER_EYE, myInventory, 32, translate(lang.getReportToDiscord()), translate(lang.getNoPerm()));
            createDisplay(Material.ENDER_PEARL, myInventory, 32, translate(lang.getReportToBoth()), translate(lang.getNoPerm()));


        }else {
            createDisplay(Material.GRASS, myInventory, 30, translate(lang.getReportToStaff()), translate(lang.getReportToStaff()));
            createDisplay(Material.ENDER_EYE, myInventory, 32, translate(lang.getReportToDiscord()), translate(lang.getReportToDiscord()));
            createDisplay(Material.ENDER_PEARL, myInventory, 40, translate(lang.getReportToBoth()), translate(lang.getReportToBoth()));

        }
        createDisplay(glassMaterial, myInventory, 35, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 37, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 43, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 47, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
        createDisplay(glassMaterial, myInventory, 51, translate(lang.getGlassMatMessage()), translate(lang.getGlassMatLore()), durability);
    }

    public void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
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

    public Inventory getInventory() {
        return myInventory;
    }
}
