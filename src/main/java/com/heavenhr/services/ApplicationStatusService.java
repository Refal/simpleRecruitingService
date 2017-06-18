package com.heavenhr.services;

import com.heavenhr.domain.Application;
import com.heavenhr.listeners.ApplicationChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@Service
public class ApplicationStatusService extends HandlerInterceptorAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationStatusService.class);
    private final List<ApplicationChangeListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public void registerListener(ApplicationChangeListener listener) {
        listeners.add(listener);
    }

    public void deregisterListener(ApplicationChangeListener listener) {
        listeners.remove(listener);
    }

    public void notify(Application application) {
        listeners.forEach(listener -> {
            try {
                listener.onStatusChange(application);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        });
    }
}
