package psm.percentile.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psm.percentile.common.model.*;
import psm.percentile.common.tools.DataConverter;
import psm.percentile.common.tools.PercentileConverter;
import psm.percentile.web.repository.WHOChildGrowthStandardsRepository;
import psm.percentile.web.service.exception.BadRequestParamsException;
import psm.percentile.web.service.helper.GettingSampleHelper;

import java.time.LocalDate;

/**
 * Created by Patrycja on 16.05.2017.
 */
@Service
public class PercentileCalculationService implements CalculationService {


    @Autowired
    PercentileConverter percentileConverter;

    @Autowired
    GettingSampleHelper gettingSampleHelper;

    @Autowired
    WHOChildGrowthStandardsRepository whoRepository;

    @Autowired
    private DataConverter dataConverter;

    private final String MEASUREMENT_TYPE_DOES_NOT_EXIST = "MEASUREMENT_TYPE_DOES_NOT_EXIST";
    private final String CANNOT_USE_VALUE_BELOW_91_DAYS = "CANNOT_USE_VALUE_BELOW_91_DAYS";


    @Override
    public ValuePerPercentile calculateValueBy(MeasurementType measurementType, ChildSex childSex, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, String unitValueForParameterX, double childMeasure) throws BadRequestParamsException {
        if (unitTypeForParameterX.equals(UnitType.AGE_BY_BIRTHDAY)) {
            int days = dataConverter.convertPeriodToDays(unitValueForParameterX, LocalDate.now());
            return findValueBy(measurementType, childSex, UnitType.AGE_BY_DAY, unitTypeForParameterY, days, childMeasure);
        }
        return findValueBy(measurementType, childSex, unitTypeForParameterX, unitTypeForParameterY, Double.valueOf(unitValueForParameterX), childMeasure);
    }

    @Override // stworzyc obiekt QueryProperties i przekazywac go do requesta ??//
    public ValuePerPercentile findValueBy(MeasurementType measurementType, ChildSex childSex, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, double unitValue, double childMeasure) throws BadRequestParamsException {

        if (!MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT.equals(measurementType)) {
            unitValue = gettingSampleHelper.resolveUnitValueAsDays(unitTypeForParameterX, unitValue);
        }

        LambdaMuSigmaProperties lms;

        switch (measurementType) {

            case WEIGHT_FOR_LENGTH_HEIGHT:
                double roundedUnitValue = Math.round((unitValue) * 10) / 10.0;
                lms = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurementType, childSex, unitTypeForParameterX, roundedUnitValue).getLambdaMuSigmaProperties();
                break;

            case WEIGHT_FOR_AGE:
            case BMI_FOR_AGE:
            case HEAD_CIRCUMFERENCE_FOR_AGE:
            case LENGTH_HEIGHT_FOR_AGE:
                lms = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurementType, childSex, UnitType.AGE_BY_DAY, unitValue).getLambdaMuSigmaProperties();
                break;

            case TRICEPS_SKINFOLD_FOR_AGE:
            case SUBSCAPULAR_SKINFOLD_FOR_AGE:
            case ARM_CIRCUMFERENCE_FOR_AGE:
                if (unitValue < 91) {
                    throw new BadRequestParamsException(CANNOT_USE_VALUE_BELOW_91_DAYS);
                }
                lms = whoRepository.findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(measurementType, childSex, UnitType.AGE_BY_DAY, unitValue).getLambdaMuSigmaProperties();
                break;
            default:
                throw new BadRequestParamsException(MEASUREMENT_TYPE_DOES_NOT_EXIST);
        }

        ValuePerPercentile percentile = new ValuePerPercentile(percentileConverter.valueToPercentile(childMeasure, lms), childMeasure);
        return percentile;
    }

}
