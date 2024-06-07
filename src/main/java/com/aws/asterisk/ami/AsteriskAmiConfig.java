package com.aws.asterisk.ami;

import com.aws.asterisk.AsteriskProperties;
import org.asteriskjava.manager.DefaultManagerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class AsteriskAmiConfig {
    private final AsteriskProperties asteriskProperties;

    @Autowired
    public AsteriskAmiConfig(AsteriskProperties asteriskProperties) {
        this.asteriskProperties = asteriskProperties;
    }

    @Bean
    @DependsOn({"asteriskEventListener"})
    DefaultManagerConnection manager(AsteriskEventListener asteriskEventListener) {
        DefaultManagerConnection manager = new DefaultManagerConnection();
        AsteriskAmiProperties asteriskAmiProperties = asteriskProperties.getAmi();
        manager.setHostname(asteriskAmiProperties.getHost());
        manager.setUsername(asteriskAmiProperties.getUsername());
        manager.setPassword(asteriskAmiProperties.getPassword());
        manager.addEventListener(asteriskEventListener);
        return manager;
    }

    @Bean("asteriskEventListener")
    AsteriskEventListener asteriskEventListener() {
        return new AsteriskEventListener();
    }
}
