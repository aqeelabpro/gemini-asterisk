package com.google.asterisk.ami;

import lombok.extern.log4j.Log4j2;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.ManagerEvent;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AsteriskEventListener implements ManagerEventListener {
    @Override
    public void onManagerEvent(ManagerEvent event) {
    }
}