package psm.percentile.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psm.percentile.common.model.*;
import psm.percentile.web.repository.WHOSamplesRepository;

import java.util.List;

@Service
public class MeasurementService implements IMeasurementService {

    @Autowired
    WHOSamplesRepository samplesRepository;

    @Override
    public Sample getDetails(SampleKey key) {
        if (MeasurementType.WEIGHT_FOR_LENGTH.equals(key.getMeasurementType())) {
            return samplesRepository.findByMeasurementTypeAndUnitTypeForParameterXAndChildSexAndUnitValueForParameterX(
                    MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT,
                    UnitType.LENGTH,
                    key.getSex(),
                    key.getParameterX());
        } else if (MeasurementType.WEIGHT_FOR_HEIGHT.equals(key.getMeasurementType())) {
            return samplesRepository.findByMeasurementTypeAndUnitTypeForParameterXAndChildSexAndUnitValueForParameterX(
                    MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT,
                    UnitType.HEIGHT,
                    key.getSex(),
                    key.getParameterX());
        } else {
            return samplesRepository.findByMeasurementTypeAndChildSexAndUnitValueForParameterX(
                    key.getMeasurementType(),
                    key.getSex(),
                    key.getParameterX());
        }
    }

    @Override
    public Sample updateLMS(SampleKey key, LambdaMuSigmaProperties lms) {
        Sample sample = getDetails(key);
        sample.setLambdaMuSigmaProperties(lms);
        return samplesRepository.save(sample);
    }

    @Override
    public Sample updatePercentilesValues(SampleKey key, List<ValuePerPercentile> percentiles) {
        Sample sample = getDetails(key);
        sample.setValuePerPercentiles(percentiles);
        return samplesRepository.save(sample);
    }
}
