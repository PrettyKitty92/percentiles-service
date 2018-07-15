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
public class WHOChartGenerationForMinValueZeroTest {


    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForAgaByDayIs0() throws BadRequestParamsException {
        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "0";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 51.161), null);

        assertThat(chart.getLabels().size()).isEqualTo(8);
        assertThat(chart.getLabels().get(0)).isEqualTo("0");
        assertThat(chart.getLabels().get(7)).isEqualTo("7");

    }


    @Test
    public void shouldReturnSampleForAgaByDayIs2() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "2";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 58), null);

        assertThat(chart.getLabels().size()).isEqualTo(10);
        assertThat(chart.getLabels().get(0)).isEqualTo("0");
        assertThat(chart.getLabels().get(9)).isEqualTo("9");
        }


    @Test
    public void shouldReturnSampleForAgaByDayYoungerThen3months() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "30";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 58), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("23");
        assertThat(chart.getLabels().get(14)).isEqualTo("37");

    }



    @Test
    public void shouldReturnSampleForAgaByDayYoungerThen2years() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "100";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 70), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("65");
        assertThat(chart.getLabels().get(10)).isEqualTo("135");

    }

    @Test
    public void shouldReturnSampleForAgaByDayYoungerThen2yearsAndOlder3months() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "700";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 10), null);



        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("665");
        assertThat(chart.getLabels().get(10)).isEqualTo("735");

    }

    @Test
    public void shouldReturnSampleForAgaByDayOlderThen2years() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY ;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "800";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 91.193), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(13);
        assertThat(chart.getLabels().get(0)).isEqualTo("716");
        assertThat(chart.getLabels().get(12)).isEqualTo("884");

    }

    @Test
    public void shouldReturnSampleForAgaByDayIs1855() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "1855";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(99, 58), null);

        assertThat(chart.getLabels().size()).isEqualTo(7);
        assertThat(chart.getLabels().get(0)).isEqualTo("1771");
        assertThat(chart.getLabels().get(6)).isEqualTo("1855");

    }

    @Test
    public void shouldReturnSampleForAgaByDayIs1856() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_DAY;
        String unitValue = "1856";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(97, 119.279	), null);

        assertThat(chart.getLabels().size()).isEqualTo(7);
        assertThat(chart.getLabels().get(0)).isEqualTo("1772");
        assertThat(chart.getLabels().get(6)).isEqualTo("1856");

    }

}
