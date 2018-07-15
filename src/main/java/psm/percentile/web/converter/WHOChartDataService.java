package psm.percentile.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import psm.percentile.common.model.*;
import psm.percentile.common.model.chart.ChartOfPercentileSample;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.common.model.chart.ChartOfPercentileSamplesBuilder;
import psm.percentile.common.model.user.Baby;
import psm.percentile.common.tools.DataConverter;
import psm.percentile.common.tools.PercentileConverter;
import psm.percentile.web.repository.WHOChildGrowthStandardsRepository;
import psm.percentile.web.service.exception.BadRequestParamsException;
import psm.percentile.web.service.helper.GettingSampleHelper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.math3.util.Precision.round;

@Component
public class WHOChartDataService {


    @Autowired
    WHOChildGrowthStandardsRepository whoRepository;


    @Autowired
    GettingSampleHelper gettingSampleHelper;

    @Autowired
    private DataConverter dataConverter;

    @Autowired
    PercentileConverter percentileConverter;

    private final List<String> percentiles = Arrays.asList("0", "1", "3", "5", "10", "15", "25", "50", "75", "85", "90", "95", "97", "99", "100");


    private static final String MEASUREMENT_TYPE_DOES_NOT_EXIST = "MEASUREMENT_TYPE_DOES_NOT_EXIST";
    private static final String CANNOT_USE_VALUE_BELOW_91_DAYS = "CANNOT_USE_VALUE_BELOW_91_DAYS";
    private static final String BAD_MEASUREMENT_PARAMETERS = "BAD_MEASUREMENT_PARAMETERS";
    private static final String MEASUREMENT_OUT_OF_BOUND = "MEASUREMENT_OUT_OF_BOUND";

    private static final int DAY_ZERO = 0;
    private static final int TREE_MONTHS_IN_DAYS = 91;
    private static final int TWO_YEARS_IN_DAYS = 730;
    private static final int LAST_DAYS_OF_MEASUREMENT = 1856;


    // po wyliczeniu percentyla wołamy funkcje
    public ChartOfPercentileSamples prepareChartDataForValuePerPercentileInPeriod(
            MeasurementType measurementType,
            ChildSex childSex,
            UnitType unitTypeForParameterX,
            String unitValueForParameterX,
            ValuePerPercentile valuePerPercentile,
            String birthDate) throws BadRequestParamsException {

        if (MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT
                .equals(measurementType)) {
            return getChartOfSamplesForLengthOrHeight(
                    unitTypeForParameterX,
                    valuePerPercentile,
                    unitValueForParameterX,
                    measurementType,
                    childSex);
        } else {

            int ageInDays = convertIntoDayValue(unitTypeForParameterX,
                    unitValueForParameterX,
                    birthDate);

            if (Arrays.asList(MeasurementType.TRICEPS_SKINFOLD_FOR_AGE,
                    MeasurementType.SUBSCAPULAR_SKINFOLD_FOR_AGE,
                    MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE)
                    .contains(measurementType)) {
                if (ageInDays < TREE_MONTHS_IN_DAYS) {
                    throw new BadRequestParamsException(CANNOT_USE_VALUE_BELOW_91_DAYS);
                } else {
                    return getChartOfSamplesForAge(
                            TREE_MONTHS_IN_DAYS,
                            valuePerPercentile,
                            ageInDays,
                            measurementType,
                            childSex);
                }
            } else {
                return getChartOfSamplesForAge(
                        DAY_ZERO,
                        valuePerPercentile,
                        ageInDays,
                        measurementType,
                        childSex);
            }
        }
    }

    //for unlogged calculation
    // zwraca sample dla wszytskich typow poza WEIGHT_FOR_LENGTH_HEIGHT : funkcja ustala wartosci graniczne dla badanych zakresów
    private ChartOfPercentileSamples getChartOfSamplesForAge(
            int minXValue,
            ValuePerPercentile valuePerPercentile,
            int babysAgeInDays,
            MeasurementType measurementType,
            ChildSex childSex) throws BadRequestParamsException {

        int startDay, endDay, babyIndex, interval;

        if (minXValue == DAY_ZERO) {
            if (babysAgeInDays >= 0 && babysAgeInDays < TREE_MONTHS_IN_DAYS) {
                interval = 1;
                if ((babysAgeInDays - 7) >= DAY_ZERO) { //
                    startDay = babysAgeInDays - 7;
                    babyIndex = 7;
                } else {
                    babyIndex = babysAgeInDays;
                    startDay = DAY_ZERO;
                }
                endDay = babysAgeInDays + 7;
            } else if (babysAgeInDays < TWO_YEARS_IN_DAYS) {
                interval = 7;
                startDay = babysAgeInDays - 35;
                endDay = babysAgeInDays + 35;
                babyIndex = 5;
            } else if (babysAgeInDays <= LAST_DAYS_OF_MEASUREMENT) {
                interval = 14;
                startDay = babysAgeInDays - 84;
                babyIndex = 6;
                if ((babysAgeInDays + 84) > LAST_DAYS_OF_MEASUREMENT) {
                    int x = (LAST_DAYS_OF_MEASUREMENT - babysAgeInDays) / interval;
                    endDay = babysAgeInDays + (x * interval);
                } else {
                    endDay = babysAgeInDays + 84;
                }
            } else {
                throw new BadRequestParamsException(BAD_MEASUREMENT_PARAMETERS);
            }
        } else if (minXValue == TREE_MONTHS_IN_DAYS) {
            if (babysAgeInDays < TREE_MONTHS_IN_DAYS || babysAgeInDays > LAST_DAYS_OF_MEASUREMENT) {
            }
            if (babysAgeInDays < TWO_YEARS_IN_DAYS) { // miary powyzejsz 3 mieisecy dla dzieci do 2 lat
                interval = 7;
                if ((babysAgeInDays - 35) > TREE_MONTHS_IN_DAYS) { //
                    startDay = babysAgeInDays - 35;
                    babyIndex = 5;
                } else {
                    babyIndex = ((int) (babysAgeInDays - TREE_MONTHS_IN_DAYS) / interval);
                    startDay = babysAgeInDays - (babyIndex * interval);
                }
                endDay = babysAgeInDays + 35;
            } else { // miary powyzejsz 3 mieisecy dla dzieci od 2 lat
                interval = 14;
                startDay = babysAgeInDays - 84;
                babyIndex = 6;
                if ((babysAgeInDays + 84) > LAST_DAYS_OF_MEASUREMENT) {
                    int x = (LAST_DAYS_OF_MEASUREMENT - babysAgeInDays) / interval;
                    endDay = babysAgeInDays + (x * interval);
                } else {
                    endDay = babysAgeInDays + 84;
                }
            }
        } else {
            throw new BadRequestParamsException(BAD_MEASUREMENT_PARAMETERS);

        }
        List<Sample> samples = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterX(measurementType, childSex, UnitType.AGE_BY_DAY);
        samples = samples.stream()
                .filter(sample -> sample.getUnitValueForParameterX() >= startDay)
                .filter(sample -> sample.getUnitValueForParameterX() <= endDay)
                .filter(sample -> (sample.getUnitValueForParameterX() - startDay) % interval == 0)
                .collect(Collectors.toList());

        samples.sort(Comparator.comparing(sample -> sample.getUnitValueForParameterX()));


        ChartOfPercentileSamples chart = new ChartOfPercentileSamplesBuilder(measurementType, childSex)
                .setMeasurementXParamType(UnitType.AGE_BY_DAY)
                .setStartValue(startDay)
                .setEndValue(endDay)
                .setInterval(interval)
                .setSamples(samples)
                .setLabels(samples)
                .setYourBaby(babyIndex, valuePerPercentile.getValue())
                .build();

        return chart;
    }

    private ChartOfPercentileSamples getChartOfSamplesForLengthOrHeight(
            UnitType unitTypeForParameterX,
            ValuePerPercentile valuePerPercentile, String unitValueForParameterX,
            MeasurementType measurementType,
            ChildSex childSex) throws BadRequestParamsException {

        int babyIndex;
        double startValue, endValue, interval = 0.5;
        double maxValue, minValue;
        double babyLengthOrHeight = Double.valueOf(unitValueForParameterX);

        if (UnitType.LENGTH.equals(unitTypeForParameterX)) {
            if (babyLengthOrHeight < 45.0 || babyLengthOrHeight > 110.0)
                throw new BadRequestParamsException(MEASUREMENT_OUT_OF_BOUND);
            minValue = 45.0;
            maxValue = 110.0;
        } else if (UnitType.HEIGHT.equals(unitTypeForParameterX)) {
            if (babyLengthOrHeight < 65.0 || babyLengthOrHeight > 120)
                throw new BadRequestParamsException(MEASUREMENT_OUT_OF_BOUND);
            minValue = 65.0;
            maxValue = 120.0;
        } else {
            throw new BadRequestParamsException(BAD_MEASUREMENT_PARAMETERS);
        }

        if (babyLengthOrHeight - minValue < 5.0) {
            int x = (int) ((babyLengthOrHeight - minValue) / interval);
            startValue = babyLengthOrHeight - (x * interval);
            endValue = babyLengthOrHeight + 5.0;
            babyIndex = (int) ((babyLengthOrHeight - minValue) / 0.5);
        } else if (babyLengthOrHeight + 5 > maxValue) {
            startValue = babyLengthOrHeight - 5;
            endValue = maxValue;
            babyIndex = 10;
        } else {
            startValue = babyLengthOrHeight - 5;
            endValue = babyLengthOrHeight + 5;
            babyIndex = 10;
        }


        //getSamples for period
        List<Sample> samples = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterX(measurementType, childSex, unitTypeForParameterX)
                .stream()
                .filter(sample -> sample.getUnitValueForParameterX() >= startValue)
                .filter(sample -> sample.getUnitValueForParameterX() <= endValue)
                .filter(sample -> (sample.getUnitValueForParameterX() - startValue) % interval == 0.0)
                .collect(Collectors.toList());

        samples.sort(Comparator.comparing(sample -> sample.getUnitValueForParameterX()));
        //crateChartOfPercentiles
        ChartOfPercentileSamples chart = new ChartOfPercentileSamplesBuilder(measurementType, childSex)
                .setMeasurementXParamType(unitTypeForParameterX)
                .setStartValue(startValue)
                .setEndValue(endValue)
                .setInterval(interval)
                .setSamples(samples)
                .setLabels(samples)
                .setYourBaby(babyIndex, valuePerPercentile.getValue())
                .build();

        return chart;
    }


    public ChartOfPercentileSamples prepareChartDataByUnitTypeForParameterX(MeasurementType measurementType, ChildSex childSex, UnitType unitTypeForParameterX) {

        int interval;
        UnitType unitTypeLabel;
        if (MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT
                .equals(measurementType)) {
            interval = 5;
            unitTypeLabel = unitTypeForParameterX;
        } else {
            interval = 61;
            unitTypeLabel = UnitType.AGE_BY_MONTH;
        }

        List<Sample> samples =
                whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterX(
                        measurementType,
                        childSex,
                        unitTypeForParameterX
                )
                        .stream()
                        .filter(sample ->
                                (sample.getUnitValueForParameterX()) % interval == 0.0)
                        .collect(Collectors.toList());

        samples.sort(Comparator.comparing(
                sample -> sample.getUnitValueForParameterX()));

        ChartOfPercentileSamples chart =
                new ChartOfPercentileSamplesBuilder(measurementType, childSex)
                        .setMeasurementXParamType(unitTypeForParameterX)
                        .setSamples(samples)
                        .setLabels(samples, unitTypeLabel)
                        .build();
        return chart;

    }

    /***************************************************************/

    public ChartOfPercentileSamples prepareChartForMeasurement(List<Measurement> measurements, ChildSex sex) {

        ChartOfPercentileSamples chartData = new ChartOfPercentileSamples();
        chartData.setChildSex(sex);
        chartData.setMeasurementType(measurements.get(0).getMeasurementType());

        List<String> labels = new ArrayList();

        List<ChartOfPercentileSample> chartDataSamples = new ArrayList<>();
        Map<String, List<Double>> sets = new TreeMap<>();

        for (String percentile : percentiles) {
            sets.put(percentile, new ArrayList<Double>());
        }
        sets.put("Your baby", new ArrayList<Double>());
        // labels.add("Your baby");


        measurements.sort(Comparator.comparing(a -> a.getDataOfBabiesLife()));

        for (Measurement measurement : measurements) {
            LambdaMuSigmaProperties lms;
            if (!MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT.equals(measurement.getMeasurementType())) {
                lms = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurement.getMeasurementType(), sex, UnitType.AGE_BY_DAY, measurement.getDataOfBabiesLife()).getLambdaMuSigmaProperties();
            } else {
                lms = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurement.getMeasurementType(), sex, measurement.getParameterXUnitType(), measurement.getParameterXUnitValue()).getLambdaMuSigmaProperties();
            }
            for (String percentile : percentiles) {
                double value = calculateValueFor(percentile, lms);
                sets.get(percentile).add(value);
            }
            sets.get("Your baby").add(measurement.getParameterYUnitValue());
            labels.add(String.valueOf(measurement.getDataOfBabiesLife()));
        }

        sets.forEach((label, data) -> {
            chartDataSamples.add(new ChartOfPercentileSample(label, data));
        });

        chartData.setLabels(labels);
        chartData.setSamples(chartDataSamples);
        return chartData;
    }


    private Double calculateValueFor(String label, LambdaMuSigmaProperties lms) {
        return percentileConverter.percentileToValue(Integer.valueOf(label), lms);
    }


    private int convertIntoDayValue(UnitType unitTypeForParameterX, String unitValue, String birthDate) {


        int days;
        if (UnitType.AGE_BY_DATE.equals(unitTypeForParameterX)) {
            days = dataConverter.convertPeriodToDays(birthDate, unitValue);
        } else if ((UnitType.AGE_BY_BIRTHDAY.equals(unitTypeForParameterX))) {
            if (birthDate == null || birthDate.isEmpty()) {
                days = dataConverter.convertPeriodToDays(unitValue);
            } else {
                days = dataConverter.convertPeriodToDays(LocalDate.now(), unitValue);
            }
        } else if ((UnitType.AGE_BY_MONTH.equals(unitTypeForParameterX))) {
            days = (int) (30.4 * Integer.valueOf(unitValue));
        } else if ((UnitType.AGE_BY_WEEK.equals(unitTypeForParameterX))) {
            days = 7 * Integer.valueOf(unitValue);
        } else {

            days = Integer.valueOf(unitValue);
        }
        return days;
    }


    public ChartOfPercentileSamples prepareComparisionChartByValue(List<Baby> userBabies, int from, int to, MeasurementType measurementType) {
        ChartOfPercentileSamples chartData = new ChartOfPercentileSamples();
        chartData.setMeasurementType(measurementType);
        chartData.setUnitType(UnitType.AGE_BY_DAY);
        chartData.setFinalUnitValue(to);
        chartData.setStartUnitValue(from);

        List<String> labels = new ArrayList();
        List<ChartOfPercentileSample> chartDataSamples = new ArrayList<>();
        Map<String, List<Double>> sets = new TreeMap<>();
        for (Baby baby : userBabies) {
            sets.put(baby.getName(), new ArrayList<Double>());
        }

        for (int i = from; i <= to; i++) {
            labels.add(String.valueOf(i));
            for (Baby baby : userBabies) {
                Measurement measurement = baby.getMeasurementPerDay(i, measurementType);
                sets.get(baby.getName()).add(measurement != null ? measurement.getParameterYUnitValue() : null);
            }
        }

        sets.forEach((label, data) -> {
            chartDataSamples.add(new ChartOfPercentileSample(label, data));
        });

        chartData.setLabels(labels);
        chartData.setSamples(chartDataSamples);
        return chartData;
    }
}
