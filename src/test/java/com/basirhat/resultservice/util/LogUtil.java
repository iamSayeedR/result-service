package com.basirhat.resultservice.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private LogUtil() {

    }

    public static ListAppender<ILoggingEvent> buildTestLogListAppender(Class logClass) {
        Logger logger = (Logger) LoggerFactory.getLogger(logClass);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        return listAppender;
    }
}
