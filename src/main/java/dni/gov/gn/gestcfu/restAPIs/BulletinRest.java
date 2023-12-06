package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.*;
import dni.gov.gn.gestcfu.repositories.BulletinImpotRespository;
import dni.gov.gn.gestcfu.repositories.BulletinRespository;
import dni.gov.gn.gestcfu.repositories.ImpositionRespository;
import dni.gov.gn.gestcfu.repositories.ImpotRespository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
public class BulletinRest {

    @Autowired
    private BulletinRespository bulletinRespository;
    @Autowired
    private BulletinImpotRespository bulletinImpotRespository;
    @Autowired
    private ImpotRespository impotRespository;
    @Autowired
    private ImpositionRespository impositionRespository;


    @PostMapping(value = "/bulletin")
    public Bulletin addBulletin(@RequestBody BulletinImpotModel bulletin) {
        Bulletin bulletin1;
        bulletin1 = bulletinRespository
                .save(new Bulletin(bulletin.getCode(), bulletin.dateBL, bulletin.getOriginService(),
                        bulletin.periodDebutAnnee, bulletin.getPeriodDebutMois(), bulletin.getPeriodFinAnnee(),
                        bulletin.getPeriodFinMois(), bulletin.getStatus(), bulletin.getBatiment(), null, bulletin.getIsDeleted()));


        for (String resp : bulletin.getBulletinImpots()) {
            bulletinImpotRespository.save(
                    new BulletinImpot(null, Double.parseDouble(resp.substring(resp.lastIndexOf('-') + 1)),
                            impotRespository.findById(Long.parseLong(resp.substring(0, resp.lastIndexOf('-')))).get(),
                            bulletin1));
        }


        impositionRespository.save(
                new Imposition("AIM"+bulletin1.getCode().substring(2), null, bulletin1, null));

        return bulletin1;
    }

    @PutMapping(value = "/bulletin/{id}")
    public Bulletin updateBulletin(@PathVariable(value = "id") String id, @RequestBody Bulletin bulletin) {
        bulletin.setCode(id);
        return bulletinRespository.save(bulletin);
    }

    @PatchMapping(value = "/bulletin/{id}")
    public Bulletin updateBulletinOneItem(@PathVariable(value = "id") String id, @RequestBody Boolean status) {
        Bulletin bulletin = bulletinRespository.findById(id).get();
        bulletin.setStatus(status);
        return bulletinRespository.save(bulletin);
    }

    @GetMapping(value = "/bulletin-by")
    public List<Bulletin> getBulletinBy(@RequestParam("batiment") String batiment,
                                        @RequestParam("code") String code) {

        return bulletinRespository.getBulletinBy(code, batiment);
    }

    @GetMapping(value = "/bulletin/{id}")
    public Bulletin bulletin(@PathVariable(value = "id") String id) {
        return bulletinRespository.findById(id).get();
    }

    @GetMapping(value = "/bulletin")
    public List<Bulletin> bulletins() {
        return bulletinRespository.findAll();
    }

    @GetMapping(value = "/bulletin-max")
    public String getBulletinMax() {
        return bulletinRespository.getBulletinMax();
    }


}
@Data
class BulletinImpotModel{
    String code;
    LocalDate dateBL;
    String originService;
    int periodDebutAnnee;
    int periodDebutMois;
    int periodFinAnnee;
    int periodFinMois;
    Boolean status;
    Batiment batiment;
    List<String> bulletinImpots;
    Boolean isDeleted;
}