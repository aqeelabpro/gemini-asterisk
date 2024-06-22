package com.aws.asterisk.config;

import com.aws.asterisk.AsteriskProperties;
import lombok.Data;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AsteriskManagerConfiguration {

    private AsteriskProperties asteriskProperties;

    public AsteriskManagerConfiguration(AsteriskProperties asteriskProperties) {
        this.asteriskProperties = asteriskProperties;
    }

    @Bean
    public ManagerConnection managerConnectionFactory() {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(asteriskProperties.getAmi().getHost(), asteriskProperties.getAmi().getUsername(), asteriskProperties.getAmi().getPassword());
        return factory.createManagerConnection();
    }
}