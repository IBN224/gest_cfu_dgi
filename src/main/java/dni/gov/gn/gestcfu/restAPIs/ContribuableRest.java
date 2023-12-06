package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Contribuable;
import dni.gov.gn.gestcfu.entities.Region;
import dni.gov.gn.gestcfu.repositories.ContribuableRespository;
import dni.gov.gn.gestcfu.repositories.RegionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ContribuableRest {

    @Autowired
    private ContribuableRespository contribuableRespository;

    @PostMapping(value = "/contribuable")
    public Contribuable addContribuable(@RequestBody Contribuable contribuable) {
        return contribuableRespository.save(contribuable);
    }

    @PutMapping(value = "/contribuable/{id}")
    public Contribuable updateContribuable(@PathVariable(value = "id") String id, @RequestBody Contribuable contribuable) {
        contribuable.setCode(id);
        return contribuableRespository.save(contribuable);
    }

    @GetMapping(value = "/contribuable")
    public List<Contribuable> contribuables() {
        return contribuableRespository.findAll();
    }

    @GetMapping(value = "/contribuable/{id}")
    public Contribuable contribuable(@PathVariable(value = "id") String id) {
        return contribuableRespository.findById(id).get();
    }

    @GetMapping(value = "/contribuable-by")
    public List<Contribuable> contribuableBy(@RequestParam(name = "type") String type) {
        return contribuableRespository.getContribuableBy(type);
    }
}
