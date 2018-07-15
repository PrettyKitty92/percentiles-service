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
public class WHOChartGenerationForWeekTest {

    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForAgaByWeek0() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_WEEK;
        String unitValue = "0";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(5, 46.77), null);

        assertThat(chart.getLabels().size()).isEqualTo(8);
        assertThat(chart.getLabels().get(0)).isEqualTo("0");
        assertThat(chart.getLabels().get(7)).isEqualTo("7");
    }

    @Test
    public void shouldReturnSampleForAgaByWeek1() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_WEEK;
        String unitValue = "1";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(7, 2.726), null);

        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("0");
        assertThat(chart.getLabels().get(14)).isEqualTo("14");

    }

    @Test
    public void shouldReturnSampleForAgaByWeek13() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.BMI_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_WEEK;
        String unitValue = "13";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(50, 16.895), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("56");
        assertThat(chart.getLabels().get(10)).isEqualTo("126");

    }

    @Test
    public void shouldReturnSampleForAgaByWeek120() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.BMI_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_WEEK;
        String unitValue = "120";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(25, 14.738), null);

        assertThat(chart.getLabels().size()).isEqualTo(13);
        assertThat(chart.getLabels().get(0)).isEqualTo("756");
        assertThat(chart.getLabels().get(12)).isEqualTo("924");

    }

    @Test
    public void shouldReturnSampleForAgaByWeek265() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.HEAD_CIRCUMFERENCE_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_WEEK;
        String unitValue = "256";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(10, 48.785), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("1708");
        assertThat(chart.getLabels().get(10)).isEqualTo("1848");

    }

}
