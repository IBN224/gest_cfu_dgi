package dni.gov.gn.gestcfu.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TD_PREFECTURE")
@SequenceGenerator(name = "sequence_td_prefecture", sequenceName = "TD_PREFECTURE_SEQ", allocationSize = 1)
public class Prefecture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_prefecture")
    private Long code;
    private String libelle;
    @ManyToOne
    private Region region;
}
