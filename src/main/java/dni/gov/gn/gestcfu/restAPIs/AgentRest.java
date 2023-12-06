package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Agent;
import dni.gov.gn.gestcfu.repositories.AgentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AgentRest {

    @Autowired
    private AgentRespository agentRespository;

    @PostMapping(value = "/agent")
    public Agent addAgent(@RequestBody Agent agent) {
        return agentRespository.save(agent);
    }

    @PutMapping(value = "/agent/{id}")
    public Agent updateAgent(@PathVariable(value = "id") Long id, @RequestBody Agent agent) {
        agent.setCode(id);
        return agentRespository.save(agent);
    }

    @GetMapping(value = "/agent/{id}")
    public Agent agent(@PathVariable(value = "id") Long id) {
        return agentRespository.findById(id).get();
    }

    @GetMapping(value = "/agent")
    public List<Agent> agents() {
        return agentRespository.findAll();
    }


}
