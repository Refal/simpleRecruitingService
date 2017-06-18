package com.heavenhr.listeners;

import com.heavenhr.domain.Application;
import com.heavenhr.services.ApplicationStatusService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@Service
public class ConsoleStatusChangeListener implements ApplicationChangeListener, InitializingBean {

    @Autowired
    private
    ApplicationStatusService statusService;

    @Override
    public void afterPropertiesSet() throws Exception {
        statusService.registerListener(this);
    }

    @Override
    public void onStatusChange(Application application) {
        System.out.printf("Application status changed to: %s", application.toString());
    }


}
