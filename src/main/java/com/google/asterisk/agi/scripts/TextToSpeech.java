package com.aws.asterisk.agi.scripts;


import com.aws.asterisk.agi.AgiLogic;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextToSpeech implements AgiScript {
    private final AgiLogic agiLogic;

    @Autowired
    public TextToSpeech(AgiLogic agiLogic) {
        this.agiLogic = agiLogic;
    }

    @Override
    public void service(AgiRequest request, AgiChannel channel) {
        agiLogic.convertTextInToSpeech(request, channel);
    }

}
