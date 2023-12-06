package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.SecteurOuLocalite;
import dni.gov.gn.gestcfu.repositories.SecteurOuLocaliteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SecteurOuLocaliteRest {

    @Autowired
    private SecteurOuLocaliteRespository secteurOuLocaliteRespository;

    @PostMapping(value = "/localite")
    public SecteurOuLocalite addSecteurOuLocalite(@RequestBody SecteurOuLocalite secteurOuLocalite) {
        return secteurOuLocaliteRespository.save(secteurOuLocalite);
    }

    @PutMapping(value = "/localite/{id}")
    public SecteurOuLocalite updateSecteurOuLocalite(@PathVariable(value = "id") Long id, @RequestBody SecteurOuLocalite secteurOuLocalite) {
        secteurOuLocalite.setCode(id);
        return secteurOuLocaliteRespository.save(secteurOuLocalite);
    }

    @GetMapping(value = "/localite")
    public List<SecteurOuLocalite> secteurOuLocalites() {
        return secteurOuLocaliteRespository.findAll();
    }

    @GetMapping(value = "/localite/{id}")
    public SecteurOuLocalite secteurOuLocalite(@PathVariable(value = "id") Long id) {
        return secteurOuLocaliteRespository.findById(id).orElse(null);
    }

    @GetMapping(value = "/all-localite-by-code/{id}")
    public List<SecteurOuLocalite> allSecteurOuLocaliteByCode(@PathVariable(value = "id") Long id) {
        return secteurOuLocaliteRespository.findByQuartierOuDistrict_Code(id);
    }
}
