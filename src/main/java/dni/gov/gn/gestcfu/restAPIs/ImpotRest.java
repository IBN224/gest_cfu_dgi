package dni.gov.gn.gestcfu.restAPIs;


import dni.gov.gn.gestcfu.entities.Impot;
import dni.gov.gn.gestcfu.repositories.ImpotRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ImpotRest {

    @Autowired
    private ImpotRespository impotRespository;



    @GetMapping(value = "/impot/{id}")
    public Impot impot(@PathVariable(value = "id") Long id) {
        return impotRespository.findById(id).get();
    }

    @GetMapping(value = "/impot")
    public List<Impot> impots() {
        return impotRespository.findAll();
    }

}
