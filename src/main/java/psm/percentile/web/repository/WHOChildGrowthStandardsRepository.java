package psm.percentile.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.Sample;
import psm.percentile.common.model.UnitType;

import java.util.Collection;
import java.util.List;

/**
 * Created by Patrycja on 15.05.2017.
 */

public interface WHOChildGrowthStandardsRepository extends MongoRepository<Sample, String> {

    List<Sample> findAll();

    List<Sample> findByUnitValueForParameterX(double unitValue);

    Sample findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterX(MeasurementType measurementType, ChildSex childSex, UnitType unitType, double unitValue);

    List<Sample> findByMeasurementTypeAndChildSex(MeasurementType measurementType, ChildSex childSex);

    List<Sample> findByMeasurementTypeAndChildSexAndUnitValueForParameterXBetween(MeasurementType measurementType, ChildSex childSex, double startUnitValue, double finalUnitValue);

    List<Sample> findByMeasurementTypeAndChildSexAndUnitTypeForParameterXAndUnitValueForParameterXBetween(MeasurementType measurementType, ChildSex childSex, UnitType unitType, double startUnitValue, double finalUnitValue);


    List<Sample> findByMeasurementTypeAndUnitValueForParameterX(MeasurementType measurementType, double unitValue);

    List<Sample> findByMeasurementTypeAndChildSexAndUnitTypeForParameterX(MeasurementType measurementType, ChildSex childSex, UnitType unitType);

   List<Sample> findByMeasurementTypeAndChildSexAndUnitTypeForParameterXOrderByUnitTypeForParameterXAsc(MeasurementType measurementType, ChildSex childSex, UnitType unitType);

    List<Sample> findByMeasurementTypeAndChildSexAndUnitValueForParameterX(MeasurementType measurementType, ChildSex sex, int dayOfLife);


    //   List<Sample> findByMeasurementTypeAndChildSexAndUnitTypeForParameterX(MeasurementType measurementType, ChildSex childSex, UnitType unitType);



}



