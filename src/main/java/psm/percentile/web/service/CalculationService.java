package psm.percentile.web.service;

import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.UnitType;
import psm.percentile.common.model.ValuePerPercentile;
import psm.percentile.web.service.exception.BadRequestParamsException;


public interface CalculationService {

    ValuePerPercentile findValueBy(MeasurementType measurementType, ChildSex childSex, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, double unitValue, double childMeasure) throws BadRequestParamsException;

    ValuePerPercentile calculateValueBy(MeasurementType measurementType, ChildSex childSex, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, String unitValueForParameterX, double childMeasure) throws BadRequestParamsException;
}
