package psm.percentile.web.converter;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import psm.percentile.common.model.ChildSex;
import psm.percentile.common.model.MeasurementType;
import psm.percentile.common.model.UnitType;
import psm.percentile.common.model.ValuePerPercentile;
import psm.percentile.common.model.chart.ChartOfPercentileSamples;
import psm.percentile.web.Application;
import psm.percentile.web.configuration.MongoDbConfiguration;
import psm.percentile.web.service.exception.BadRequestParamsException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        Application.class,
        MongoDbConfiguration.class})
public class WHOChartGenerationForUnitTypeHeightTest {

    //Age by day
    //Age by week
    //age by month
    //age by date of birth

    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForHeight65() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "65";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(3, 6.394), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("65.0");
        assertThat(chart.getLabels().get(10)).isEqualTo("70.0");

    }


    @Test
    public void shouldReturnSampleForHeight68() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "68.7";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(10, 7.175), null);

        assertThat(chart.getLabels().size()).isEqualTo(18);
        assertThat(chart.getLabels().get(0)).isEqualTo("65.2");
        assertThat(chart.getLabels().get(17)).isEqualTo("73.7");
        }


    @Test
    public void shouldReturnSampleForHeight90() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "90";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(50, 12.646), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(21);
        assertThat(chart.getLabels().get(0)).isEqualTo("85.0");
        assertThat(chart.getLabels().get(20)).isEqualTo("95.0");

    }



    @Test
    public void shouldReturnSampleForHeight110() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "110";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 19.66), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(21);
        assertThat(chart.getLabels().get(0)).isEqualTo("105.0");
        assertThat(chart.getLabels().get(20)).isEqualTo("115.0");

    }


    @Test
    public void shouldReturnSampleForHeight118() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "118";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 21.953), null);

        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("113.0");
        assertThat(chart.getLabels().get(14)).isEqualTo("120.0");

    }

    @Test
    public void shouldReturnSampleForHeight118WithPoint() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "118.4";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 21.953), null);

        assertThat(chart.getLabels().size()).isEqualTo(14);
        assertThat(chart.getLabels().get(0)).isEqualTo("113.4");
        assertThat(chart.getLabels().get(13)).isEqualTo("119.9");

    }

    @Test
    public void shouldReturnSampleForHeight120() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.HEIGHT;
        String unitValue = "120";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(85, 24.689), null);



        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("115.0");
        assertThat(chart.getLabels().get(10)).isEqualTo("120.0");

    }

}
