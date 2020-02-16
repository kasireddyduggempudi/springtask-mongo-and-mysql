package com.vedantu.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import org.springframework.stereotype.Service;

@Service
public class LogFactory {

    public LogFactory() throws URISyntaxException {
        System.out.println("log factory initialized");
        //System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        //System.setProperty("log4j2.AsyncQueueFullPolicy", "Discard");

        String log4jConfigFile = "/ENV-LOCAL"
                + File.separator + "log.xml";
        System.setProperty("log4j.configurationFile", log4jConfigFile);
        System.out.println("log4j.configurationFile " + log4jConfigFile);
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);

//		InputStream is = LogFactory.class.getClassLoader()
//                .getResourceAsStream(log4jConfigFile);
//		File file = new File(is);
        // this will force a reconfiguration
        context.setConfigLocation(new URI(log4jConfigFile));
//        System.out.println(log4jConfigFile);
        System.out.println("Logging Enabled");
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    @PreDestroy
    public void cleanUp() {
        try {
            LogManager.shutdown();
        } catch (Exception e) {
            System.err.println("Error in shutting down LogManager " + e.getMessage());
        }
    }

}
