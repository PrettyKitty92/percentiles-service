package psm.percentile.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psm.percentile.common.model.*;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.model.user.Baby;
import psm.percentile.common.model.user.UserAccount;
import psm.percentile.common.tools.DataConverter;
import psm.percentile.web.converter.WHOChartDataService;
import psm.percentile.web.repository.UsersRepository;
import psm.percentile.web.service.exception.BadRequestParamsException;
import psm.percentile.web.service.exception.UsernameExistsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private DataConverter dataConverter;


    @Autowired
    WHOChartDataService whoChartDataService;

    @Autowired
    PercentileCalculationService calculationService;


    @Override
    public ApplicationUser registerNewUserAccount(UserAccount account, String authority) throws UsernameExistsException {
        if (usernameExist(account.getUsername())) {
            throw new UsernameExistsException("There is an account with that username: " + account.getUsername());

            //return 409 status code: 409  message: applicationUser already exist
        }
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName(account.getFirstName());
        applicationUser.setLastName(account.getLastName());
        applicationUser.setUsername(account.getUsername());
        applicationUser.setEmail(account.getEmail());
        applicationUser.setPassword(account.getPassword());
        applicationUser.setAuthorities(Arrays.asList(authority));
        return repository.save(applicationUser);
    }

    @Override
    public ApplicationUser findUserDetailsByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public ApplicationUser addBabyForUser(Baby baby, String username) {
        ApplicationUser user = repository.findByUsername(username);
        user.getBabies().add(baby);
        return repository.save(user);
    }


    @Override
    public BabyAge getBabyAge(String userName, String babyName) {
        Baby currentBaby = repository.findByUsername(userName).getBabies().stream()
                .filter(baby -> babyName.toUpperCase().equals(baby.getName().toUpperCase()))
                .collect(Collectors.toList())
                .get(0);

        return dataConverter.convertToBabyAge(currentBaby.getDateOfBirth());
    }

    @Override
    public ValuePerPercentile calculateAndSaveMeasurement(String babyName, String userName, MeasurementType measurementType, UnitType unitTypeForParameterX, UnitType unitTypeForParameterY, String unitValueForParameterX, double childMeasure, String dayOfLife, boolean save) throws BadRequestParamsException {
        ApplicationUser user = repository.findByUsername(userName);
        Baby currentBaby = user.getBabies().stream()
                .filter(baby -> babyName.toUpperCase().equals(baby.getName().toUpperCase()))
                .collect(Collectors.toList())
                .get(0);
        ValuePerPercentile result;
        if (unitTypeForParameterX.equals(UnitType.AGE_BY_DATE)) {
            int days = dataConverter.convertPeriodToDays(currentBaby.getDateOfBirth(), unitValueForParameterX);
            unitValueForParameterX = String.valueOf(days);
            result = calculationService.findValueBy(measurementType, currentBaby.getSex(), UnitType.AGE_BY_DAY, unitTypeForParameterY, days, childMeasure);
        } else {
            result = calculationService.findValueBy(measurementType, currentBaby.getSex(), unitTypeForParameterX, unitTypeForParameterY, Double.valueOf(unitValueForParameterX), childMeasure);
        }


        if (save) {

            if(currentBaby.getMeasurements()
                    .stream()
                    .filter(measurement ->
                            measurement.getMeasurementType().equals(measurementType)
                                    && measurement.getParameterXUnitType().equals(unitTypeForParameterX)
                                    && measurement.getParameterYUnitType().equals(unitTypeForParameterY)
                    ).count() > 0 ){
                throw new BadRequestParamsException("MEASUREMENT_ALREADY_EXIST");
            }

            Measurement measurement = new Measurement();
            if (measurementType.equals(MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT)) {
                measurement.setDataOfBabiesLife(Integer.valueOf(dayOfLife));
            } else {
                measurement.setDataOfBabiesLife(Integer.valueOf(unitValueForParameterX));
            }
            measurement.setMeasurementType(measurementType);
            measurement.setParameterXUnitType(unitTypeForParameterX);
            measurement.setParameterYUnitType(unitTypeForParameterY);
            measurement.setParameterXUnitValue(Double.valueOf(unitValueForParameterX));
            measurement.setParameterYUnitValue(childMeasure);
            measurement.setPercentile(result.getPercentile());

            currentBaby.addMeasurement(measurement);
            user.updateBaby(currentBaby);
            repository.save(user);
        }


        return result;
    }

    @Override
    public List<Measurement> getAllBabyMeasurements(String userName, String babyName, MeasurementType measurementType) {
        Baby baby = findBabyBy(userName, babyName);
        return baby.getMeasurements().stream().filter(measurement -> measurementType.equals(measurement.getMeasurementType())).collect(Collectors.toList());
    }

    @Override
    public List<Measurement> getBabyMeasurementsFromDayToDay(String userName, String babyName, MeasurementType measurementType, int from, int to) {
        Baby baby = findBabyBy(userName, babyName);
        return baby.getMeasurements().stream().filter(measurement -> measurementType.equals(measurement.getMeasurementType()) && from <= measurement.getDataOfBabiesLife() && to >= measurement.getDataOfBabiesLife()).collect(Collectors.toList());
    }

    public List<Measurement> getBabyMeasurementsFromDateToDate(String userName, String babyName, MeasurementType measurementType, String from, String to) {
        Baby baby = findBabyBy(userName, babyName);
        int formInDays = dataConverter.convertPeriodToDays(baby.getDateOfBirth(), from);
        int toInDays = dataConverter.convertPeriodToDays(baby.getDateOfBirth(), to);
        return baby.getMeasurements().stream().filter(measurement -> measurementType.equals(measurement.getMeasurementType()) && formInDays <= measurement.getDataOfBabiesLife() && toInDays >= measurement.getDataOfBabiesLife()).collect(Collectors.toList());
    }

    public List<Measurement> getBabyMeasurementsFromDateToDate(String userName, String babyName, MeasurementType measurementType, LocalDate from, LocalDate to) {
        Baby baby = findBabyBy(userName, babyName);
        int formInDays = dataConverter.convertPeriodToDays(baby.getDateOfBirth(), from);
        int toInDays = dataConverter.convertPeriodToDays(baby.getDateOfBirth(), to);
        return baby.getMeasurements().stream().filter(measurement -> measurementType.equals(measurement.getMeasurementType()) && formInDays <= measurement.getDataOfBabiesLife() && toInDays >= measurement.getDataOfBabiesLife()).collect(Collectors.toList());
    }

    @Override
    public ChartOfPercentileSamples getAllBabyMeasurementsChart(String userName, String babyName, MeasurementType measurementType) {
        Baby baby = findBabyBy(userName, babyName);
        List<Measurement> measurements = getAllBabyMeasurements(userName, babyName, measurementType);
        if (measurements.size() > 0) {
            return this.whoChartDataService.prepareChartForMeasurement(measurements, baby.getSex());
        } else {
            return new ChartOfPercentileSamples();
        }
    }

    @Override
    public ChartOfPercentileSamples getBabyMeasurementsFromDayToDayChart(String userName, String babyName, MeasurementType measurementType, int from, int to) {
        Baby baby = findBabyBy(userName, babyName);
        List<Measurement> measurements = getBabyMeasurementsFromDayToDay(userName, babyName, measurementType, from, to);
        if (measurements.size() > 0) {
            return this.whoChartDataService.prepareChartForMeasurement(measurements, baby.getSex());
        } else {
            return new ChartOfPercentileSamples();
        }

    }

    @Override
    public ChartOfPercentileSamples getBabyMeasurementsFromDateToDateChart(String userName, String babyName, MeasurementType measurementType, String from, String to) {
        Baby baby = findBabyBy(userName, babyName);
        List<Measurement> measurements = getBabyMeasurementsFromDateToDate(userName, babyName, measurementType, from, to);
        if (measurements.size() > 0) {
            return this.whoChartDataService.prepareChartForMeasurement(measurements, baby.getSex());
        } else {
            return new ChartOfPercentileSamples();
        }

    }

    @Override
    public ChartOfPercentileSamples comparisionChartFor(String name, String[] babies, MeasurementType measurementType) {
        List<Baby> userBabies = new ArrayList<>();
        for (String baby : babies) {
            userBabies.add(findBabyBy(name, baby));
        }
        Baby youngestChild = findYoungestChild(userBabies);
        LocalDate from = youngestChild.getDateOfBirth();
        LocalDate to = LocalDate.now();

        if (!MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT.equals(measurementType)) {
            return whoChartDataService.prepareComparisionChartByValue(userBabies, 1, dataConverter.convertPeriodToDays(from, to), measurementType);
        } else {
            return null;
        }
    }

    private Baby findYoungestChild(List<Baby> userBabies) {
        Baby youngest = userBabies.get(0);
        for (Baby baby : userBabies) {
            if (youngest.getDateOfBirth().isBefore(baby.getDateOfBirth())) {
                youngest = baby;
            }
        }
        return youngest;
    }


    private Baby findBabyBy(String username, String babyName) {
        return repository.findByUsername(username).getBabies().stream()
                .filter(baby -> babyName.toUpperCase().equals(baby.getName().toUpperCase()))
                .collect(Collectors.toList())
                .get(0);
    }


    private boolean usernameExist(String username) {
        ApplicationUser applicationUser = repository.findByUsername(username);
        return applicationUser != null;
    }


}
