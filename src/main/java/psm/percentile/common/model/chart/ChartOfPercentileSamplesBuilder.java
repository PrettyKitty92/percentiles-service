package psm.percentile.common.model.chart;

import psm.percentile.common.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChartOfPercentileSamplesBuilder {

    private final List<String> percentiles = Arrays.asList("0", "1", "3", "5", "10", "15", "25", "50", "75", "85", "90", "95", "97", "99", "100");

    private List<ChartOfPercentileSample> samples;
    private List<String> labels;
    private MeasurementType measurementType;
    private ChildSex childSex;
    private UnitType unitType;
    private double unitValue;
    private double startUnitValue;
    private double finalUnitValue;
    private double interval;


    public ChartOfPercentileSamplesBuilder(MeasurementType measurementType, ChildSex childSex) {
        this.measurementType = measurementType;
        this.childSex = childSex;

    }

    public ChartOfPercentileSamples build() {
        return new ChartOfPercentileSamples(this);
    }

    public ChartOfPercentileSamplesBuilder setSamples(List<Sample> samples) {
        List<ChartOfPercentileSample> chartDataSamples = new ArrayList<>();
        Map<Integer, List<Double>> sets = new TreeMap<>();

        for (String percentile : percentiles) {
            sets.put(Integer.valueOf(percentile), new ArrayList<Double>());
        }

        for (Sample sample : samples) {
            for (ValuePerPercentile value : sample.getValuePerPercentiles()) {
                sets.get(value.getPercentile()).add(value.getValue());
            }
        }

        sets.forEach((label, data) ->
                chartDataSamples.add(new ChartOfPercentileSample(String.valueOf(label), data))
        );

        this.samples = chartDataSamples;
        return this;
    }

    public ChartOfPercentileSamplesBuilder setLabels(List<Sample> samples) {
        List<Double> labelsToSort = new ArrayList<Double>();
        this.labels = new ArrayList<>();
        samples.stream().forEach(sample -> labelsToSort.add(sample.getUnitValueForParameterX()));
        (labelsToSort.stream().sorted()).forEach(
                label -> {
                    if (UnitType.HEIGHT.equals(this.unitType) || UnitType.LENGTH.equals(this.unitType)) {
                        this.labels.add(String.valueOf(label));
                    } else {
                        this.labels.add(String.valueOf(label.intValue()));
                    }
                });
        return this;
    }


    public ChartOfPercentileSamplesBuilder setLabels(List<Sample> samples, UnitType unitTypeLabel) {
        if (UnitType.AGE_BY_MONTH.equals(unitTypeLabel)) {

        } else {
            return setLabels(samples);
        }
        this.labels = new ArrayList<>();
        samples.stream().forEach(sample -> {
            Double labelInMonth = sample.getUnitValueForParameterX() / 30.4;
            int valueInMonths = labelInMonth.intValue();
            if (valueInMonths > 0 && valueInMonths % 12 == 0) {
                this.labels.add(( "rok\n" + valueInMonths / 12));
            } else {
                this.labels.add(String.valueOf(valueInMonths % 12));
            }
        });
        return this;
    }

    public ChartOfPercentileSamplesBuilder setYourBaby(int babyIndex, double value) {
        Double list[] = new Double[this.labels.size()];
        list[babyIndex] = value;
        ChartOfPercentileSample yourChildSample = new ChartOfPercentileSample();
        yourChildSample.setLabel("Your baby");
        yourChildSample.setData(Arrays.asList(list));
        this.samples.add(yourChildSample);
        return this;
    }

    public ChartOfPercentileSamplesBuilder setMeasurementXParamType(UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public ChildSex getChildSex() {
        return childSex;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public double getStartUnitValue() {
        return startUnitValue;
    }

    public double getFinalUnitValue() {
        return finalUnitValue;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<ChartOfPercentileSample> getSamples() {
        return samples;
    }

    public ChartOfPercentileSamplesBuilder setStartValue(double startDay) {
        this.startUnitValue = startDay;
        return this;
    }

    public ChartOfPercentileSamplesBuilder setEndValue(double endDay) {
        this.finalUnitValue = endDay;
        return this;
    }

    public ChartOfPercentileSamplesBuilder setInterval(double interval) {
        this.interval = interval;
        return this;
    }


}
