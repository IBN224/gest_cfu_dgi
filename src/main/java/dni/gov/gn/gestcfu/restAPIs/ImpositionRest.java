package dni.gov.gn.gestcfu.restAPIs;


import dni.gov.gn.gestcfu.entities.Imposition;
import dni.gov.gn.gestcfu.repositories.ImpositionRespository;
import dni.gov.gn.gestcfu.util.GeneratePdfAim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ImpositionRest {

    @Autowired
    private ImpositionRespository impositionRespository;


    @PostMapping(value = "/imposition")
    public Imposition addImposition(@RequestBody Imposition imposition) {
        return impositionRespository.save(imposition);
    }

    @PutMapping(value = "/imposition/{id}")
    public Imposition updateImposition(@PathVariable(value = "id") String id, @RequestBody Imposition imposition) {
        imposition.setCode(id);
        return impositionRespository.save(imposition);
    }

    @GetMapping(value = "/imposition-by")
    public List<Imposition> getImpositionBy(@RequestParam("batiment") String batiment,
                                            @RequestParam("code") String code) {

        return impositionRespository.getImpositionBy(code, batiment);
    }

    @GetMapping(value = "/imposition/{id}")
    public Imposition bulletin(@PathVariable(value = "id") String id) {
        return impositionRespository.findById(id).get();
    }

    @GetMapping(value = "/imposition")
    public List<Imposition> impositions() {
        return impositionRespository.findAll();
    }

    @GetMapping(value = "/imposition-pdf")
    public ResponseEntity<InputStreamResource> pdfImposition(@RequestParam(name="code")String code){


        ByteArrayInputStream in = GeneratePdfAim.impositionPdf( impositionRespository.findById(code).get());

        //return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=aim.pdf");
        //headers.setContentType(MediaType.parseMediaType("application/pdf"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }


}
