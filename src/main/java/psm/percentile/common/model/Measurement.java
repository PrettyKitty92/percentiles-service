package psm.percentile.common.model;

import java.util.List;

/**
 * Created by Patrycja on 10.09.2017.
 */
public class Measurement {

    private MeasurementType measurementType;
    private UnitType parameterXUnitType;
    private UnitType parameterYUnitType;
    private double parameterXUnitValue;
    private double parameterYUnitValue;
    private double percentile;
    private int dataOfBabiesLife;

    private List<AdditionalInfo> additionalInformations;

    public Measurement() {
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public UnitType getParameterXUnitType() {
        return parameterXUnitType;
    }

    public void setParameterXUnitType(UnitType parameterXUnitType) {
        this.parameterXUnitType = parameterXUnitType;
    }

    public UnitType getParameterYUnitType() {
        return parameterYUnitType;
    }

    public void setParameterYUnitType(UnitType parameterYUnitType) {
        this.parameterYUnitType = parameterYUnitType;
    }

    public double getParameterXUnitValue() {
        return parameterXUnitValue;
    }

    public void setParameterXUnitValue(double parameterXUnitValue) {
        this.parameterXUnitValue = parameterXUnitValue;
    }

    public double getParameterYUnitValue() {
        return parameterYUnitValue;
    }

    public void setParameterYUnitValue(double parameterYUnitValue) {
        this.parameterYUnitValue = parameterYUnitValue;
    }

    public double getPercentile() {
        return percentile;
    }

    public void setPercentile(double percentile) {
        this.percentile = percentile;
    }

    public int getDataOfBabiesLife() {
        return dataOfBabiesLife;
    }

    public void setDataOfBabiesLife(int dataOfBabiesLife) {
        this.dataOfBabiesLife = dataOfBabiesLife;
    }

    public List<AdditionalInfo> getAdditionalInformations() {
        return additionalInformations;
    }

    public void setAdditionalInformations(List<AdditionalInfo> additionalInformations) {
        this.additionalInformations = additionalInformations;
    }
}
