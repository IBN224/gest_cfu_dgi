package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.TypeBatiment;
import dni.gov.gn.gestcfu.repositories.TypeBatimentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TypeBatimentRest {

    @Autowired
    private TypeBatimentRespository typeBatimentRespository;

    @PostMapping(value = "/type-batiment")
    public TypeBatiment addTypeBatiment(@RequestBody TypeBatiment typeBatiment) {
        return typeBatimentRespository.save(new TypeBatiment(34L, typeBatiment.getLibelle(), null));
    }

    @PutMapping(value = "/type-batiment/{id}")
    public TypeBatiment updateTypeBatiment(@PathVariable(value = "id") Long id, @RequestBody TypeBatiment typeBatiment) {
        typeBatiment.setCode(id);
        return typeBatimentRespository.save(typeBatiment);
    }

    @GetMapping(value = "/type-batiment")
    public List<TypeBatiment> typeBatiments() {
        return typeBatimentRespository.findAll();
    }

    @GetMapping(value = "/type-batiment/{id}")
    public TypeBatiment typeBatiment(@PathVariable(value = "id") Long id) {
        return typeBatimentRespository.findById(id).get();
    }

}
