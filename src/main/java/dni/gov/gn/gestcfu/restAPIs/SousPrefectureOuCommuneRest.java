package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.SousPrefectureOuCommune;
import dni.gov.gn.gestcfu.repositories.SousPrefectureOuCommuneRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SousPrefectureOuCommuneRest {

    @Autowired
    private SousPrefectureOuCommuneRespository sousPrefectureOuCommuneRespository;

    @PostMapping(value = "/sous-prefecture")
    public SousPrefectureOuCommune addSousPrefectureOuCommune(@RequestBody SousPrefectureOuCommune sousPrefectureOuCommune) {
        return sousPrefectureOuCommuneRespository.save(sousPrefectureOuCommune);
    }

    @PutMapping(value = "/sous-prefecture/{id}")
    public SousPrefectureOuCommune updateSousPrefectureOuCommune(@PathVariable(value = "id") Long id, @RequestBody SousPrefectureOuCommune sousPrefectureOuCommune) {
        sousPrefectureOuCommune.setCode(id);
        return sousPrefectureOuCommuneRespository.save(sousPrefectureOuCommune);
    }

    @GetMapping(value = "/sous-prefecture")
    public List<SousPrefectureOuCommune> sousPrefectureOuCommunes() {
        return sousPrefectureOuCommuneRespository.findAll();
    }

    @GetMapping(value = "/sous-prefecture/{id}")
    public SousPrefectureOuCommune sousPrefectureOuCommune(@PathVariable(value = "id") Long id) {
        return sousPrefectureOuCommuneRespository.findById(id).orElse(null);
    }

    @GetMapping(value = "/all-sous-prefecture-by-code/{id}")
    public List<SousPrefectureOuCommune> allSsPrefectureByCode(@PathVariable(value = "id") Long id) {
        return sousPrefectureOuCommuneRespository.findByPrefecture_Code(id);
    }
}
