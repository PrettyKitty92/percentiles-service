package psm.percentile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.Sample;
import psm.percentile.common.model.UnitType;
import psm.percentile.web.repository.WHOChildGrowthStandardsRepository;
import psm.percentile.web.service.helper.GettingSampleHelper;

import java.util.Collection;

/**
 * Created by Patrycja on 15.05.2017.
 */
@RestController
@RequestMapping("/samples")
@CrossOrigin(origins = "http://localhost:4200")
public class WHOChildGrowthStandardsController {

    @Autowired
    WHOChildGrowthStandardsRepository repository;

    @Autowired
    GettingSampleHelper gettingSampleHelper;


    Collection<Sample> retrieveSamples() {
        return repository.findAll();
    }

    @GetMapping(params = "unitValue") ////add UnitTypeForX
    Collection<Sample> retrieveSample(@RequestParam double unitValue) {
        return repository.findByUnitValueForParameterX(unitValue);
    }

    @GetMapping(value = "/{measurementType}", params = {"unitValue"}) ////add UnitTypeForX
    Collection<Sample> retrieveSampleByMeasurementType(@PathVariable MeasurementType measurementType, @RequestParam double unitValue) {
        return repository.findByMeasurementTypeAndUnitValueForParameterX(measurementType, unitValue);
    }

    @GetMapping(value = "/{measurementType}/{childSex}", params = {"unitTypeForParameterX", "unitValue"}) //add UnitTypeForX
    Sample retrieveSampleByMeasurementTypeAndAndChildSex(@PathVariable MeasurementType measurementType, @PathVariable ChildSex childSex,
                                                         @RequestParam UnitType unitTypeForParameterX, @RequestParam double unitValue) {
        gettingSampleHelper.resolveUnitValue(unitTypeForParameterX,unitValue);
        return repository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurementType, childSex, unitTypeForParameterX, unitValue);
    }

    @GetMapping(value = "/{measurementType}/{childSex}") //add UnitTypeForX
    Collection<Sample> retrieveSampleByMeasurementTypeAndAndChildSex(@PathVariable MeasurementType measurementType, @PathVariable ChildSex childSex) {
        return repository.findByMeasurementTypeAndChildSex(measurementType, childSex);
    }

    @GetMapping(value = "/{measurementType}/{childSex}", params = {"startUnitValue", "finalUnitValue"}) //add UnitTypeForX
    Collection<Sample> retrieveSampleByMeasurementTypeAndAndChildSex(@PathVariable MeasurementType measurementType, @PathVariable ChildSex childSex, @RequestParam double startUnitValue, @RequestParam double finalUnitValue) {
        return repository.findByMeasurementTypeAndChildSexAndUnitValueForParameterXBetween(measurementType, childSex, startUnitValue, finalUnitValue);
    }
}
