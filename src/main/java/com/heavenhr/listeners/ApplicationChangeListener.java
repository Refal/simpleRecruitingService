package com.heavenhr.listeners;

import com.heavenhr.domain.Application;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
public interface ApplicationChangeListener {
    void onStatusChange(Application application);
}
