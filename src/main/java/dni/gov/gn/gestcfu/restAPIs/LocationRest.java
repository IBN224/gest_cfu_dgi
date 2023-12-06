package dni.gov.gn.gestcfu.restAPIs;

import dni.gov.gn.gestcfu.entities.Location;
import dni.gov.gn.gestcfu.repositories.LocationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class    LocationRest {

    @Autowired
    private LocationRespository locationRespository;

    @PostMapping(value = "/location")
    public Location addTypeLocation(@RequestBody Location location) {
        return locationRespository.save(location);
    }

    @PutMapping(value = "/location/{id}")
    public Location updateLocation(@PathVariable(value = "id") Long id, @RequestBody Location location) {
        location.setCode(id);
        return locationRespository.save(location);
    }

    @GetMapping(value = "/location-by")
    public List<Location> locationsBy(@RequestParam("usage") String usage,
                                      @RequestParam("type") String type,
                                      @RequestParam("batiment") String batiment,
                                      @RequestParam("contribuable") String contribuable) {
        return locationRespository.getLocationBy(usage, type, batiment, contribuable);
    }

    @GetMapping(value = "/location-montant-by")
    public double getLocationMontantBy(@RequestParam("batiment") String batiment,
                                               @RequestParam("dateDebut") String dateDebut,
                                               @RequestParam("dateFin") String dateFin) {
        if(locationRespository.getLocationMontantBy(batiment, dateDebut, dateFin)!=null)
         return Double.parseDouble(locationRespository.getLocationMontantBy(batiment, dateDebut, dateFin));
        else return 0.0;
    }

    @GetMapping(value = "/location-by-batiment")
    public List<Location> getLocationByBatiment(@RequestParam("batiment") String batiment,
                                        @RequestParam("dateDebut") String dateDebut,
                                        @RequestParam("dateFin") String dateFin) {

        return locationRespository.getLocationByBatiment(batiment, dateDebut, dateFin);
    }

    @GetMapping(value = "/location/{id}")
    public Location location(@PathVariable(value = "id") Long id) {
        return locationRespository.findById(id).get();
    }

    @GetMapping(value = "/location")
    public List<Location> locations() {
        return locationRespository.findAll();
    }
}
