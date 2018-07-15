package psm.percentile.common.model.chart;

import java.util.List;

/**
 * Created by Patrycja on 27.08.2017.
 */
public class ChartOfPercentileSample {

    String label;
    List<Double> data;

    public ChartOfPercentileSample() {
    }

    public ChartOfPercentileSample(String label, List<Double> data) {
        this.label = label;
        this.data = data;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }
}
