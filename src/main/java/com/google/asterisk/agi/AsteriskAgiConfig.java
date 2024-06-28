package com.google.asterisk.agi;


import com.google.asterisk.AsteriskProperties;
import com.google.asterisk.agi.strategy.AsteriskMappingStrategy;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class AsteriskAgiConfig {
    private final AsteriskProperties asteriskProperties;

    @Autowired
    public AsteriskAgiConfig(AsteriskProperties asteriskProperties) {
        this.asteriskProperties = asteriskProperties;
    }

    @Bean("asteriskMappingStrategy")
    AsteriskMappingStrategy asteriskMappingStrategy() {
        return new AsteriskMappingStrategy();
    }

    @Bean("defaultAgiServer")
    @DependsOn({"asteriskMappingStrategy"})
    DefaultAgiServer defaultAgiServer(AsteriskMappingStrategy asteriskMappingStrategy) throws UnknownHostException {
        DefaultAgiServer defaultAgiServer = new DefaultAgiServer();
        AsteriskAgiProperties asteriskAgiProperties = asteriskProperties.getAgi();
        InetAddress address = InetAddress.getByName(asteriskAgiProperties.getHost());
        defaultAgiServer.setAddress(address);
        defaultAgiServer.setPort(asteriskAgiProperties.getPort());
        defaultAgiServer.setPoolSize(asteriskAgiProperties.getPoolSize());
        defaultAgiServer.setMaximumPoolSize(asteriskAgiProperties.getMaximumPoolSize());
        defaultAgiServer.setMappingStrategy(asteriskMappingStrategy);
        return defaultAgiServer;
    }

    @Bean
    @DependsOn("defaultAgiServer")
    AgiServerThread AgiServerThread(DefaultAgiServer defaultAgiServer) {
        return new AgiServerThread(defaultAgiServer);
    }
}