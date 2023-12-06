package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Imposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImpositionRespository extends JpaRepository<Imposition, String> {

    @Query("select b from Imposition b where (:code is null or b.code =:code) and "
         + "(:batiment is 'all' or b.bulletin.batiment.code =:batiment) order by b.code desc")
    List<Imposition> getImpositionBy(@Param("code") String code,
                                     @Param("batiment") String batiment);
}
