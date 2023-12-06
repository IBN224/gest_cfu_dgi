package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Prefecture;
import dni.gov.gn.gestcfu.repositories.PrefectureRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PrefectureRest {

    @Autowired
    private PrefectureRespository prefectureRespository;

    @PostMapping(value = "/prefecture")
    public Prefecture addPrefecture(@RequestBody Prefecture prefecture) {
        return prefectureRespository.save(prefecture);
    }

//    @PutMapping(value = "/prefecture/{id}")
//    public Prefecture updatePrefecture(@PathVariable(value = "id") Long id, @RequestBody Prefecture prefecture) {
//        prefectureRespository.
//        return prefectureRespository.save(prefecture);
//    }

    @GetMapping(value = "/prefecture")
    public List<Prefecture> prefectures() {
        return prefectureRespository.findAll();
    }

    @GetMapping(value = "/all-prefecture-by-code/{id}")
    public List<Prefecture> allQuartierOuDistrictByCode(@PathVariable(value = "id") Long id) {
        return prefectureRespository.findPrefectureByRegion_Code(id);
    }

    @GetMapping(value = "/prefecture/{id}")
    public Prefecture prefecture(@PathVariable(value = "id") Long id) {
        return prefectureRespository.findById(id).orElse(null);
    }
}
