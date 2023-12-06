package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Batiment;
import dni.gov.gn.gestcfu.entities.Contribuable;
import dni.gov.gn.gestcfu.repositories.BatimentRespository;
import dni.gov.gn.gestcfu.repositories.ContribuableRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BatimentRest {

    @Autowired
    private BatimentRespository batimentRespository;

    @PostMapping(value = "/batiment")
    public Batiment addBatiment(@RequestBody Batiment batiment) {
        return batimentRespository.save(batiment);
    }

    @PutMapping(value = "/batiment/{id}")
    public Batiment updateBatiment(@PathVariable(value = "id") String id, @RequestBody Batiment batiment) {
        batiment.setCode(id);
        return batimentRespository.save(batiment);
    }

    @GetMapping(value = "/batiment")
    public List<Batiment> batiments() {

        //Pageable paging = PageRequest.of(1, 5);

        return batimentRespository.findAll();
    }

    @GetMapping(value = "/batiment/{id}")
    public Batiment batiment(@PathVariable(value = "id") String id) {
        return batimentRespository.findById(id).get();
    }

    @GetMapping(value = "/batiment-by")
    public List<Batiment> batimentBy(@RequestParam("status") String status,
                                     @RequestParam("type") Long type,
                                     @RequestParam("region") Long region,
                                     @RequestParam("prefecture") Long prefecture,
                                     @RequestParam("sousPref") Long sousPref,
                                     @RequestParam("quartier") Long quartier,
                                     @RequestParam("localite") Long localite,
                                     @RequestParam("contribuable") String contribuable,
                                     @RequestParam(name = "page", defaultValue = "0")int page,
                                     @RequestParam(name = "size", defaultValue = "10")int size) {
        //if(page>0) page--;

        return batimentRespository.getBatimentBy(status, type, contribuable);
    }

    @GetMapping(value = "/max-batiment-by-secteur")
    public Long maxBatimentBySect(@RequestParam("code") Long code) {
        return batimentRespository.getBatimentMaxBySecteur(code);
    }
}
