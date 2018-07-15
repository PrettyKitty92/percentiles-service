package psm.percentile.common.model;

/**
 * Created by Patrycja on 19.09.2017.
 */
public class BabyAge {

    private int days;
    private int months;
    private int years;
    private long allInDays;

    public BabyAge() {
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public long getAllInDays() {
        return allInDays;
    }

    public void setAllInDays(long allInDays) {
        this.allInDays = allInDays;
    }
}
