package com.aws.asterisk;


import com.aws.asterisk.agi.AsteriskAgiProperties;
import com.aws.asterisk.ami.AsteriskAmiProperties;
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
