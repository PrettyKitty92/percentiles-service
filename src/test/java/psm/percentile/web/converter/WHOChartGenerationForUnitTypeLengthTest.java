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
public class WHOChartGenerationForUnitTypeLengthTest {

    //Age by day
    //Age by week
    //age by month
    //age by date of birth

    @Autowired
    private WHOChartDataService whoChartDataService;


    @Test
    public void shouldReturnSampleForLength45() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "45.0";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(3, 2.064), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("45.0");
        assertThat(chart.getLabels().get(10)).isEqualTo("50.0");

    }


    @Test
    public void shouldReturnSampleForLength48() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "48.9";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(3, 2.66), null);

        assertThat(chart.getLabels().size()).isEqualTo(18);
        assertThat(chart.getLabels().get(0)).isEqualTo("45.4");
        assertThat(chart.getLabels().get(17)).isEqualTo("53.9");
        }


    @Test
    public void shouldReturnSampleForLength80() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "80.0";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(90, 11.645), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(21);
        assertThat(chart.getLabels().get(0)).isEqualTo("75.0");
        assertThat(chart.getLabels().get(20)).isEqualTo("85.0");

    }

    @Test
    public void shouldReturnSampleForLength108() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.GIRL;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "108";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(5, 15.149), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("103.0");
        assertThat(chart.getLabels().get(14)).isEqualTo("110.0");

    }


    @Test
    public void shouldReturnSampleForLength108WithPoint() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "108.8";

        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(5, 15.149), null);

        assertThat(chart.getSamples().size()).isEqualTo(16);
        assertThat(chart.getLabels().size()).isEqualTo(13);
        assertThat(chart.getLabels().get(0)).isEqualTo("103.8");
        assertThat(chart.getLabels().get(12)).isEqualTo("109.8");

    }

    @Test
    public void shouldReturnSampleForLength110() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.WEIGHT_FOR_LENGTH_HEIGHT;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.LENGTH;
        String unitValue = "110";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(5, 15.875), null);

        assertThat(chart.getLabels().size()).isEqualTo(11);
        assertThat(chart.getLabels().get(0)).isEqualTo("105.0");
        assertThat(chart.getLabels().get(10)).isEqualTo("110.0");

    }

}
