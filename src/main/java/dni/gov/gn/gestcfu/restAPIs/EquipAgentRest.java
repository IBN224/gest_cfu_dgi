package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Agent;
import dni.gov.gn.gestcfu.entities.EquipAgent;
import dni.gov.gn.gestcfu.repositories.EquipAgentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EquipAgentRest {

    @Autowired
    private EquipAgentRespository equipAgentRespository;

    @PostMapping(value = "/equip-agent")
    public EquipAgent addEquipAgent(@RequestBody EquipAgent equipAgent) {
        return equipAgentRespository.save(equipAgent);
    }

    @PutMapping(value = "/equip-agent/{id}")
    public EquipAgent updateEquipAgent(@PathVariable(value = "id") Long id, @RequestBody EquipAgent equipAgent) {
        equipAgent.setCode(id);
        return equipAgentRespository.save(equipAgent);
    }

    @GetMapping(value = "/equip-agent")
    public List<EquipAgent> equipAgents() {
        return equipAgentRespository.findAll();
    }

    @GetMapping(value = "/equip-agent/{id}")
    public EquipAgent equipAgent(@PathVariable(value = "id") Long id) {
        return equipAgentRespository.findById(id).get();
    }

    @GetMapping(value = "/equip-agent-by")
    public List<?> equipAgentBy() {
        return equipAgentRespository.getEquipAgentBy();
    }

    @GetMapping(value = "/agent-by-equip")
    public List<EquipAgent> getAllAgentByEquip(@RequestParam("equip") String equip,
                                               @RequestParam("annee") int annee) {
        return equipAgentRespository.getAllAgentByEquip(equip, annee);
    }

}
