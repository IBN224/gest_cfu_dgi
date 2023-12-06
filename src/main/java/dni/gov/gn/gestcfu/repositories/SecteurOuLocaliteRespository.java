package dni.gov.gn.gestcfu.repositories;


import dni.gov.gn.gestcfu.entities.SecteurOuLocalite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecteurOuLocaliteRespository extends JpaRepository<SecteurOuLocalite, Long> {

    List<SecteurOuLocalite> findByQuartierOuDistrict_Code(Long code);
}
