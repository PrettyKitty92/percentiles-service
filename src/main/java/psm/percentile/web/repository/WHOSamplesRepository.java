package psm.percentile.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.Sample;
import psm.percentile.common.model.UnitType;

import java.util.List;

/**
 * Created by Patrycja on 15.05.2017.
 */

public interface WHOSamplesRepository extends MongoRepository<Sample, String> {

    List<Sample> findAll();

    Sample findByMeasurementTypeAndChildSexAndUnitValueForParameterX(MeasurementType type, ChildSex sex, double unitValue);
    Sample findByMeasurementTypeAndUnitTypeForParameterXAndChildSexAndUnitValueForParameterX(MeasurementType type, UnitType unitType, ChildSex sex, double unitValue);

}



