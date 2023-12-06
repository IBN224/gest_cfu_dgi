package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "TD_QUITTANCE")
@SequenceGenerator(name = "sequence_td_quittance", sequenceName = "TD_QUITTANCE_SEQ", allocationSize = 1)
public class Quittance {

    @Id
    private String code;
    private LocalDate dateQuittance;
    private String type;
    private String modeReglement;
    private String bank;
    private String chequeOuRecue;
    private double montantPayer;
    @ManyToOne
    @JsonIgnoreProperties("quittances")
    private Declaration declaration;
    @ManyToOne
    @JsonIgnoreProperties("quittances")
    private Imposition imposition;
    private String observation;
    private String duplicata;


}
