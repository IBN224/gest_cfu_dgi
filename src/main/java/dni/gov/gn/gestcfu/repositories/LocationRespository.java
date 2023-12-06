package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.Location;
import dni.gov.gn.gestcfu.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface LocationRespository extends JpaRepository<Location, Long> {

    @Query("select b from Location b where (:usage is 'all' or b.usage =:usage) and (:type is 'all' or b.type =:type) and "
         + "(:batiment is 'all' or b.batiment.code =:batiment) and "
         + "(:contribuable is 'all' or b.batiment.contribuable.code =:contribuable) order by b.code desc")
    List<Location> getLocationBy(@Param("usage") String usage,
                                 @Param("type") String type,
                                 @Param("batiment") String batiment,
                                 @Param("contribuable") String contribuable);

    @Query("select sum(b.montant) from Location b where b.batiment.code=:batiment and "
         + " TO_DATE(TO_CHAR(b.dateDebut,'yyyy-MM'), 'yyyy-MM')>=TO_DATE(:dateDebut,'yyyy-MM') and "
         + " TO_DATE(TO_CHAR(b.dateFin,'yyyy-MM'), 'yyyy-MM')<=TO_DATE(:dateFin,'yyyy-MM') ")
    String getLocationMontantBy(@Param("batiment") String batiment,
                                @Param("dateDebut") String dateDebut,
                                @Param("dateFin") String dateFin);

    @Query("select b from Location b where b.batiment.code=:batiment and "
            + " TO_DATE(TO_CHAR(b.dateDebut,'yyyy-MM'), 'yyyy-MM')>=TO_DATE(:dateDebut,'yyyy-MM') and "
            + " TO_DATE(TO_CHAR(b.dateFin,'yyyy-MM'), 'yyyy-MM')<=TO_DATE(:dateFin,'yyyy-MM') order by b.code desc ")
    List<Location> getLocationByBatiment(@Param("batiment") String batiment,
                                         @Param("dateDebut") String dateDebut,
                                         @Param("dateFin") String dateFin);
}
