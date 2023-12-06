package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_QUARTIER_OU_DISTRICT")
@SequenceGenerator(name = "sequence_td_quartier_ou_dist", sequenceName = "TD_QUART_OU_DIST_SEQ", allocationSize = 1)
public class QuartierOuDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_quartier_ou_dist")
    private Long code;
    private String libelle;
    @ManyToOne
    private SousPrefectureOuCommune sousPrefOuCom;
}
