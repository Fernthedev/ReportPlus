package me.xbones.reportplus.api.punishments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Punishment {

    private String punisher;

    private String punished;

    private String reason;

    private PunishmentType type;


}
