package psm.percentile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.web.service.IBabySampleGenerator;

@Controller
@RequestMapping("/percentiles")
public class BabySampleGeneratorController {

    @Autowired
    IBabySampleGenerator babySampleGenerator;


    @PostMapping("/generate/measurements/for/user")
    public ResponseEntity<Object> signUpUser(@RequestParam String username)  {
        ApplicationUser user;
        try {
            user = babySampleGenerator.generateBabyMeasurementForUser(username);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
