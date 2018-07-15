package psm.percentile.web.service;

import psm.percentile.common.model.*;

import java.util.List;

public interface IMeasurementService {

    Sample getDetails(SampleKey key);

    Sample updateLMS(SampleKey key, LambdaMuSigmaProperties lms);

    Sample updatePercentilesValues(SampleKey key, List<ValuePerPercentile> lms);
}
