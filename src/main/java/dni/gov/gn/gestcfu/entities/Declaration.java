package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "TD_DECLARATION")
@SequenceGenerator(name = "sequence_td_declaration", sequenceName = "TD_DECLARATION_SEQ", allocationSize = 1)
public class Declaration {

    @Id
    private String code;
    private LocalDate dateDeclaration;
    private String type;
    private double montant;
    private int periodDebutAnnee;
    private int periodDebutMois;
    private int periodFinAnnee;
    private int periodFinMois;
    @ManyToOne
    private Batiment batiment;
    @OneToMany(mappedBy = "declaration")
    @JsonIgnoreProperties("declaration")
    private List<Quittance> quittances;


}
