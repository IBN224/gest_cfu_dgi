package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_AGENT")
@SequenceGenerator(name = "sequence_td_agent", sequenceName = "TD_AGENT_SEQ", allocationSize = 1)
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_agent")
    private Long code;
    private String matricule;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;

}
