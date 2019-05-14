package me.xbones.reportplus.spigot.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConfigInventory {

    private Material glassMaterial;
    private short durability;
    private Inventory myInventory;

    public void initializeConfig(Player p) {
        glassMaterial = Material.LEGACY_STAINED_GLASS_PANE;
        durability = 15;
        myInventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cRep&7ort"));
        createDisplay(Material.LEGACY_WOOL, myInventory,  1, ChatColor.translateAlternateColorCodes('&', "&cReporting"), ChatColor.GRAY + "Enable/Disable Reporting",(short)5);
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

    public Inventory getInventory() {
        return myInventory;
    }
}
