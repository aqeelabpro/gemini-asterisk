package com.google.asterisk.ami;

import com.google.asterisk.AsteriskProperties;
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
    DefaultManagerConnection manager(com.aws.asterisk.ami.AsteriskEventListener asteriskEventListener) {
        DefaultManagerConnection manager = new DefaultManagerConnection();
        com.aws.asterisk.ami.AsteriskAmiProperties asteriskAmiProperties = asteriskProperties.getAmi();
        manager.setHostname(asteriskAmiProperties.getHost());
        manager.setUsername(asteriskAmiProperties.getUsername());
        manager.setPassword(asteriskAmiProperties.getPassword());
        manager.addEventListener(asteriskEventListener);
        return manager;
    }

    @Bean("asteriskEventListener")
    com.aws.asterisk.ami.AsteriskEventListener asteriskEventListener() {
        return new com.aws.asterisk.ami.AsteriskEventListener();
    }
}
