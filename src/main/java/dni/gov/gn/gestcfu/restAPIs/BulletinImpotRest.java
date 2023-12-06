package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Bulletin;
import dni.gov.gn.gestcfu.entities.BulletinImpot;
import dni.gov.gn.gestcfu.repositories.BulletinImpotRespository;
import dni.gov.gn.gestcfu.repositories.BulletinRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BulletinImpotRest {

    @Autowired
    private BulletinImpotRespository bulletinImpotRespository;

    @PostMapping(value = "/bulletin-impot")
    public BulletinImpot addBulletinImpot(@RequestBody BulletinImpot bulletinImpot) {
        return bulletinImpotRespository.save(bulletinImpot);
    }

    @PutMapping(value = "/bulletin-impot/{id}")
    public BulletinImpot updateBulletinImpot(@PathVariable(value = "id") Long id, @RequestBody BulletinImpot bulletinImpot) {
        bulletinImpot.setCode(id);
        return bulletinImpotRespository.save(bulletinImpot);
    }

    @GetMapping(value = "/bulletin-impot/{id}")
    public BulletinImpot bulletin(@PathVariable(value = "id") Long id) {
        return bulletinImpotRespository.findById(id).get();
    }

    @GetMapping(value = "/bulletin-impot")
    public List<BulletinImpot> bulletins() {
        return bulletinImpotRespository.findAll();
    }


}
