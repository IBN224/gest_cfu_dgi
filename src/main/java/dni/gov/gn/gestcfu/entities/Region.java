package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_REGION")
@SequenceGenerator(name = "sequence_td_region", sequenceName = "TD_REGION_SEQ", allocationSize = 1)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_region")
    private Long code;
    private String libelle;
}
