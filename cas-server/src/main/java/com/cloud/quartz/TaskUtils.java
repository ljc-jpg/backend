package com.cloud.quartz;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@Component
public class TaskUtils {

    private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    @Resource
    private RestTemplate restTemplate;

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public void invokeMethod(ScheduleJob scheduleJob) {
        if (null != scheduleJob && StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            String address = scheduleJob.getBeanClass();
            if (null != scheduleJob.getJobData()) {
                address += "?" + scheduleJob.getJobData();
            }
            try {
                logger.debug("address:{}", address);
                String result = restTemplate.getForObject(address, String.class);
                logger.debug("result:{}" + result);
            } catch (Exception e) {
                logger.error("invokMethod error", e);
            }
        }
    }
}
