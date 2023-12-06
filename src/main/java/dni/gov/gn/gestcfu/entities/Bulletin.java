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
@Table(name = "TD_BULLETIN")
@SequenceGenerator(name = "sequence_td_bulletin", sequenceName = "TD_BULLETIN_SEQ", allocationSize = 1)
public class Bulletin {

    @Id
    private String code;
    private LocalDate dateBL;
    private String originService;
    private int periodDebutAnnee;
    private int periodDebutMois;
    private int periodFinAnnee;
    private int periodFinMois;
    private Boolean status;
    @ManyToOne
    private Batiment batiment;
    @OneToMany(mappedBy = "bulletin")
    @JsonIgnoreProperties("bulletin")
    private List<BulletinImpot> bulletinImpots;
    private Boolean isDeleted;


}
