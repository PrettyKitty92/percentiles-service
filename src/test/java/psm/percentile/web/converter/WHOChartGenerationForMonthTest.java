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
public class WHOChartGenerationForMonthTest {

    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForAgaByMonth0() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_MONTH;
        String unitValue = "0";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(1, 44.81), null);

        assertThat(chart.getLabels().size()).isEqualTo(8);
        assertThat(chart.getLabels().get(0)).isEqualTo("0");
        assertThat(chart.getLabels().get(7)).isEqualTo("7");
    }

    @Test
    public void shouldReturnSampleForAgaByMonth1() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_MONTH;
        String unitValue = "1";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(15, 3.904), null);

        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("23");
        assertThat(chart.getLabels().get(14)).isEqualTo("37");

    }

    @Test
    public void shouldReturnSampleForAgaByMonth3() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.BMI_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_MONTH;
        String unitValue = "3";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(50, 16.353), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("56");
        assertThat(chart.getLabels().get(10)).isEqualTo("126");

    }


    @Test
    public void shouldReturnSampleForAgaByMonth3example2() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_MONTH;
        String unitValue = "3";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(50, 13.025), null);

        assertThat(chart.getLabels().size()).isEqualTo(6);
        assertThat(chart.getLabels().get(0)).isEqualTo("91");
        assertThat(chart.getLabels().get(5)).isEqualTo("126");

    }

    @Test
    public void shouldReturnSampleForAgaByMonth61() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.HEAD_CIRCUMFERENCE_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_MONTH;
        String unitValue = "61"; //1854


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(90, 51.786), null);

        assertThat(chart.getLabels().size()).isEqualTo(7);
        assertThat(chart.getLabels().get(0)).isEqualTo("1770");
        assertThat(chart.getLabels().get(6)).isEqualTo("1854");

    }

}
