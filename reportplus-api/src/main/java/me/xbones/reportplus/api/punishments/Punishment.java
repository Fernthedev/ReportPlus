package me.xbones.reportplus.api.punishments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Punishment {

    private String punisher;

    private String punished;

    private String reason;

    private PunishmentType type;


}
