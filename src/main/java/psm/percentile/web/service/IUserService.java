package psm.percentile.web.service;

import psm.percentile.common.model.*;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.model.user.Baby;
import psm.percentile.common.model.user.UserAccount;
import psm.percentile.web.service.exception.BadRequestParamsException;
import psm.percentile.web.service.exception.UsernameExistsException;

import java.util.List;

/**
 * Created by Patrycja on 01.09.2017.
 */
public interface IUserService {
    ApplicationUser registerNewUserAccount(UserAccount accountDto, String authority)
            throws UsernameExistsException;

    ApplicationUser findUserDetailsByUsername(String username);

    ApplicationUser addBabyForUser(Baby baby, String name);

    BabyAge getBabyAge(String userName, String babyName);

    ValuePerPercentile calculateAndSaveMeasurement(String babyName, String userName, MeasurementType measurementType, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, String unitValueForParameterX, double childMeasure, String dayOfLife, boolean save) throws BadRequestParamsException;

    List<Measurement> getAllBabyMeasurements(String userName, String babyName, MeasurementType measurementType);

    List<Measurement> getBabyMeasurementsFromDayToDay(String userName, String babyName, MeasurementType measurementType, int from, int to);

    List<Measurement> getBabyMeasurementsFromDateToDate (String userName, String babyName, MeasurementType measurementType, String from, String to);

    ChartOfPercentileSamples getAllBabyMeasurementsChart(String userName, String babyName, MeasurementType measurementType);

    ChartOfPercentileSamples getBabyMeasurementsFromDayToDayChart(String userName, String babyName, MeasurementType measurementType, int from, int to);

    ChartOfPercentileSamples getBabyMeasurementsFromDateToDateChart(String userName, String babyName, MeasurementType measurementType, String from, String to);

    ChartOfPercentileSamples comparisionChartFor(String name, String[] babies, MeasurementType measurementType);


}


