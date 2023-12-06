package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Declaration;
import dni.gov.gn.gestcfu.entities.Location;
import dni.gov.gn.gestcfu.repositories.DeclarationRespository;
import dni.gov.gn.gestcfu.repositories.LocationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class DeclarationRest {

    @Autowired
    private DeclarationRespository declarationRespository;

    @PostMapping(value = "/declaration")
    public Declaration addDeclaration(@RequestBody Declaration declaration) {
        return declarationRespository.save(declaration);
    }

    @PutMapping(value = "/declaration/{id}")
    public Declaration updateDeclaration(@PathVariable(value = "id") String id, @RequestBody Declaration declaration) {
        declaration.setCode(id);
        return declarationRespository.save(declaration);
    }

    @GetMapping(value = "/declaration-by")
    public List<Declaration> declarationsBy(@RequestParam("batiment") String batiment) {
        return declarationRespository.getDeclarationBy(batiment);
    }

    @GetMapping(value = "/declaration/{id}")
    public Declaration declaration(@PathVariable(value = "id") String id) {
        return declarationRespository.findById(id).get();
    }

    @GetMapping(value = "/declaration")
    public List<Declaration> declarations() {
        return declarationRespository.findAll();
    }

    @GetMapping(value = "/declaration-max")
    public String getDeclarationMax() {
        return declarationRespository.getDeclarationMax();
    }

}
