package com.ttd.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import com.ttd.config.PropertyUtil;

public class ApplicationStartUpListener implements ApplicationListener<ApplicationStartingEvent>{
	 	
	@Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        PropertyUtil.loadAllProperties();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>ApplicationStartUpListener EXEC");
    }
}
