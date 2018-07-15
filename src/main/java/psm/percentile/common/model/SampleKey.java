package psm.percentile.common.model;

public class SampleKey {

    private MeasurementType measurementType;
    private ChildSex sex;
    private double parameterX;

    public SampleKey(MeasurementType type, ChildSex sex, double parameterX) {
        this.measurementType = type;
        this.sex = sex;
        this.parameterX = parameterX;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public ChildSex getSex() {
        return sex;
    }

    public void setSex(ChildSex sex) {
        this.sex = sex;
    }

    public double getParameterX() {
        return parameterX;
    }

    public void setParameterX(double parameterX) {
        this.parameterX = parameterX;
    }
}
