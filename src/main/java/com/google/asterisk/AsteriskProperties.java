package com.google.asterisk;


import com.google.asterisk.agi.AsteriskAgiProperties;
import com.google.asterisk.ami.AsteriskAmiProperties;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Component
@ConfigurationProperties("asterisk")
public class AsteriskProperties {
    private AsteriskAmiProperties ami;
    private AsteriskAgiProperties agi;
}
