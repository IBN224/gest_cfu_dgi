package dni.gov.gn.gestcfu.entities;


import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "TC_IMPOT")
@SequenceGenerator(name = "sequence_tc_impot", sequenceName = "TC_IMPOT_SEQ", allocationSize = 1)
public class Impot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_tc_impot")
    private Long code;
    private String libelleLong;
    private String libelleCourt;
    private String groupImpot;
    private String services;

}
