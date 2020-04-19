package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/quartz")
public class QuartzController {

    private static final Logger log = LoggerFactory.getLogger(QuartzController.class);

    @GetMapping("/quartzTest")
    public void quartzTest() {
        try {
            log.info("******************8*****************");
        } catch (Exception e) {
            log.debug("quartzTest:" + e);
        }
    }


}
