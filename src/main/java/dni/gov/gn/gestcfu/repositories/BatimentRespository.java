package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Batiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatimentRespository extends JpaRepository<Batiment, String> {

    @Query("select b from Batiment b where (:status is 'all' or b.status =:status) and (:type is 0L or b.typeBatiment.code =:type) and "
         + "(:contribuable is 'all' or b.contribuable.code =:contribuable) order by b.code desc ")
    List<Batiment> getBatimentBy(@Param("status") String status,
                                 @Param("type") Long type,
                                 @Param("contribuable") String contribuable);

    @Query("select max(b.code) from Batiment b where b.secteurBatiment.code =:secteur")
    Long getBatimentMaxBySecteur(@Param("secteur") Long code);


//    @Query("select b from Batiment b where (:status is 'all' or b.status =:status) and (:type is 0L or b.typeBatiment.code =:type) and "
//            + "(:region is 0L or b.secteurBatiment.quartierOuDistrict.sousPrefOuCom.prefecture.region.code =:region) and "
//            + "(:prefecture is 0L or b.secteurBatiment.quartierOuDistrict.sousPrefOuCom.prefecture.code =:prefecture) and "
//            + "(:sousPref is 0L or b.secteurBatiment.quartierOuDistrict.sousPrefOuCom.code =:sousPref) and "
//            + "(:quartier is 0L or b.secteurBatiment.quartierOuDistrict.code =:quartier) and "
//            + "(:localite is 0L or b.secteurBatiment.code =:localite) and "
//            + "(:contribuable is 'all' or b.contribuable.code =:contribuable)")
//    Page<Batiment> getBatimentBy(@Param("status") String status,
//                                 @Param("type") Long type,
//                                 @Param("region") Long region,
//                                 @Param("prefecture") Long prefecture,
//                                 @Param("sousPref") Long sousPref,
//                                 @Param("quartier") Long quartier,
//                                 @Param("localite") Long localite,
//                                 @Param("contribuable") String contribuable,
//                                 Pageable pageable);

}
