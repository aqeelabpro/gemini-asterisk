package com.google.asterisk;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.manager.ManagerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2
@Service
public class AsteriskService
{
    private final ManagerConnection manager;
    private final AgiServerThread agiServerThread;

    @Autowired
    public AsteriskService(ManagerConnection manager, AgiServerThread agiServerThread) {
        this.manager = manager;
        this.agiServerThread = agiServerThread;
    }

    @PostConstruct
    public void onStartUp()
    {
        try
        {
            manager.login();
            log.info("The connection to the asterisk Ami server is established.");
            agiServerThread.startup();
            log.info("AgiServer is started and ready to receive requests from Asterisk server.");
        }
        catch(Exception e)
        {
            log.error("Error calling onStartUp. Method params: " + Arrays.toString(new Object[] {}), e);
        }
    }

    @PreDestroy
    public void onShutdown()
    {
        try
        {
            log.info("Ami Server shutdown");
            manager.logoff();
            log.info("Agi Server shutdown");
            agiServerThread.shutdown();
        }
        catch(Exception e)
        {
            log.error("Error calling onShutdown. Method params: " + Arrays.toString(new Object[] {}), e);
        }
    }
}
