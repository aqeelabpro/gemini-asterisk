package com.google.asterisk.agi.scripts;


import com.google.asterisk.agi.AgiLogic;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeechToText implements AgiScript {
    private final AgiLogic agiLogic;

    @Autowired
    public SpeechToText(AgiLogic agiLogic) {
        this.agiLogic = agiLogic;
    }

    @Override
    public void service(AgiRequest request, AgiChannel channel) {
        agiLogic.transcribeSpeechToText(request, channel);
    }
}
