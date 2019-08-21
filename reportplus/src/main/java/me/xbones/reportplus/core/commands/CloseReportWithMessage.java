package me.xbones.reportplus.core.commands;

import me.xbones.reportplus.api.Report;
import me.xbones.reportplus.core.Core;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CloseReportWithMessage extends Command {

	private Core main;

	public CloseReportWithMessage(Core main) {
	    super(main,"closereport");
		this.main = main;
	}

	@Override
	public void execute(MessageReceivedEvent event) {

        Member member = event.getGuild().getMember(event.getAuthor());
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                event.getChannel().sendMessage("You are not allowed to use this command!").queue();
                return;
            }


            String[] args = event.getMessage().getContentRaw().split(" ");

            if(args.length < 3){
                event.getChannel().sendMessage("Please enter ID and message.").queue();
                return;
            }
            int id = Integer.parseInt(args[1]);

            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }

            Report selectedReport = null;
            for(Report r : main.getReportPlus().getReports()){
                if(r.getReportId() == id){
                    selectedReport = r;
                }
            }

            if(selectedReport == null){
                event.getChannel().sendMessage("Report not found. Please make sure the id is correct.").queue();
                return;
            }
        String Message = sb.toString().trim();

        main.getReportPlus().closeReport(event.getMember().getEffectiveName(), selectedReport, true, Message);


        event.getChannel().sendMessage("```Report #" + id + " closed with message: " + Message + "```").queue();

    }
  }
