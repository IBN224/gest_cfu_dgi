package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TD_IMPOSITION")
@SequenceGenerator(name = "sequence_td_imposition", sequenceName = "TD_imposition_SEQ", allocationSize = 1)
public class Imposition {

    @Id
    private String code;
    private LocalDate dateImposition;
    @ManyToOne
    private Bulletin bulletin;
    @OneToMany(mappedBy = "imposition")
    @JsonIgnoreProperties("imposition")
    private List<Quittance> quittances;


}
