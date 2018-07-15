package psm.percentile.common.tools;

import org.springframework.stereotype.Component;
import psm.percentile.common.model.BabyAge;
import psm.percentile.common.model.UnitType;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Component
public class DataConverter {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public BabyAge convertToBabyAge(LocalDate dateOfBirth) {
        BabyAge age = new BabyAge();
        LocalDate today = LocalDate.now();
        Period p = Period.between(dateOfBirth, today);

        age.setDays(p.getDays());
        age.setMonths(p.getMonths());
        age.setYears(p.getYears());
        age.setAllInDays( ChronoUnit.DAYS.between(dateOfBirth, today));

        return age;
    }

    public int convertPeriodToDays(String dateOfBirth) {
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(LocalDate.parse(dateOfBirth,formatter), today);
    }

    public int convertPeriodToDays(String dateOfBirth, LocalDate secondDate) {
        return (int) ChronoUnit.DAYS.between(LocalDate.parse(dateOfBirth,formatter),secondDate);
    }

    public int convertPeriodToDays(LocalDate dateOfBirth, String unitValueForParameterX) {
        return (int) ChronoUnit.DAYS.between(dateOfBirth, LocalDate.parse(unitValueForParameterX,formatter));
    }

    public int convertPeriodToDays(LocalDate dateOfBirth, LocalDate unitValueForParameterX) {
        return (int) ChronoUnit.DAYS.between(dateOfBirth, unitValueForParameterX);

    }

    public int convertPeriodToDays(String birthDate, String unitValue) {
        return (int) ChronoUnit.DAYS.between( LocalDate.parse(birthDate,formatter), LocalDate.parse(unitValue,formatter));
    }
}
