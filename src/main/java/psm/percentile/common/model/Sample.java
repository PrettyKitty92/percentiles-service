package psm.percentile.common.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrycja on 15.05.2017.
 */
@Document(collection = "WHOChildGrowthStandards")
public class Sample {

    @Id
    private ObjectId id;

    private MeasurementType measurementType;
    private ChildSex childSex;
    private double unitValueForParameterX;
    private UnitType unitTypeForParameterX;
    private UnitType unitTypeForParameterY;
    private LambdaMuSigmaProperties lambdaMuSigmaProperties;
    private double standardDeviation;

    List<ValuePerPercentile> valuePerPercentiles = new ArrayList<ValuePerPercentile>();

    public Sample() {
    }

    public Sample(MeasurementType measurementType, ChildSex childSex, double unitValue, UnitType parameterX, UnitType parameterY, LambdaMuSigmaProperties lambdaMuSigmaProperties, double standardDeviation, List<ValuePerPercentile> valuePerPercentiles) {
        this.measurementType = measurementType;
        this.childSex = childSex;
        this.unitValueForParameterX = unitValue;
        this.unitTypeForParameterX = parameterX;
        this.unitTypeForParameterY = parameterY;
        this.lambdaMuSigmaProperties = lambdaMuSigmaProperties;
        this.standardDeviation = standardDeviation;
        this.valuePerPercentiles = valuePerPercentiles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public double getUnitValueForParameterX() {
        return unitValueForParameterX;
    }

    public void setUnitValueForParameterX(double unitValueForParameterX) {
        this.unitValueForParameterX = unitValueForParameterX;
    }

    public UnitType getUnitTypeForParameterX() {
        return unitTypeForParameterX;
    }

    public void setUnitTypeForParameterX(UnitType unitTypeForParameterX) {
        this.unitTypeForParameterX = unitTypeForParameterX;
    }

    public UnitType getUnitTypeForParameterY() {
        return unitTypeForParameterY;
    }

    public void setUnitTypeForParameterY(UnitType unitTypeForParameterY) {
        this.unitTypeForParameterY = unitTypeForParameterY;
    }

    public LambdaMuSigmaProperties getLambdaMuSigmaProperties() {
        return lambdaMuSigmaProperties;
    }

    public void setLambdaMuSigmaProperties(LambdaMuSigmaProperties lambdaMuSigmaProperties) {
        this.lambdaMuSigmaProperties = lambdaMuSigmaProperties;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public List<ValuePerPercentile> getValuePerPercentiles() {
        return valuePerPercentiles;
    }

    public void setValuePerPercentiles(List<ValuePerPercentile> valuePerPercentiles) {
        this.valuePerPercentiles = valuePerPercentiles;
    }
}
