package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TC_TYPE_BATIMENT")
@SequenceGenerator(name = "sequence_tc_type_batiment", sequenceName = "TC_TYPE_BATIMENT_SEQ", allocationSize = 1)
public class TypeBatiment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_tc_type_batiment")
    private Long code;
    private String libelle;
    @OneToMany(mappedBy = "typeBatiment")
    @JsonIgnoreProperties("typeBatiment")
    private List<Batiment> batiments;
}
