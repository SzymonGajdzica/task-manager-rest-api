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
        if(InetAddress.getLocalHost().getHostAddress().contains("192.168")){
            dataSourceBuilder.url("jdbc:postgresql://localhost:1234/task-manager");
            dataSourceBuilder.username("postgres");
            dataSourceBuilder.password("12345");
        }
        return dataSourceBuilder.build();
    }
}