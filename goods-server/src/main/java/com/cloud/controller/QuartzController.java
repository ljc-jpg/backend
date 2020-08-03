package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuz
 * @date 2020/8/3
 */

@RestController
@RequestMapping("/quartz")
public class QuartzController {

    private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);

    @GetMapping("/quartzTest")
    public void quartzTest() {
        try {
            logger.info("******************8*****************");
        } catch (Exception e) {
            logger.debug("quartzTest:" + e);
        }
    }


}
