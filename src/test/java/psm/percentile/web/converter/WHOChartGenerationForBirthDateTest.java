package psm.percentile.web.converter;


import org.junit.Ignore;
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
public class WHOChartGenerationForBirthDateTest {

    @Autowired
    private WHOChartDataService whoChartDataService;




    @Test
    @Ignore
    public void shouldReturnSampleForAgaByDayIs0() throws BadRequestParamsException {

        MeasurementType measurementType = MeasurementType.LENGTH_HEIGHT_FOR_AGE;
        ChildSex childSex = ChildSex.BOY;
        UnitType unitTypeForParameterX = UnitType.AGE_BY_BIRTHDAY;
        String unitValue = "15-05-2018";


        ChartOfPercentileSamples chart = whoChartDataService.prepareChartDataForValuePerPercentileInPeriod(measurementType,childSex,unitTypeForParameterX,unitValue,
                new ValuePerPercentile(75, 51.161),null);

        assertThat(chart.getLabels().size()).isEqualTo(15);
        assertThat(chart.getLabels().get(0)).isEqualTo("11");
        assertThat(chart.getLabels().get(14)).isEqualTo("25");

    }

}
