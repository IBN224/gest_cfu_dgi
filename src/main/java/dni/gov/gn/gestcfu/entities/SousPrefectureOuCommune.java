package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_SOUS_PREFECTURE_OU_COMMUNE")
@SequenceGenerator(name = "sequence_td_sous_pref_ou_com", sequenceName = "TD_SS_PRE_COM_SEQ", allocationSize = 1)
public class SousPrefectureOuCommune {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_sous_pref_ou_com")
    private Long code;
    private String libelle;
    @ManyToOne
    private Prefecture prefecture;
}
