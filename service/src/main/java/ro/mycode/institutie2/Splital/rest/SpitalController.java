package ro.mycode.institutie2.Splital.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.institutie2.Splital.dtos.CreateSpitalRequest;
import ro.mycode.institutie2.Splital.dtos.CreateSpitalResponse;
import ro.mycode.institutie2.Splital.dtos.UpdateSpitalRequest;
import ro.mycode.institutie2.Splital.model.Spital;
import ro.mycode.institutie2.Splital.service.SpitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/splital")
@AllArgsConstructor
@Slf4j
public class SpitalController {

   private SpitalService spitalService;

    @GetMapping("/allSpitale")
    public ResponseEntity<List<Spital>> getAllSpitale() {
        List<Spital> spitale=spitalService.getAllSpitale();
        return   new ResponseEntity<>(spitale, HttpStatus.OK);
    }

    @PostMapping("/createSpital")
    public ResponseEntity<CreateSpitalResponse>addSpital(@RequestBody CreateSpitalRequest createSpitalRequest) {
        CreateSpitalResponse createSpitalResponse = spitalService.addSpital(createSpitalRequest);
        return   new ResponseEntity<>(createSpitalResponse,HttpStatus.CREATED);
    }
    @PutMapping("/updateSpital")
    public ResponseEntity<Void>updated(@RequestBody UpdateSpitalRequest updateSpitalRequest) {
        spitalService.updateSpital(updateSpitalRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void>getFindById(@PathVariable long id){
        spitalService.deleteSpital(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
