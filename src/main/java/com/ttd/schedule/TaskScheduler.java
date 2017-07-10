package com.ttd.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ttd.utils.ServletContextUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

//定时任务示例
@Component
public class TaskScheduler {
    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Integer count0 = 1;
    private Integer count1 = 1;
    private Integer count2 = 1;

   /* @Scheduled(fixedRate = 5000)// 每5000毫秒执行一次，不管上一次是否执行完
    public void reportCurrentTime() throws InterruptedException {
        logger.info(String.format("---第%s次执行，当前时间为：%s", count0++, dateFormat.format(new Date())));
    }*/

//    @Scheduled(fixedDelay = 5000)// 每5000毫秒执行一次，下一次需等上一次执行完
    public void reportCurrentTimeAfterSleep() throws InterruptedException {
    	ServletContextUtil context = ServletContextUtil.getContext();
        logger.error("|--------"+Thread.currentThread().getName() + ":" + context);
        logger.error(">>>>>>>>>"+context.getRequest());
        logger.error("<<<<<<<<<"+context.getResponse());
    }

   /* @Scheduled(cron = "0 0 1 * * *")
    public void reportCurrentTimeCron() throws InterruptedException {
        logger.info(String.format("+++第%s次执行，当前时间为：%s", count2++, dateFormat.format(new Date())));
    }*/
}