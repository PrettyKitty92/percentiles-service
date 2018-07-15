package psm.percentile.web.service.helper;

import org.springframework.stereotype.Component;
import psm.percentile.common.model.UnitType;

/**
 * Created by Patrycja on 14.09.2017.
 */
@Component
public class GettingSampleHelper {

    public static final int MAX_WEEK_NO = 13;
    public static final int DAYS_BY_ONE_WEEK = 7;
    public static final double DAYS_BY_ONE_MONTH = 30.4;

    public double resolveUnitValue(UnitType unitTypeForParameterX, double unitValue) {
        if (UnitType.AGE_BY_WEEK.equals(unitTypeForParameterX)) {
            if ((int) unitValue > MAX_WEEK_NO) {
                unitValue = convertWeekIntoDays(unitValue);
            }
        }
        return unitValue;
    }

    public int resolveUnitValueAsDays(UnitType unitTypeForParameterX, double unitValue) {
        if (UnitType.AGE_BY_WEEK.equals(unitTypeForParameterX)) {
            return convertWeekIntoDays(unitValue);
        } else if (UnitType.AGE_BY_MONTH.equals(unitTypeForParameterX)){
            return convertMonthIntoDays(unitValue);
        } else {
            return (int) unitValue;
        }
    }

    private int convertWeekIntoDays(double unitValue) {
        return DAYS_BY_ONE_WEEK * (int) unitValue;
    }

    public int convertMonthIntoDays(double unitValue) {
        return  (int) (DAYS_BY_ONE_MONTH * (int) unitValue);
    }
}
