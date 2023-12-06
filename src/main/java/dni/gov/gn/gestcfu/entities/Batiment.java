package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "TD_BATIMENT")
@SequenceGenerator(name = "sequence_td_batiment", sequenceName = "TD_BATIMENT_SEQ", allocationSize = 1)
public class Batiment {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_batiment")
    private String code;
    private String status;
    private String natureFondation;
    private String natureToit;
    private String natureMur;
    private String etatBatiment;
    private LocalDate dateCreation;
    private int nbreEtage;
    private int nbreUnite;
    private int nbreOccupee;
    @ManyToOne
    @JsonIgnoreProperties("batiments")
    private TypeBatiment typeBatiment;
    @ManyToOne
    @JsonIgnoreProperties("batiments")
    private Contribuable contribuable;
//    @ManyToOne
//    private QuartierOuDistrict quartierBatiment;
//    @ManyToOne
//    private SousPrefectureOuCommune communeBatiment;
    @ManyToOne
    private SecteurOuLocalite secteurBatiment;
    private String longitude;
    private String latitude;
    private LocalDate dateGeolocalisation;
    private String nomContact;
    private String prenomContact;
    private String telephoneContact;
    @ManyToOne
    private QuartierOuDistrict quartierContact;
//    @ManyToOne
//    private SousPrefectureOuCommune communeContact;
//    @ManyToOne
//    private SecteurOuLocalite secteurContact;
    private String natureRelation;
    @OneToMany(mappedBy = "batiment")
    @JsonIgnoreProperties("batiment")
    private List<Location> locations;
}
