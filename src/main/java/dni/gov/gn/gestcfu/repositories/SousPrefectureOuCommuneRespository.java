package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.SousPrefectureOuCommune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SousPrefectureOuCommuneRespository extends JpaRepository<SousPrefectureOuCommune, Long> {

    List<SousPrefectureOuCommune> findByPrefecture_Code(Long code);
}
