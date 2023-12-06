package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TC_TYPE_CONTRIBUABLE")
@SequenceGenerator(name = "sequence_tc_type_contribuable", sequenceName = "TC_TYPE_CONTRIBUABLE_SEQ", allocationSize = 1)
public class TypeContribuable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_tc_type_contribuable")
    private Long code;
    private String libelle;
    private String  dureeValiditeDocument;
}
