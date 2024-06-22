package com.aws.asterisk.agi.strategy;

import com.aws.asterisk.agi.scripts.SpeechToText;
import com.aws.asterisk.agi.scripts.TextToSpeech;
import lombok.extern.log4j.Log4j2;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiScript;
import org.asteriskjava.fastagi.MappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class AsteriskMappingStrategy implements MappingStrategy {
    @Autowired
    private SpeechToText speechToText;

    @Autowired
    private TextToSpeech textToSpeech;

    @Override
    public AgiScript determineScript(AgiRequest request, AgiChannel channel) {
        String script = request.getScript();
        log.debug("AGI: {} triggered", script);

        if ("speechToText.agi".equals(script))
            return speechToText;

        if ("textToSpeech.agi".equals(script))
            return textToSpeech;

        return null;
    }
}
