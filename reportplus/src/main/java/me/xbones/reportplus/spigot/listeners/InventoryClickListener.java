package me.xbones.reportplus.spigot.listeners;

import com.github.fernthedev.fernapi.universal.Universal;
import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryClickListener implements Listener {

    private ReportPlus main;
    private Inventory inventory;
    private ItemStack clicked;

    public InventoryClickListener(ReportPlus main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        clicked = event.getCurrentItem();
        inventory = event.getInventory();
        String reportMsg = main.getUtils().getMessagesConfig().getString("Button-Click-Message");

        Universal.debug("Inventory title: " + event.getView().getTitle());

        Universal.debug("Checking " + main.getRPInventoryManager().getReportInventory().getTitle());
        Universal.debug("Then checking " +  main.getRPInventoryManager().getCloseReportInventory(main.getSelectedReports().get(player.getName())).getName());

        try {
            if (main.getRPInventoryManager().getReportInventory().getInventory() != null
                    && ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(
                    ChatColor.translateAlternateColorCodes('&', main.getRPInventoryManager().getReportInventory().getTitle()))) {


                if (clicked != null) {
                    if (clicked.getType() == Material.LEGACY_STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                        player.closeInventory();
                    } else if (clicked.getType() == Material.ENDER_EYE) {
                        if (player.hasPermission("reportplus.use")) {
                            main.getDiscordChosen().add(player.getName());
                            player.closeInventory();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + reportMsg));

                        } else {
                            event.setCancelled(true);
                        }
                    } else if (clicked.getType() == Material.GRASS) {
                        if (player.hasPermission("reportplus.use")) {
                            main.getMinecraftChosen().add(player.getName());
                            player.closeInventory();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + reportMsg));
                        } else {
                            event.setCancelled(true);
                        }

                    } else if (clicked.getType() == Material.BOOK) {
                        if (player.hasPermission("reportplus.listreports")) {
                            main.getRPInventoryManager().initializeList();
                            event.setCancelled(true);
                            player.openInventory(main.getRPInventoryManager().getReportsList().getInventory());
                        } else {
                            event.setCancelled(true);
                        }
                    } else if (clicked.getType() == Material.ENDER_PEARL) {
                        if (player.hasPermission("reportplus.use")) {
                            main.getBothChosen().add(player.getName());
                            player.closeInventory();
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + reportMsg));
                        }
                    }
                }

            } else if (event.getView().getTitle().equals(
                    main.getRPInventoryManager().getReportsList().getTitle()
            )) {
                if (clicked.getType() == Material.LEGACY_BOOK_AND_QUILL) {
                    List<String> lore;
                    lore = clicked.getItemMeta().getLore();
                    String reporter = lore.get(0);
                    String reportid = lore.get(1);
                    String report = lore.get(2);

                    for (Report r : main.getReportsList()) {
                        if (reporter.equals(ChatColor.GREEN + "Reporter: " + r.getReporter())) {
                            if (reportid.equals(ChatColor.RED + "Report id: " + r.getReportId())) {
                                if (report.equals(ChatColor.AQUA + "Report: " + r.getReportContent())) {
                                    main.getSelectedReports().put(player.getName(), r);

                                    main.getRPInventoryManager().initializeCloseReportInventory(r);
                                    event.setCancelled(true);
                                    player.openInventory(main.getRPInventoryManager().getCloseReportInventory(r).getInventory());

                                }
                            }
                        }
                    }
                }
            } else if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle())
                    .equals(ChatColor.translateAlternateColorCodes('&', main.getRPInventoryManager().getCloseReportInventory(main.getSelectedReports().get(player.getName())).getName()))) {

                Report r = main.getSelectedReports().get(player.getName());
                if (clicked.getType() == Material.LEGACY_WOOL && clicked.getDurability() == (short) 14) {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Enter-Message")));
                    player.closeInventory();
                    main.getSendingMessage().add(player.getName());

                } else if (clicked.getType() == Material.LEGACY_WOOL && clicked.getDurability() == (short) 5) {
                    player.closeInventory();
                    main.getRPInventoryManager().initializeList();
                    player.openInventory(main.getRPInventoryManager().getReportsList().getInventory());
                    main.getSelectedReports().remove(player.getName());
                } else if (clicked.getType() == Material.BARRIER) {
                    CloseReport(player, r);
                    main.getSelectedReports().remove(player.getName());
                }
                event.setCancelled(true);

            }
        }catch(NullPointerException ex){
        }
    }

    public void CloseReport(Player player, Report r){
        inventory.remove(clicked);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getPrefix() + " " + main.getUtils().getMessagesConfig().getString("Success-Close-Report").replace("%id%", String.valueOf(r.getReportId()))));
        if(Bukkit.getPlayer(r.getReporter()) != null)
            Bukkit.getPlayer(r.getReporter()).sendMessage(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId()))));
        else {
            if(main.getConfig().getStringList("User-Notifications." + r.getReporter()) != null){

                List<String> notifications = main.getConfig().getStringList("User-Notifications." + r.getReporter());
                notifications.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId()))));
                main.getConfig().set("User-Notifications." +r.getReporter(), notifications);
                main.saveConfig();
                main.reloadPluginConfig();
            }
        }
        for (Player p : main.getServer().getOnlinePlayers()) {
            if (p.hasPermission("reportplus.receive")) {
                if (p.hasPermission("reportplus.receive") || p.isOp()) {
                    for (String s : main.getUtils().getMessagesConfig().getStringList("Minecraft-Report-Receive-Format")) {
                        String translatedText = s.replace("%closer%", player.getName());
                        translatedText = translatedText.replace("%id%", r.getReportId() + ""); // {+ "" }to cast int to String
                        translatedText = translatedText.replace("%reason%", r.getReportContent());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', translatedText));
                    }
//                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m          I         "));
//                    p.sendMessage("");
//                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6&lReport &c&lClosed!         "));
//                    p.sendMessage("");
//                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6Closer: &c" + player.getName() + "          "));
//                    p.sendMessage("");
//                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6Report ID: &c" + r.getReportId() + "          "));
//                    p.sendMessage("");
//                    p.sendMessage(
//                            ChatColor.translateAlternateColorCodes('&', "          &6Report reason: &c" + r.getReportContent() + "          "));
//                    p.sendMessage("");
//                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m          I         "));
                }
            }
        }
        player.closeInventory();
        main.getReportsList().remove(r);
        if(main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
            main.getSqlManager().removeReportFromDatabase(r);
        else{
            main.getUtils().getReportsConfig().set("Reports.Report" + r.getReportId(), null);
        main.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> main.getUtils().saveReportsToConfig(), 20 * 2);}
    }

    public void CloseReport(String closer, Report r, boolean discord){

        inventory.remove(clicked);
        if(Bukkit.getPlayer(r.getReporter()) != null)
            Bukkit.getPlayer(r.getReporter()).sendMessage(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId()))));
        else {
            if(main.getConfig().getStringList("User-Notifications." +r.getReporter()) != null){
                List<String> notifications = main.getConfig().getStringList("User-Notifications." + r.getReporter());
                notifications.add(ChatColor.translateAlternateColorCodes('&', main.getUtils().getMessagesConfig().getString("Report-Closed-Message").replace("%id%", String.valueOf(r.getReportId()))));
                main.getConfig().set("User-Notifications." + r.getReporter(), notifications);
                main.saveConfig();
                main.reloadPluginConfig();
            }
        }
        for (Player p : main.getServer().getOnlinePlayers()) {
            if (p.hasPermission("reportplus.receive")) {
                if (p.hasPermission("reportplus.receive") || p.isOp()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m          I         "));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6&lReport &c&lClosed!         "));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6Closer: &c" +closer + "          "));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "          &6Report ID: &c" + r.getReportId() + "          "));
                    p.sendMessage("");
                    p.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "          &6Report reason: &c" + r.getReportContent() + "          "));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m          I         "));
                }
            }
        }
        main.getReportsList().remove(r);
        if(main.getConfig().getBoolean("Enabled-Modules.MySQL.Enabled"))
            main.getSqlManager().removeReportFromDatabase(r);
        else
            main.getUtils().getReportsConfig().set("Reports.Report" + r.getReportId(), null);

        main.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> main.getUtils().saveReportsToConfig(), 20 * 2);
    }
}
