package psm.percentile.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psm.percentile.common.model.*;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.model.user.Baby;
import psm.percentile.common.tools.DataConverter;
import psm.percentile.web.converter.WHOChartDataService;
import psm.percentile.web.repository.UsersRepository;
import psm.percentile.web.repository.WHOChildGrowthStandardsRepository;
import psm.percentile.web.service.exception.BadRequestParamsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BabySampleGenerator implements IBabySampleGenerator {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private WHOChildGrowthStandardsRepository whoRepository;

    @Autowired
    private DataConverter dataConverter;


    @Autowired
    WHOChartDataService whoChartDataService;

    @Autowired
    PercentileCalculationService calculationService;

    @Override
    public ApplicationUser generateBabyMeasurementForUser(String username) {

        ApplicationUser applicationUser = repository.findByUsername(username);

        List<Baby> newBabeies = new ArrayList<>();
        for (Baby baby : applicationUser.getBabies()) {
            baby.setMeasurements(generateMeasurementsList(baby));
            newBabeies.add(baby);
        }
        applicationUser.setBabies(newBabeies);
        repository.save(applicationUser);
        return applicationUser;
    }

    public List<Measurement> generateMeasurementsList(Baby baby) {


        List<Measurement> measurements = new ArrayList<>();

        LocalDate dateOfBirth = baby.getDateOfBirth();
        int babyAgeInDays = dataConverter.convertPeriodToDays(dateOfBirth, LocalDate.now());

        for (int i = 0; i <= babyAgeInDays; i++) {
            int finalI = i;
            for (MeasurementType measurementType : MeasurementType.values()) {
                try {
                    if (MeasurementType.WEIGHT_FOR_LENGTH.equals(measurementType) || MeasurementType.WEIGHT_FOR_HEIGHT.equals(measurementType)) {
                        continue;
                    }
                    Measurement m = generateMeasurementForTypeAndDay(measurementType, finalI, baby.getSex(), baby.getName());
                    if (m != null) {
                        measurements.add(m);
                    }
                } catch (BadRequestParamsException e) {
                    e.printStackTrace();
                }
            }
        }
        return measurements;
    }

    private Measurement generateMeasurementForTypeAndDay(MeasurementType measurementType, int dayOfLife, ChildSex sex, String name) throws BadRequestParamsException {

        Measurement measurement = new Measurement();

        double unitValueForParameterX = 0.0, unitValueForParameterY = 0.0;
        UnitType unitTypeForParameterX, unitTypeForParameterY;
        ValuePerPercentile result;

        if (measurementType.equals(MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT)) {
            if (dayOfLife == 100) {
                int o = 0;
            }
            unitTypeForParameterY = UnitType.WEIGHT;
            measurement.setDataOfBabiesLife(Integer.valueOf(dayOfLife));
            if (dayOfLife < 731) {
                unitTypeForParameterX = UnitType.LENGTH;
            } else {
                unitTypeForParameterX = UnitType.HEIGHT;
            }

            // to do setup  double unitValueForParameterX=0.0, unitValueForParameterY = 0.0;

            unitValueForParameterX = resolveXValueForLengthHeight(measurementType, unitTypeForParameterX, unitTypeForParameterY, sex, dayOfLife, name);
            unitValueForParameterY = resolveYValueForLengthHeight(measurementType, unitTypeForParameterX, unitTypeForParameterY, sex, dayOfLife, name);
            result = calculationService.calculateValueBy(measurementType, sex, unitTypeForParameterX, unitTypeForParameterY, String.valueOf(unitValueForParameterX), unitValueForParameterY);
        } else {

            if (dayOfLife == 99) {
                int o = 0;
            }
            unitTypeForParameterY = resolveUnitType(measurementType);
            unitTypeForParameterX = UnitType.AGE_BY_DAY;
            unitValueForParameterX = dayOfLife;
            measurement.setDataOfBabiesLife((int) unitValueForParameterX);


            unitValueForParameterY = resolveYValue(measurementType, unitTypeForParameterX, unitTypeForParameterY, sex, dayOfLife, name);

            if (unitValueForParameterY == -1.0) {
                return null;
            }

            result = calculationService.calculateValueBy(measurementType, sex, unitTypeForParameterX, unitTypeForParameterY, String.valueOf(unitValueForParameterX), unitValueForParameterY);
        }
        measurement.setMeasurementType(measurementType);
        measurement.setParameterXUnitType(unitTypeForParameterX);
        measurement.setParameterYUnitType(unitTypeForParameterY);
        measurement.setParameterXUnitValue(unitValueForParameterX);
        measurement.setParameterYUnitValue(unitValueForParameterY);


        measurement.setPercentile(result.getPercentile());
        System.out.println("*********************");
        System.out.println(measurement.getDataOfBabiesLife());
        System.out.println("*********************");
        return measurement;
    }

    private double resolveXValueForLengthHeight(MeasurementType measurementType, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, ChildSex sex, int dayOfLife, String name) {

        double minValue, maxValue, xValue;


        if (UnitType.LENGTH.equals(unitTypeForParameterX)) {
            minValue = 45.0;
            maxValue = 110.0;
            if (ChildSex.BOY.equals(sex)) {

                xValue = 50.0 + (dayOfLife * 0.07);

            } else {
                if (name.equalsIgnoreCase("Ania")) {
                    xValue = 45.0 + (dayOfLife * 0.8);
                }
                xValue = 50.0 + (dayOfLife * 0.06);
            }
        } else {
            minValue = 65.0;
            maxValue = 120.0;
            if (ChildSex.BOY.equals(sex)) {
                xValue = 50.0 + (dayOfLife * 0.07) + (dayOfLife * 0.01);
            } else {
                if (name.equalsIgnoreCase("Ania")) {
                    if (dayOfLife < 100) {
                        xValue = 45.0 + (dayOfLife * 0.06);
                    } else if (dayOfLife < 500) {
                        xValue = 45.0 + (dayOfLife * 0.05);
                    } else if (dayOfLife < 1200) {
                        xValue = 45.0 + (dayOfLife * 0.04);
                    } else {
                        xValue = 45.0 + (dayOfLife * 0.07);
                    }
                } else {
                    if (dayOfLife < 100) {
                        xValue = 50.0 + (dayOfLife * 0.01);
                    } else if (dayOfLife < 500) {
                        xValue = 50.0 + (dayOfLife * 0.03);
                    } else if (dayOfLife < 1200) {
                        xValue = 50.0 + (dayOfLife * 0.01);
                    } else {
                        xValue = 50.0 + (dayOfLife * 0.01);
                    }
                }
            }
        }
        if (xValue < minValue) {
            xValue = minValue;
        }
        if (xValue > maxValue) {
            xValue = maxValue;
        }
        return xValue;
    }

    private double resolveYValue(MeasurementType measurementType, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, ChildSex sex, int dayOfLife, String name) {

        double yValue = 0.0;
        switch (measurementType) {
            case WEIGHT_FOR_AGE:
            case HEAD_CIRCUMFERENCE_FOR_AGE:
            case BMI_FOR_AGE:
            case LENGTH_HEIGHT_FOR_AGE:

                yValue = resolveYValueFromDay0(measurementType, sex, dayOfLife, name);
                break;
            case TRICEPS_SKINFOLD_FOR_AGE:
            case SUBSCAPULAR_SKINFOLD_FOR_AGE:
            case ARM_CIRCUMFERENCE_FOR_AGE:

                yValue = resolveYValueFrom3Month(measurementType, sex, dayOfLife, name);
                break;
        }

        return yValue;
    }

    private double resolveYValueForLengthHeight(MeasurementType measurementType, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, ChildSex sex, int dayOfLife, String name) {
        double startValue, yValue;

        if (ChildSex.BOY.equals(sex)) {
            startValue = 4.00;
        } else {
            startValue = 3.50;
        }

        if (ChildSex.BOY.equals(sex)) {
            yValue = startValue + (dayOfLife * 0.008);
        } else {
            if (name.equalsIgnoreCase("Ania")) {
                if (dayOfLife < 100) {
                    yValue = startValue + (dayOfLife * 0.004);
                } else if (dayOfLife < 500) {
                    yValue = startValue + (dayOfLife * 0.005);
                } else if (dayOfLife < 1200) {
                    yValue = startValue + (dayOfLife * 0.006);
                } else {
                    yValue = startValue + (dayOfLife * 0.007);
                }
            } else {
                if (dayOfLife < 100) {
                    yValue = startValue + (dayOfLife * 0.009);
                } else if (dayOfLife < 500) {
                    yValue = startValue + (dayOfLife * 0.005);
                } else if (dayOfLife < 1200) {
                    yValue = startValue + (dayOfLife * 0.007);
                } else {
                    yValue = startValue + (dayOfLife * 0.006);
                }
            }
        }

        return yValue;
    }

    private double resolveYValueFrom3Month(MeasurementType measurementType, ChildSex sex, int dayOfLife, String name) {
        if (dayOfLife < 91) {
            return -1.0;
        }

        List<Sample> sample = whoRepository.findByMeasurementTypeAndChildSexAndUnitValueForParameterX(measurementType, sex, dayOfLife);
        ValuePerPercentile percentil50 = sample.get(0).getValuePerPercentiles().stream().filter(val -> val.getPercentile() == 50).collect(Collectors.toList()).get(0);


        double yValue = 0.0;

        switch (measurementType) {

            case TRICEPS_SKINFOLD_FOR_AGE:
                yValue = findTricepsSkindfold(sex, dayOfLife, name, percentil50.getValue());
                break;
            case SUBSCAPULAR_SKINFOLD_FOR_AGE:
                yValue = findSubscapularSkindfold(sex, dayOfLife, name, percentil50.getValue());
                break;
            case ARM_CIRCUMFERENCE_FOR_AGE:
                yValue = findArmCircumference(sex, dayOfLife, name, percentil50.getValue());
                break;
        }
        return yValue;
    }

    private double findArmCircumference(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 0.5;
        return findY(sex, dayOfLife, name, interval, percentile50value);
    }

    private double findSubscapularSkindfold(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 0.5;
        return findY(sex, dayOfLife, name, interval, percentile50value);

    }

    private double findTricepsSkindfold(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 0.5;
        return findY(sex, dayOfLife, name, interval, percentile50value);
    }


    private double

    resolveYValueFromDay0(MeasurementType measurementType, ChildSex sex, int dayOfLife, String name) {


        List<Sample> sample = whoRepository.findByMeasurementTypeAndChildSexAndUnitValueForParameterX(measurementType, sex, dayOfLife);
        ValuePerPercentile percentil50 = sample.get(0).getValuePerPercentiles().stream().filter(val -> val.getPercentile() == 50).collect(Collectors.toList()).get(0);

        double yValue = 0.0;
        switch (measurementType) {
            case WEIGHT_FOR_AGE:
                yValue = findWeight(sex, dayOfLife, name, percentil50.getValue());
                break;
            case HEAD_CIRCUMFERENCE_FOR_AGE:
                yValue = findHeadCircumference(sex, dayOfLife, name, percentil50.getValue());
                break;
            case BMI_FOR_AGE:
                yValue = findBmi(sex, dayOfLife, name, percentil50.getValue());
            case LENGTH_HEIGHT_FOR_AGE:
                yValue = findLengthHeigth(sex, dayOfLife, name, percentil50.getValue());
                break;
        }
        return yValue;
    }

    private double findWeight(ChildSex sex, int dayOfLife, String name, double percentile50value) {

        double interval = 0.5;
        return findY(sex, dayOfLife, name, interval, percentile50value);
    }

    private double findHeadCircumference(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 0.5;
        return findY(sex, dayOfLife, name, interval, percentile50value);

    }

    private double findBmi(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 1;
        return findY(sex, dayOfLife, name, interval, percentile50value);

    }

    private double findLengthHeigth(ChildSex sex, int dayOfLife, String name, double percentile50value) {
        double interval = 2;
        return findY(sex, dayOfLife, name, interval, percentile50value);

    }

    private double findY(ChildSex sex, int dayOfLife, String name, double interval, double percentile50value) {
        double yValue = 0.0;


        if (ChildSex.BOY.equals(sex)) {
                if (dayOfLife < 3) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 6) {
                    yValue = percentile50value + 0.2 * interval;
                } else if (dayOfLife < 9) {
                    yValue = percentile50value + 0.3 * interval;
                } else if (dayOfLife < 12) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 15) {
                    yValue = percentile50value + 0.7 * interval;
                } else if (dayOfLife < 18) {
                    yValue = percentile50value + 0.8 * interval;
                } else if (dayOfLife < 21) {
                    yValue = percentile50value + 1 * interval;
                } else if (dayOfLife < 24) {
                    yValue = percentile50value + 0.8 * interval;
                } else if (dayOfLife < 27) {
                    yValue = percentile50value + 0.7 * interval;
                } else if (dayOfLife < 30) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 35) {
                    yValue = percentile50value + 0.4 * interval;
                } else if (dayOfLife < 40) {
                    yValue = percentile50value + 0.2 * interval;
                } else if (dayOfLife < 43) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 46) {
                    yValue = percentile50value ;
                } else if (dayOfLife < 49) {
                    yValue = percentile50value - 0.1 * interval;
                } else if (dayOfLife < 52) {
                    yValue = percentile50value - 0.2 * interval;
                } else if (dayOfLife < 55) {
                    yValue = percentile50value - 0.3 * interval;
                } else if (dayOfLife < 58) {
                    yValue = percentile50value - 0.5 * interval;
                } else if (dayOfLife < 60) {
                    yValue = percentile50value - 0.6 * interval;
                } else {
                    yValue = percentile50value + interval;
                }

        } else {
            if (name.equalsIgnoreCase("Ania")) {
                if (dayOfLife < 10) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 13) {
                    yValue = percentile50value + 0.7 * interval;
                } else if (dayOfLife < 20) {
                    yValue = percentile50value + 0.8 * interval;
                } else if (dayOfLife < 25) {
                    yValue = percentile50value + interval;
                } else if (dayOfLife < 30) {
                    yValue = percentile50value + 0.8 * interval;
                } else if (dayOfLife < 32) {
                    yValue = percentile50value + 0.7 * interval;
                } else if (dayOfLife < 34) {
                    yValue = percentile50value + 0.6 * interval;
                } else if (dayOfLife < 36) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 38) {
                    yValue = percentile50value + 0.4 * interval;
                } else if (dayOfLife < 40) {
                    yValue = percentile50value + 0.3 * interval;
                } else if (dayOfLife < 45) {
                    yValue = percentile50value + 0.2 * interval;
                } else if (dayOfLife < 50) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 55) {
                    yValue = percentile50value;
                } else if (dayOfLife < 70) {
                    yValue = percentile50value - 0.1 * interval;
                } else if (dayOfLife < 80) {
                    yValue = percentile50value - 0.2 * interval;
                } else if (dayOfLife < 90) {
                    yValue = percentile50value - 0.3 * interval;
                } else if (dayOfLife < 120) {
                    yValue = percentile50value - 0.4 * interval;
                } else if (dayOfLife < 160) {
                    yValue = percentile50value - 0.5 * interval;
                } else if (dayOfLife < 170) {
                    yValue = percentile50value - 0.6 * interval;
                } else if (dayOfLife < 180) {
                    yValue = percentile50value - 0.5 * interval;
                } else if (dayOfLife < 200) {
                    yValue = percentile50value - 0.4 * interval;
                } else if (dayOfLife < 220) {
                    yValue = percentile50value - 0.3 * interval;
                } else if (dayOfLife < 300) {
                    yValue = percentile50value - 0.2 * interval;
                } else if (dayOfLife < 400) {
                    yValue = percentile50value - 0.1 * interval;
                } else if (dayOfLife < 500) {
                    yValue = percentile50value;
                } else if (dayOfLife < 600) {
                    yValue = percentile50value - 0.1 * interval;
                } else if (dayOfLife < 650) {
                    yValue = percentile50value - 0.3 * interval;
                } else if (dayOfLife < 700) {
                    yValue = percentile50value - 0.4 * interval;
                } else if (dayOfLife < 720) {
                    yValue = percentile50value - 0.6 * interval;
                } else if (dayOfLife < 800) {
                    yValue = percentile50value - 0.8 * interval;
                } else {
                    yValue = percentile50value + interval;
                }
            } else {
                if (dayOfLife < 10) {
                    yValue = percentile50value - 0.5 * interval;
                } else if (dayOfLife < 13) {
                    yValue = percentile50value - 0.7 * interval;
                } else if (dayOfLife < 20) {
                    yValue = percentile50value - 0.85 * interval;
                } else if (dayOfLife < 25) {
                    yValue = percentile50value - interval;
                } else if (dayOfLife < 30) {
                    yValue = percentile50value - 0.8 * interval;
                } else if (dayOfLife < 32) {
                    yValue = percentile50value - 0.7 * interval;
                } else if (dayOfLife < 34) {
                    yValue = percentile50value - 0.6 * interval;
                } else if (dayOfLife < 36) {
                    yValue = percentile50value - 0.5 * interval;
                } else if (dayOfLife < 38) {
                    yValue = percentile50value - 0.4 * interval;
                } else if (dayOfLife < 40) {
                    yValue = percentile50value - 0.3 * interval;
                } else if (dayOfLife < 45) {
                    yValue = percentile50value - 0.2 * interval;
                } else if (dayOfLife < 50) {
                    yValue = percentile50value - 0.1 * interval;
                } else if (dayOfLife < 55) {
                    yValue = percentile50value;
                } else if (dayOfLife < 70) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 80) {
                    yValue = percentile50value + 0.2 * interval;
                } else if (dayOfLife < 90) {
                    yValue = percentile50value + 0.3 * interval;
                } else if (dayOfLife < 120) {
                    yValue = percentile50value + 0.4 * interval;
                } else if (dayOfLife < 160) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 170) {
                    yValue = percentile50value + 0.6 * interval;
                } else if (dayOfLife < 180) {
                    yValue = percentile50value + 0.5 * interval;
                } else if (dayOfLife < 200) {
                    yValue = percentile50value + 0.4 * interval;
                } else if (dayOfLife < 220) {
                    yValue = percentile50value + 0.3 * interval;
                } else if (dayOfLife < 300) {
                    yValue = percentile50value + 0.2 * interval;
                } else if (dayOfLife < 400) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 500) {
                    yValue = percentile50value;
                } else if (dayOfLife < 600) {
                    yValue = percentile50value + 0.1 * interval;
                } else if (dayOfLife < 650) {
                    yValue = percentile50value + 0.3 * interval;
                } else if (dayOfLife < 700) {
                    yValue = percentile50value + 0.4 * interval;
                } else if (dayOfLife < 720) {
                    yValue = percentile50value + 0.6 * interval;
                } else if (dayOfLife < 800) {
                    yValue = percentile50value + 0.8 * interval;
                } else {
                    yValue = percentile50value + interval;
                }
            }
        }
        return yValue;
    }


        private UnitType resolveUnitType (MeasurementType measurementType){
            UnitType unitType = null;

            switch (measurementType) {
                case WEIGHT_FOR_AGE:
                    unitType = UnitType.WEIGHT;
                    break;
                case BMI_FOR_AGE:
                    unitType = UnitType.BMI;
                    break;
                case HEAD_CIRCUMFERENCE_FOR_AGE:
                    unitType = UnitType.HEAD_CIRCUMFERENCE;
                    break;
                case ARM_CIRCUMFERENCE_FOR_AGE:
                    unitType = UnitType.ARM_CIRCUMFERENCE;
                    break;
                case TRICEPS_SKINFOLD_FOR_AGE:
                    unitType = UnitType.TRICEPS_SKINFOLD;
                    break;
                case LENGTH_HEIGHT_FOR_AGE:
                    unitType = UnitType.LENGTH_HEIGHT;
                    break;
                case SUBSCAPULAR_SKINFOLD_FOR_AGE:
                    unitType = UnitType.SUBSCAPULAR_SKINFOLD;
                    break;
            }

            return unitType;
        }




}
