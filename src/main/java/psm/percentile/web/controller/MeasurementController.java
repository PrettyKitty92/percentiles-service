package psm.percentile.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.*;
import psm.percentile.web.service.IMeasurementService;
import psm.percentile.web.service.MeasurementService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Patrycja on 01.09.2017.
 */

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class MeasurementController {

    private IMeasurementService measurementService;

/*    @Autowired
    private IAuthenticationFacade authenticationFacade;*/


    public MeasurementController(MeasurementService measurementService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.measurementService = measurementService;
    }

    @GetMapping("/measurement/details")
    ResponseEntity adminDatailes(Principal user,
                                 @RequestParam MeasurementType type,
                                 @RequestParam ChildSex sex,
                                 @RequestParam double parameterX) {

        SampleKey key = new SampleKey(type,sex,parameterX);
        Sample sample = null;
        try {
             sample = measurementService.getDetails(key);
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(sample, HttpStatus.OK);
    }



    @PutMapping("/measurement/edit/lms")
    ResponseEntity editLMS(Principal user,
                                  @RequestParam MeasurementType type,
                                  @RequestParam ChildSex sex,
                                  @RequestParam double parameterX,
                                  @RequestBody LambdaMuSigmaProperties lms) {
        SampleKey key = new SampleKey(type,sex,parameterX);
        Sample sample = null;
        try {
            sample = measurementService.updateLMS(key,lms);
        } catch (Throwable ex) {
           return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/measurement/edit/percentiles")
    ResponseEntity editPercentiles(Principal user,
                                  @RequestParam MeasurementType type,
                                  @RequestParam ChildSex sex,
                                  @RequestParam double parameterX,
                                  @RequestBody List<ValuePerPercentile> perPercentileList) {
        SampleKey key = new SampleKey(type,sex,parameterX);
        Sample sample = null;
        try {
            sample = measurementService.updatePercentilesValues(key,perPercentileList);
        } catch (Throwable ex) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}



