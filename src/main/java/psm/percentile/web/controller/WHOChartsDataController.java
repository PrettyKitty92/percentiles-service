package psm.percentile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.*;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.web.converter.WHOChartDataService;
import psm.percentile.web.service.exception.BadRequestParamsException;


@RestController
@RequestMapping("/samples/charts")
@CrossOrigin(origins = "http://localhost:4200")
public class WHOChartsDataController {


    @Autowired
    WHOChartDataService whoChartDataService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello in Percentile World!";
    }

    @GetMapping(value="/{measurementType}/{childSex}")
    ResponseEntity retrieveSampleByMeasurementTypeAndAndChildSexByUnitTypeForParameterX(
            @PathVariable MeasurementType measurementType,
            @PathVariable ChildSex childSex,
            @RequestParam UnitType unitTypeForParameterX){

        ChartOfPercentileSamples chartData = new ChartOfPercentileSamples();
        try {
            chartData = whoChartDataService.prepareChartDataByUnitTypeForParameterX(measurementType, childSex, unitTypeForParameterX);
        } catch (Throwable ex) {
            if (ex instanceof BadRequestParamsException) {
                return new ResponseEntity( ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(chartData, HttpStatus.OK);
    }

    @GetMapping(value="/result/{measurementType}/{childSex}")
    ResponseEntity retrieveSampleByMeasurementTypeAndAndChildSexFotValuePerPercentileInPeriod(@PathVariable MeasurementType measurementType, @PathVariable ChildSex childSex,
                                                                                              @RequestParam UnitType unitTypeForParameterX, @RequestParam String unitValue,
                                                                                              @RequestParam double valuePerPercentile, @RequestParam int percentile,
                                                                                              @RequestParam(required=false) String birthDate){
        ChartOfPercentileSamples chartData = new ChartOfPercentileSamples();
        try {
            chartData = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue, new ValuePerPercentile(percentile, valuePerPercentile), birthDate);

        } catch (Throwable ex) {
            if (ex instanceof BadRequestParamsException) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(chartData, HttpStatus.OK);
    }
}
