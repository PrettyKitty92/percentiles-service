package psm.percentile.common.model;

/**
 * Created by Patrycja on 11.09.2017.
 */
public class AdditionalInfo {

    private AdditionalInfoType intormation;
    private boolean value;

    public AdditionalInfo() {
    }

    public AdditionalInfoType getIntormation() {
        return intormation;
    }

    public void setIntormation(AdditionalInfoType intormation) {
        this.intormation = intormation;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
