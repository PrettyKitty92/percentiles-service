package psm.percentile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.UnitType;
import psm.percentile.common.model.ValuePerPercentile;
import psm.percentile.web.service.PercentileCalculationService;
import psm.percentile.web.service.exception.BadRequestParamsException;

/**
 * Created by Patrycja on 16.05.2017.
 */
@RestController
@RequestMapping("/percentiles")
@CrossOrigin(origins = "http://localhost:4200")
public class CalculationController {

    @Autowired
    PercentileCalculationService service;

    @GetMapping(value = "/{measurementType}/{childSex}", params = {"unitTypeForParameterX", "unitValueForParameterX", "unitTypeForParameterY", "childMeasure"})
    ResponseEntity<ValuePerPercentile> retrievePercentil(@PathVariable MeasurementType measurementType, @PathVariable ChildSex childSex,

                                                         @RequestParam UnitType unitTypeForParameterX, @RequestParam String unitValueForParameterX,
                                                         UnitType unitTypeForParameterY, @RequestParam double childMeasure) {

        ValuePerPercentile percentile = new ValuePerPercentile();
        try {
            percentile = service.calculateValueBy(measurementType, childSex, unitTypeForParameterX, unitTypeForParameterY, unitValueForParameterX, childMeasure);
        } catch (Throwable ex) {
            if (ex instanceof BadRequestParamsException) {
                return new ResponseEntity( ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(percentile, HttpStatus.OK);

    }


}








