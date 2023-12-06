package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "TD_LOCATION")
@SequenceGenerator(name = "sequence_td_location", sequenceName = "TD_LOCATION_SEQ", allocationSize = 1)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_location")
    private Long code;
    private int niveauLocation;
    private int uniteLocation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private double valeurLocative;
    private double baseImposition;
    private double tauxLocation;
    private double montant;
    private String usage;
//    @ManyToOne
//    private Contribuable contribuable;
    @ManyToOne
    @JsonIgnoreProperties("locations")
    private Batiment batiment;
//    @ManyToOne
//    private Impot impot;


}
