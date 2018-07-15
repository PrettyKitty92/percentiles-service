package psm.percentile.installer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import psm.percentile.common.model.*;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.tools.DataConverter;
import psm.percentile.common.tools.PercentileConverter;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Patrycja on 15.05.2017.
 */
public class DataSetInitializator {


    private static final String rootDirectory = "D:\\Patrycja\\Projekty\\masterthesis\\docs\\who\\";
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static ObjectMapper mapper = new ObjectMapper();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DataConverter dataConverter = new DataConverter();
    private static PercentileConverter percentileConverter = new PercentileConverter();

    private static Map<LMSKey,LambdaMuSigmaProperties> lmsValues = new HashMap<LMSKey,LambdaMuSigmaProperties>();
    private static Map<LMSKey,Sample> lmsSample = new HashMap<LMSKey,Sample>();

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {
        prepareMeasurementData();
        prepareUsersData();
    }

    private static void prepareMeasurementData() throws JsonProcessingException {
        List<Sample> samples = convertFileToListOfSamples("Weight-for-age\\wfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.WEIGHT_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.WEIGHT);
        samples.addAll(convertFileToListOfSamples("Weight-for-age\\wfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.WEIGHT_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.WEIGHT));

        samples.addAll(convertFileToListOfSamples("Length-height-for-age\\lhfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.LENGTH_HEIGHT_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.LENGTH_HEIGHT));
        samples.addAll(convertFileToListOfSamples("Length-height-for-age\\lhfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.LENGTH_HEIGHT_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.LENGTH_HEIGHT));

        samples.addAll(convertFileToListOfSamples("Weight-for-length-height\\wfl_boys_p_exp.txt", ChildSex.BOY, MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT, UnitType.LENGTH, UnitType.WEIGHT));
        samples.addAll(convertFileToListOfSamples("Weight-for-length-height\\wfl_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT, UnitType.LENGTH, UnitType.WEIGHT));
        samples.addAll(convertFileToListOfSamples("Weight-for-length-height\\wfh_boys_p_exp.txt", ChildSex.BOY, MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT, UnitType.HEIGHT, UnitType.WEIGHT));
        samples.addAll(convertFileToListOfSamples("Weight-for-length-height\\wfh_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT, UnitType.HEIGHT, UnitType.WEIGHT));

        samples.addAll(convertFileToListOfSamples("Head-circumference-for-age\\hcfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.HEAD_CIRCUMFERENCE_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.HEAD_CIRCUMFERENCE));
        samples.addAll(convertFileToListOfSamples("Head-circumference-for-age\\hcfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.HEAD_CIRCUMFERENCE_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.HEAD_CIRCUMFERENCE));

        samples.addAll(convertFileToListOfSamples("Arm-circumference-for-age\\acfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.ARM_CIRCUMFERENCE));
        samples.addAll(convertFileToListOfSamples("Arm-circumference-for-age\\acfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.ARM_CIRCUMFERENCE));


        samples.addAll(convertFileToListOfSamples("BMI-for-age\\bfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.BMI_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.BMI));
        samples.addAll(convertFileToListOfSamples("BMI-for-age\\bfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.BMI_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.BMI));


        samples.addAll(convertFileToListOfSamples("Triceps-skinfold-for-age\\tsfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.TRICEPS_SKINFOLD_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.TRICEPS_SKINFOLD));
        samples.addAll(convertFileToListOfSamples("Triceps-skinfold-for-age\\tsfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.TRICEPS_SKINFOLD_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.TRICEPS_SKINFOLD));

        samples.addAll(convertFileToListOfSamples("Subscapular-skinfold-for-age\\ssfa_boys_p_exp.txt", ChildSex.BOY, MeasurementType.SUBSCAPULAR_SKINFOLD_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.SUBSCAPULAR_SKINFOLD));
        samples.addAll(convertFileToListOfSamples("Subscapular-skinfold-for-age\\ssfa_girls_p_exp.txt", ChildSex.GIRL, MeasurementType.SUBSCAPULAR_SKINFOLD_FOR_AGE, UnitType.AGE_BY_DAY, UnitType.SUBSCAPULAR_SKINFOLD));


        String jsonInString = mapper.writeValueAsString(samples);
        File file = new File("..\\db\\data.json");
        writeIntoFile(jsonInString, file);


    }

    public static void writeIntoFile(String jsonInString, File file) {
        try (FileOutputStream fop = new FileOutputStream(file)) {

            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = jsonInString.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Sample> convertFileToListOfSamples(String filePath, ChildSex childSex, MeasurementType mType, UnitType parameterX, UnitType parameterY) {

        List<Sample> samples = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(rootDirectory + filePath));
            String line = br.readLine();
            String[] percentiles = line.split("\t");
            List list = Arrays.asList(percentiles);
            System.out.println(list);
            boolean isSD = percentiles[4] == "SD" ? true : false;

            samples = new ArrayList();
            while ((line = br.readLine()) != null) {
                Sample sample = generateSamplesOfTheDay(line.split("\t"), percentiles, isSD, childSex, mType, parameterX, parameterY);
                samples.add(sample);
            }
            return samples;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    private static Sample generateSamplesOfTheDay(String[] strArgs, String[] percentiles, boolean isSD, ChildSex childSex, MeasurementType mType, UnitType parameterX, UnitType parameterY) {

        Sample sample = new Sample();

        sample.setMeasurementType(mType);
        sample.setChildSex(childSex);
        sample.setUnitTypeForParameterX(parameterX);
        sample.setUnitTypeForParameterY(parameterY);
        sample.setUnitValueForParameterX(Double.valueOf(strArgs[0]));

        LambdaMuSigmaProperties lms = new LambdaMuSigmaProperties(Double.valueOf(strArgs[1]), Double.valueOf(strArgs[2]), Double.valueOf(strArgs[3]));
        sample.setLambdaMuSigmaProperties(lms);

        LMSKey lmsKey = new LMSKey(mType,childSex,parameterX,sample.getUnitValueForParameterX());

        lmsValues.put(lmsKey, lms);


        List<ValuePerPercentile> valuePerPercentiles = new ArrayList<ValuePerPercentile>();

        int firstPElm = strArgs.length - 15;
        int lastPElm = strArgs.length - 1;
        if (isSD) {
            sample.setStandardDeviation(Double.valueOf(strArgs[4]));
        }

        ValuePerPercentile valuePerPercentile = new ValuePerPercentile(0, Double.valueOf(strArgs[firstPElm]));
        valuePerPercentiles.add(valuePerPercentile);

        for (int i = firstPElm + 1; i < lastPElm; i++) {
            valuePerPercentile = new ValuePerPercentile(Integer.valueOf(percentiles[i].substring(1)), Double.valueOf(strArgs[i]));
            valuePerPercentiles.add(valuePerPercentile);
        }

        valuePerPercentile = new ValuePerPercentile(100, Double.valueOf(strArgs[lastPElm]));
        valuePerPercentiles.add(valuePerPercentile);

        sample.setValuePerPercentiles(valuePerPercentiles);
        lmsSample.put(lmsKey, sample);

        return sample;

    }

    private static void prepareUsersData() throws JsonProcessingException {

        List<ApplicationUser> users = new ArrayList<>();
        users.add(createUser("admin", "admin",  "admin","admin", "ADMIN"));
        users.add(createUser("Ewa", "Nowak", "MamaEwa","password", "USER"));
        users.add(createUser("Ewelina", "Kowalska", "Ewelina71","password1", "USER"));
        users.add(createUser("Ksawery", "Nowakowski", "Ksawery","password2", "USER"));
        users.add(createUser("Grzegorz", "Zieliński", "TataGrzegorz","password3", "USER"));
        users.add(createUser("Grażyna", "Kowalska", "GrażynkaIEryk","password4", "USER"));
        users.add(createUser("Elwira", "Kowal", "Elwirka88","password5", "USER"));
        users.add(createUser("Henryk", "Kmicic", "Henio","password6", "USER"));

        String jsonInString = mapper.writeValueAsString(users);
        File file = new File("..\\db\\users.json");
        writeIntoFile(jsonInString, file);
    }

    private static ApplicationUser createUser(String name, String lastname, String login, String passsword, String authority) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName(name);
        applicationUser.setLastName(lastname);
        applicationUser.setUsername(login);
        applicationUser.setEmail(name + "." + lastname + "@email.com");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(passsword));
        applicationUser.setAuthorities(Arrays.asList(authority));
        return applicationUser;
    }

    private static class LMSKey {

        MeasurementType measurementType;
        ChildSex sex;
        UnitType unitType;
        double unitValue;


        public LMSKey(MeasurementType measurementType, ChildSex sex, UnitType unitType, double unitValue) {
            this.measurementType = measurementType;
            this.unitValue = unitValue;
            this.unitType = unitType;
            this.sex = sex;
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
    }
}
