package me.xbones.reportplus.core.universalcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IReportPlus;
import me.xbones.reportplus.core.RPlayer;
import me.xbones.reportplus.core.gson.LangConfig;
import me.xbones.reportplus.spigot.ReportPlus;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("report")
public class ReportCommand extends BaseCommand {

    private HashMap<String, Long> cooldowns = new HashMap<>();
    private IReportPlus main;
    public ReportCommand(IReportPlus main){
        this.main=main;
    }

    @Default
    public void execute(FernCommandIssuer commandSender, String[] args) {
        LangConfig lang = main.getLangConfig().getConfigData();
        if (commandSender instanceof IFPlayer<?>) {
            IFPlayer<?> p = (IFPlayer<?>) commandSender;
            if (main.getBooleanFromConfig("Enabled-Modules.Reporting")) {
                if (p.hasPermission("reportplus.use")) {

                    // COOLDOWN //
                    int cooldownTime = main.getIntFromConfig("Command-cooldown"); // Get number of seconds from wherever you want
                    if(cooldowns.containsKey(p.getName())) {
                        long secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                        if(secondsLeft>0) {
                            // Still cooling down
                            p.sendMessage(new TextMessage(translate( main.getPrefix() + lang.getCoolDownText().replace("%secondsLeft%", secondsLeft + ""))));
                            return;
                        }
                    }
                    // No cooldown found or cooldown has expired, save new cooldown
                    cooldowns.put(p.getName(), System.currentTimeMillis());

                    // COOLDOWN END //

                    if(args.length < 2) {
                            p.sendMessage(new TextMessage(translate( main.getPrefix() + " " + main.getLangData().getNotEnoughArgs())));

                    } else {

                        IFPlayer<?> target = Universal.getMethods().getPlayerFromName(args[0]);


                        if(target.isPlayerNull())
                            p.sendMessage(new TextMessage(translate( main.getPrefix() + lang.getPlayerCannotBeFound())));
                        else {
                            if (target == p && !p.hasPermission("reportplus.reportSelf")) {
                                p.sendMessage(new TextMessage(translate(main.getPrefix() + " " + main.getLangConfig().getConfigData().getCantReportSelf())));
                                return;
                            }

                            if (Universal.getMethods().getServerType().isProxy()) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 1; i < args.length; i++){
                                    sb.append(args[i]).append(" ");
                                }

                                String Message = sb.toString().trim();
                                Universal.getScheduler().runAsync(() -> main.getCore().reportToBoth(new RPlayer(main.getCore(), p.getName(), p.getUuid()), target.getName(), Message));
                            } else {
                                if (!(main instanceof ReportPlus)) throw new IllegalStateException("Report plus on non-proxy state is running a non-spigot instance");

                                ReportPlus spigotMain = (ReportPlus) main;

                                Universal.debug("Opening report GUI for " + p.getName() + " reporting " + target.getName());

                                spigotMain.getReporting().put((Player) p.getPlayer(),(Player) target.getPlayer());
                                spigotMain.showGUI((Player) p.getPlayer());
                            }
                        }
                    }

                } else {
                    main.NoPerm(p);
                }
            } else {
                p.sendMessage(new TextMessage(translate( main.getPrefix() + " " + lang.getReportingIsDisabled())));
            }
        } else {
            commandSender.sendMessage(new TextMessage(translate(lang.getCanOnlyRunIngame())));
        }
    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
