package dni.gov.gn.gestcfu.repositories;

import dni.gov.gn.gestcfu.entities.EquipAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipAgentRespository extends JpaRepository<EquipAgent, Long> {

    @Query("select (e.libelle), e.annee, count(e.agent.code) from EquipAgent e group by e.libelle, annee")
    List<?> getEquipAgentBy();

    @Query("select e from EquipAgent e where e.libelle=:equip and e.annee=:annee order by e.code desc")
    List<EquipAgent> getAllAgentByEquip(@Param("equip") String equip,
                                        @Param("annee") int anne);

}
