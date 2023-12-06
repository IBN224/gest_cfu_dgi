package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TD_BULLETIN_IMPOT")
@SequenceGenerator(name = "sequence_td_bulletin_impot", sequenceName = "TD_BULLETIN_IMPOT_SEQ", allocationSize = 1)
public class BulletinImpot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_bulletin_impot")
    private Long code;
    private double montant;
    @ManyToOne
    private Impot impot;
    @ManyToOne
    @JsonIgnoreProperties("bulletinImpots")
    private Bulletin bulletin;


}
