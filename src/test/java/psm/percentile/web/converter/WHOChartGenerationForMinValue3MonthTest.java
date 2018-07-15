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
public class WHOChartGenerationForMinValue3MonthTest {

    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForAgaByDayIs91() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.SUBSCAPULAR_SKINFOLD_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "91";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 8.645), null);

        assertThat(chart.getLabels().size()).isEqualTo(6);
        assertThat(chart.getLabels().get(0)).isEqualTo("91");
        assertThat(chart.getLabels().get(5)).isEqualTo("126");

    }


    @Test
    public void shouldReturnSampleForAgaByDayIs95() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.TRICEPS_SKINFOLD_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "95";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(10, 7.727), null);

        assertThat(chart.getLabels().size()).isEqualTo(6);
        assertThat(chart.getLabels().get(0)).isEqualTo("95");
        assertThat(chart.getLabels().get(5)).isEqualTo("130");
        }


    @Test
    public void shouldReturnSampleForAgaByDayIs100() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "100";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(90, 14.927), null);

        assertThat(chart.getLabels().size()).isEqualTo(7);
        assertThat(chart.getLabels().get(0)).isEqualTo("93");
        assertThat(chart.getLabels().get(6)).isEqualTo("135");
    }



    @Test
    public void shouldReturnSampleForAgaByDayIs200() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.ARM_CIRCUMFERENCE_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "200";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(200, 15.433), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("165");
        assertThat(chart.getLabels().get(10)).isEqualTo("235");
    }

    @Test
    public void shouldReturnSampleForAgaByDayIs1886() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.TRICEPS_SKINFOLD_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "1855";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 58), null);

        assertThat(chart.getLabels().size()).isEqualTo(7);
        assertThat(chart.getLabels().get(0)).isEqualTo("1771");
        assertThat(chart.getLabels().get(6)).isEqualTo("1855");
    }

    @Test
    public void shouldReturnSampleForAgaByDayIs1830() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.SUBSCAPULAR_SKINFOLD_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "1830";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(90, 8.624), null);

        assertThat(chart.getLabels().size()).isEqualTo(8);
        assertThat(chart.getLabels().get(0)).isEqualTo("1746");
        assertThat(chart.getLabels().get(7)).isEqualTo("1844");
    }

}
