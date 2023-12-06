package dni.gov.gn.gestcfu.repositories;


import dni.gov.gn.gestcfu.entities.Quittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuittanceRespository extends JpaRepository<Quittance, String> {

    @Query("select q from Quittance q left join q.declaration d where (:code is null or q.code =:code) and "
         + "(:batiment is 'all' or d.batiment.code =:batiment) order by q.code desc ")
    List<Quittance> getQuittanceBy(@Param("code") String code,
                                   @Param("batiment") String batiment);

    @Query("select max(q.code) from Quittance q ")
    String getQuittanceMax();

}
