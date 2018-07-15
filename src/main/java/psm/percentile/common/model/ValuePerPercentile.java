package psm.percentile.common.model;

/**
 * Created by Patrycja on 15.05.2017.
 */
public class ValuePerPercentile {

    private int percentile;
    private double value;

    public ValuePerPercentile() {
    }

    public ValuePerPercentile(int percentile, double value) {
        this.percentile = percentile;
        this.value = value;
    }

    public int getPercentile() {
        return percentile;
    }

    public void setPercentile(int percentile) {
        this.percentile = percentile;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValuePerPercentile{" +
                "percentile=" + percentile +
                ", value=" + value +
                '}'+"\n";
    }


}
