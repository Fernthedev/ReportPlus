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
public class ConfigInventory {

    @NonNull
    private IReportPlus reportPlus;
    
    private Material glassMaterial;
    private short durability;
    private Inventory myInventory;

    public void initializeConfig(Player p) {
        LangConfig lang = reportPlus.getLangConfig().getGsonConfigData();
        glassMaterial = Material.LEGACY_STAINED_GLASS_PANE;
        durability = 15;
        myInventory = Bukkit.createInventory(null, 54, translate( lang.getGuiGeneralTitle()));
        createDisplay(Material.LEGACY_WOOL, myInventory,  1, translate( lang.getConfigInventoryEnableDisableReporting()), translate(lang.getConfigInventoryEnableDisableReportingLore()),(short)5);
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

    public Inventory getInventory() {
        return myInventory;
    }
}
