package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.TypeContribuable;
import dni.gov.gn.gestcfu.repositories.TypeContribuableRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TypeContribuableRest {

    @Autowired
    private TypeContribuableRespository typeContribuableRespository;

    @PostMapping(value = "/type-contribuable")
    public TypeContribuable addTypeContribuable(@RequestBody TypeContribuable typeContribuable) {
        return typeContribuableRespository.save(typeContribuable);
    }

    @PutMapping(value = "/type-contribuable/{id}")
    public TypeContribuable updateTypeContribuable(@PathVariable(value = "id") Long id, @RequestBody TypeContribuable typeContribuable) {
        typeContribuable.setCode(id);
        return typeContribuableRespository.save(typeContribuable);
    }

    @GetMapping(value = "/type-contribuable")
    public List<TypeContribuable> typeContribuables() {
        return typeContribuableRespository.findAll();
    }

}
