package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BulletinRespository extends JpaRepository<Bulletin, String> {

    @Query("select max(b.code) from Bulletin b ")
    String getBulletinMax();

    @Query("select b from Bulletin b where b.isDeleted = false and (:code is null or b.code =:code) and "
         + "(:batiment is 'all' or b.batiment.code =:batiment) order by b.code desc ")
    List<Bulletin> getBulletinBy(@Param("code") String code,
                                 @Param("batiment") String batiment);

}
