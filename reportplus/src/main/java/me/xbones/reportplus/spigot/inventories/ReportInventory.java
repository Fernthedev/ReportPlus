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

public class ReportInventory {

    private Material glassMaterial;
    private short durability;
    private Inventory myInventory;

    public void initializeReports(Player p) {
        glassMaterial = Material.LEGACY_STAINED_GLASS_PANE;
        durability = 15;
        myInventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cRep&7ort"));
        createDisplay(glassMaterial, myInventory, 2, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 6, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 10, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 16, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 18, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        if(!p.hasPermission("reportplus.listreports" )) {
            createDisplay(Material.BOOK, myInventory, 22, ChatColor.RED + "List Reports!", ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this"));
        } else {
            createDisplay(Material.BOOK, myInventory, 22, ChatColor.RED + "List Reports!", ChatColor.GREEN + "View the current open reports!");
        }
        createDisplay(glassMaterial, myInventory, 26, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 27, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);

        if(!p.hasPermission("reportplus.use")) {
            createDisplay(Material.GRASS, myInventory, 30, ChatColor.translateAlternateColorCodes('&', "&6Report to online staff"), ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this"));
            createDisplay(Material.ENDER_EYE, myInventory, 32, ChatColor.translateAlternateColorCodes('&', "&6Report through Discord"), ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this"));
            createDisplay(Material.ENDER_PEARL, myInventory, 32, ChatColor.translateAlternateColorCodes('&', "&6Report through Discord and Minecraft"), ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this"));


        }else {
            createDisplay(Material.GRASS, myInventory, 30, ChatColor.translateAlternateColorCodes('&', "&6Report to online staff"), ChatColor.translateAlternateColorCodes('&', "&7Report to online staff!"));
            createDisplay(Material.ENDER_EYE, myInventory, 32, ChatColor.translateAlternateColorCodes('&', "&6Report through Discord"), ChatColor.translateAlternateColorCodes('&', "&7Report to discord!"));
            createDisplay(Material.ENDER_PEARL, myInventory, 40, ChatColor.translateAlternateColorCodes('&', "&6Report through Discord and Minecraft"), ChatColor.translateAlternateColorCodes('&', "&7Report to discord and minecaft in the same time!"));

        }
        createDisplay(glassMaterial, myInventory, 35, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 37, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 43, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 47, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
        createDisplay(glassMaterial, myInventory, 51, ChatColor.RED + "REPORT!", ChatColor.DARK_RED + "REPORT ANYTHING!", durability);
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
