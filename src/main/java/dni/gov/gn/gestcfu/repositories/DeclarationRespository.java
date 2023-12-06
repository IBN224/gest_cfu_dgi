package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Declaration;
import dni.gov.gn.gestcfu.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeclarationRespository extends JpaRepository<Declaration, String> {

    @Query("select d from Declaration d where (:batiment is 'all' or d.batiment.code =:batiment) order by d.code desc ")
    List<Declaration> getDeclarationBy(@Param("batiment") String batiment);

    @Query("select max(d.code) from Declaration d ")
    String getDeclarationMax();
}
