package psm.percentile.common.model.chart;


import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.UnitType;

import java.util.List;

public class ChartOfPercentileSamples {
    MeasurementType measurementType;
    ChildSex childSex;
    UnitType unitType;
    double unitValue;
    double startUnitValue;
    double finalUnitValue;

    List<String> labels;
    List<ChartOfPercentileSample> samples;


    public ChartOfPercentileSamples() {
    }

    public ChartOfPercentileSamples(ChartOfPercentileSamplesBuilder builder) {
        this.measurementType = builder.getMeasurementType();
        this.childSex = builder.getChildSex();
        this.unitType = builder.getUnitType();
        this.startUnitValue = builder.getStartUnitValue();
        this.finalUnitValue = builder.getFinalUnitValue();
        this.labels = builder.getLabels();
        this.samples = builder.getSamples();
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public ChildSex getChildSex() {
        return childSex;
    }

    public void setChildSex(ChildSex childSex) {
        this.childSex = childSex;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(double unitValue) {
        this.unitValue = unitValue;
    }

    public double getStartUnitValue() {
        return startUnitValue;
    }

    public void setStartUnitValue(double startUnitValue) {
        this.startUnitValue = startUnitValue;
    }

    public double getFinalUnitValue() {
        return finalUnitValue;
    }

    public void setFinalUnitValue(double finalUnitValue) {
        this.finalUnitValue = finalUnitValue;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<ChartOfPercentileSample> getSamples() {
        return samples;
    }


    public void setSamples(List<ChartOfPercentileSample> samples) {
        this.samples = samples;
    }


}
