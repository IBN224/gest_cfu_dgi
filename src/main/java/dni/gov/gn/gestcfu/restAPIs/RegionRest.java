package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Region;
import dni.gov.gn.gestcfu.repositories.RegionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class RegionRest {

    @Autowired
    private RegionRespository regionRespository;

    @PostMapping(value = "/region")
    public Region addRegion(@RequestBody Region region) {
        return regionRespository.save(region);
    }

    @PutMapping(value = "/region/{id}")
    public Region updateRegion(@PathVariable(value = "id") Long id, @RequestBody Region region) {
        region.setCode(id);
        return regionRespository.save(region);
    }

    @GetMapping(value = "/region")
    public List<Region> regions() {
        return regionRespository.findAll();
    }

}
