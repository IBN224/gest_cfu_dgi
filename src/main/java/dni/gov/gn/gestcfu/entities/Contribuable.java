package dni.gov.gn.gestcfu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "TD_CONTRIBUABLE")
@SequenceGenerator(name = "sequence_td_contribuable", sequenceName = "TD_CONTRIBUABLE_SEQ", allocationSize = 1)
public class Contribuable {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_td_contribuable")
   // private Long id;
    private String code;
    private String codeOld;
    private String sigle;
    private String raisonSocial;
    private String telephone;
    private String email;
    private String bp;
    private String adresse;
    @ManyToOne
    private SousPrefectureOuCommune sousPrefOuCom;
    @ManyToOne
    private QuartierOuDistrict quartierOuDistrict;
    @ManyToOne
    private SecteurOuLocalite secteurOuLocalite;
//    @ManyToOne
//    private TypeContribuable typeContribuable;
    private String typeContribuable;
    private LocalDate dateCreation;
    private LocalDate dateFermeture;
    private LocalDate dateCession;
    @OneToMany(mappedBy = "contribuable")
    @JsonIgnoreProperties("contribuable")
    private List<Batiment> batiments;
}
