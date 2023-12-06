package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_EQUIP_AGENT")
@SequenceGenerator(name = "sequence_td_equip_agent", sequenceName = "TD_EQUIP_AGENT_SEQ", allocationSize = 1)
public class EquipAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_equip_agent")
    private Long code;
    private String libelle;
    private int annee;
    private String chef;
    @ManyToOne
    private Agent agent;

}
