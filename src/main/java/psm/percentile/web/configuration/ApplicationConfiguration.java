package psm.percentile.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Created by Patrycja on 16.05.2017.
 */
@Configuration
//@PropertySource(value = {"chart.properties"})
@ComponentScan(value = {"psm.percentile.common", "psm.percentile.web"})
public class ApplicationConfiguration {

    @Bean
    public NormalDistribution normalDistribution() {
        return new NormalDistribution();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
