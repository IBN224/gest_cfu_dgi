package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Contribuable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContribuableRespository extends JpaRepository<Contribuable, String> {

    public List<Contribuable> findByTypeContribuable(String code);

    @Query("select c from Contribuable c where (:type is 'all' or c.typeContribuable =:type) order by c.code desc ")
    List<Contribuable> getContribuableBy(@Param("type") String type);

}
