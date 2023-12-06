package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_SECTEUR_OU_LOCALITE")
@SequenceGenerator(name = "sequence_td_secture_ou_localite", sequenceName = "TD_SEC_LOC_SEQ", allocationSize = 1)
public class SecteurOuLocalite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_secture_ou_localite")
    private Long code;
    private String libelle;
    @ManyToOne
    private QuartierOuDistrict quartierOuDistrict;
}
