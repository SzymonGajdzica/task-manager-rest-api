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
            System.out.println("STARTING DATABASE AT LOCALHOST");
            dataSourceBuilder.url("jdbc:postgresql://localhost:1234/task-manager");
            dataSourceBuilder.username("postgres");
            dataSourceBuilder.password("12345");
        } else {
            System.out.println("STARTING DATABASE AT HEROKU");
            dataSourceBuilder.url("jdbc:postgresql://fguyjifdlvweyi:576870d9f31bedafda3724636fe00799235921fd3919aad57647447f914371c9@ec2-54-247-169-129.eu-west-1.compute.amazonaws.com:5432/d30pnjo54sfi93");
            dataSourceBuilder.username("fguyjifdlvweyi");
            dataSourceBuilder.password("576870d9f31bedafda3724636fe00799235921fd3919aad57647447f914371c9");
        }
        return dataSourceBuilder.build();
    }
}