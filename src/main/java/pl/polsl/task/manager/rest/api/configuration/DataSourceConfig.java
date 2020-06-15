package pl.polsl.task.manager.rest.api.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class DataSourceConfig {

    @SuppressWarnings("rawtypes")
    @Bean
    public DataSource getDataSource() throws UnknownHostException {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        if(InetAddress.getLocalHost().getHostAddress().contains("192.168")){
            dataSourceBuilder.url("jdbc:postgresql://localhost:1234/task-manager");
            dataSourceBuilder.username("postgres");
            dataSourceBuilder.password("12345");
        } else {
            dataSourceBuilder.url("jdbc:postgresql://ec2-54-75-246-118.eu-west-1.compute.amazonaws.com:5432/det0na748qdeqk");
            dataSourceBuilder.username("siuumazxijfqjy");
            dataSourceBuilder.password("8609935128d73f9b7834f5b721be405d9bf6c5987f152f2710cb8c2dc6f439fc");
        }
        return dataSourceBuilder.build();
    }
}