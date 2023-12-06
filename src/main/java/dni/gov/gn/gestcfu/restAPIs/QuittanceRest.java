package dni.gov.gn.gestcfu.restAPIs;


import dni.gov.gn.gestcfu.entities.Quittance;
import dni.gov.gn.gestcfu.repositories.QuittanceRespository;
import dni.gov.gn.gestcfu.services.UtilService;
import dni.gov.gn.gestcfu.util.GeneratePdfReport;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class QuittanceRest {

    @Autowired
    private QuittanceRespository quittanceRespository;
    @Autowired
    private GeneratePdfReport generatePdfReport;

    @PostMapping(value = "/quittance")
    public Quittance addQuittance(@RequestBody Quittance quittance) {
        return quittanceRespository.save(quittance);
    }

    @PutMapping(value = "/quittance/{id}")
    public Quittance updateQuittance(@PathVariable(value = "id") String id, @RequestBody Quittance quittance) {
        quittance.setCode(id);
        return quittanceRespository.save(quittance);
    }

    @GetMapping(value = "/quittance-by")
    public List<Quittance> getQuittanceBy(@RequestParam("batiment") String batiment,
                                          @RequestParam("code") String code) {

        return quittanceRespository.getQuittanceBy(code, batiment);
    }

    @GetMapping(value = "/quittance-pdf")
    public ResponseEntity<InputStreamResource> pdfQuittance(@RequestParam(name="code")String code){



        ByteArrayInputStream in = generatePdfReport.quittancePdf( quittanceRespository.findById(code).get());

        //return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=quittance.pdf");
        //headers.setContentType(MediaType.parseMediaType("application/pdf"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }

    @PatchMapping(value = "/quittance/{id}")
    public Quittance updateQuittanceOneItem(@PathVariable(value = "id") String id) {
        Quittance quittance = quittanceRespository.findById(id).get();
        quittance.setDuplicata(String.valueOf((Integer.parseInt(quittance.getDuplicata()) + 1)));
        return quittanceRespository.save(quittance);
    }

    @GetMapping(value = "/quittance/{id}")
    public Quittance quittance(@PathVariable(value = "id") String id) {
        return quittanceRespository.findById(id).get();
    }

    @GetMapping(value = "/quittance")
    public List<Quittance> quittances() {
        return quittanceRespository.findAll();
    }

    @GetMapping(value = "/quittance-max")
    public String getQuittanceMax() {
        return quittanceRespository.getQuittanceMax();
    }

}
