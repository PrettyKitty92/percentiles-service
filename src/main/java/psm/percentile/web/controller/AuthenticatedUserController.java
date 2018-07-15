package psm.percentile.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.*;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.model.user.Baby;
import psm.percentile.web.service.UserService;
import psm.percentile.web.service.exception.BadRequestParamsException;

import java.security.Principal;
import java.util.List;

/**
 * Created by Patrycja on 01.09.2017.
 */

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticatedUserController {

    private UserService userService;




    public AuthenticatedUserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
    }

    @GetMapping(value="/user/babies/baby/{babyName}/{measurementType}", params={ "unitTypeForParameterX" , "unitValueForParameterX", "unitTypeForParameterY", "childMeasure" })
    ResponseEntity saveAndCalculateMesurement(Principal user,  @PathVariable String babyName,
                                         @PathVariable MeasurementType measurementType,
                                         @RequestParam UnitType unitTypeForParameterX, @RequestParam String unitValueForParameterX,
                                                  @RequestParam UnitType unitTypeForParameterY, @RequestParam double childMeasure,
                                                  @RequestParam String dayOfLife, @RequestParam boolean save){


        ValuePerPercentile percentile = new ValuePerPercentile();
        try {
            percentile = userService.calculateAndSaveMeasurement(babyName, user.getName(),measurementType,unitTypeForParameterX,unitTypeForParameterY,unitValueForParameterX,childMeasure, dayOfLife, save);
        } catch (Throwable ex) {
            if (ex instanceof BadRequestParamsException) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(percentile, HttpStatus.OK);

    }


    @GetMapping("/user/details")
    ApplicationUser userDatailes(Principal user) {
        return userService.findUserDetailsByUsername(user.getName());
    }


    @PostMapping("/user/babies/add")
    ApplicationUser addBaby(Principal user, @RequestBody Baby baby) {
        return userService.addBabyForUser(baby, user.getName());
    }

    @GetMapping("/user/babies/baby/{babyName}/age")
    BabyAge getBabyAge(Principal user, @PathVariable String babyName) {
        return userService.getBabyAge(user.getName(), babyName);
    }


    /*measurement history*/

    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}")
    List<Measurement> allBabyMeasurements(Principal user, @PathVariable String babyName, @PathVariable MeasurementType measurementType ) {
        List<Measurement> list = userService.getAllBabyMeasurements(user.getName(), babyName, measurementType);
        return list;
    }

    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}/periodInDays")
    List<Measurement> babyMeasurementsFromDayToDay(Principal user, @PathVariable String babyName,@PathVariable MeasurementType measurementType,
                                                     @RequestParam int from, @RequestParam int to
    ) {
        return userService.getBabyMeasurementsFromDayToDay(user.getName(), babyName, measurementType, from, to);
    }

    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}/periodInDates")
    List<Measurement>  babyMeasurementsFromDateToDate(Principal user, @PathVariable String babyName, @PathVariable MeasurementType measurementType,
                                                      @RequestParam String from, @RequestParam String to) {
        return userService.getBabyMeasurementsFromDateToDate(user.getName(), babyName, measurementType,from, to);
    }



    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}/chart")
    ChartOfPercentileSamples allBabyMeasurementsChart(Principal user, @PathVariable String babyName, @PathVariable MeasurementType measurementType ) {
        return  userService.getAllBabyMeasurementsChart(user.getName(), babyName, measurementType);
    }

    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}/periodInDays//chart")
    ChartOfPercentileSamples babyMeasurementsFromDayToDayChart(Principal user, @PathVariable String babyName,@PathVariable MeasurementType measurementType,
                                                   @RequestParam int from, @RequestParam int to
    ) {
        return userService.getBabyMeasurementsFromDayToDayChart(user.getName(), babyName, measurementType, from, to);
    }

    @GetMapping("/user/babies/baby/{babyName}/measurements/{measurementType}/periodInDates/chart")
    ChartOfPercentileSamples  babyMeasurementsFromDateToDateChart(Principal user, @PathVariable String babyName, @PathVariable MeasurementType measurementType,
                                                      @RequestParam String from, @RequestParam String to) {
        return userService.getBabyMeasurementsFromDateToDateChart(user.getName(), babyName, measurementType,from, to);
    }

    /*measurement history comparator*/

    @GetMapping("/user/babies/compare")
    ChartOfPercentileSamples compareBabies(Principal user, @RequestParam String babies, @RequestParam MeasurementType measurementType) {
        ChartOfPercentileSamples chart = userService.comparisionChartFor(user.getName(), babies.split(","), measurementType);
        return chart;
    }



}



