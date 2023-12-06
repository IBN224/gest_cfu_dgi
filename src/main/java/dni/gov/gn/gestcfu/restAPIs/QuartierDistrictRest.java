package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.QuartierOuDistrict;
import dni.gov.gn.gestcfu.entities.SecteurOuLocalite;
import dni.gov.gn.gestcfu.repositories.QuartierOuDistrictRespository;
import dni.gov.gn.gestcfu.repositories.SecteurOuLocaliteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class QuartierDistrictRest {

    @Autowired
    private QuartierOuDistrictRespository quartierOuDistrictRespository;

    @PostMapping(value = "/quartier")
    public QuartierOuDistrict addQuartierOuDistrict(@RequestBody QuartierOuDistrict quartierOuDistrict) {
        return quartierOuDistrictRespository.save(quartierOuDistrict);
    }

    @PutMapping(value = "/quartier/{id}")
    public QuartierOuDistrict updateQuartierOuDistrict(@PathVariable(value = "id") Long id, @RequestBody QuartierOuDistrict quartierOuDistrict) {
        quartierOuDistrict.setCode(id);
        return quartierOuDistrictRespository.save(quartierOuDistrict);
    }

    @GetMapping(value = "/quartier")
    public List<QuartierOuDistrict> quartierOuDistricts() {
        return quartierOuDistrictRespository.findAll();
    }

    @GetMapping(value = "/quartier/{id}")
    public QuartierOuDistrict quartierOuDistrict(@PathVariable(value = "id") Long id) {
        return quartierOuDistrictRespository.findById(id).orElse(null);
    }

    @GetMapping(value = "/all-quartier-by-code/{id}")
    public List<QuartierOuDistrict> allQuartierOuDistrictByCode(@PathVariable(value = "id") Long id) {
        return quartierOuDistrictRespository.findBySousPrefOuCom_Code(id);
    }
}
