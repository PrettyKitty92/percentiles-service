package psm.percentile.common.model.user;



import com.fasterxml.jackson.annotation.JsonFormat;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.Measurement;
import psm.percentile.common.model.MeasurementType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class







Baby {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private String name;
    private ChildSex sex;
    private List<Measurement> measurements = new ArrayList<>();

    public Baby() {
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildSex getSex() {
        return sex;
    }

    public void setSex(ChildSex sex) {
        this.sex = sex;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(Measurement measurement){
        this.measurements.add(measurement);
    }

    public Measurement getMeasurementPerDay(int i, MeasurementType measurementType) {
        List<Measurement> ms = measurements.stream().filter(
                measurement ->
                        measurement.getDataOfBabiesLife() == i && measurement.getMeasurementType().equals(measurementType)
        ).collect(Collectors.toList());
        if(ms.size()>0){
            return ms.get(0);
        }else{
            return null;
        }
    }
}
