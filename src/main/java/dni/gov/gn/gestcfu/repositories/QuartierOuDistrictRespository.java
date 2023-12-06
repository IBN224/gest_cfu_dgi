package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.QuartierOuDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartierOuDistrictRespository extends JpaRepository<QuartierOuDistrict, Long> {

    List<QuartierOuDistrict> findBySousPrefOuCom_Code(Long code);
}
