package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Prefecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrefectureRespository extends JpaRepository<Prefecture, Long> {

    List<Prefecture> findPrefectureByRegion_Code(Long code);
}
