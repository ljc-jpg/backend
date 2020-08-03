package com.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhuz
 * @date 2020/8/3
 */

@Controller
public class IndexController {

    @GetMapping("/index")
    private String index() {
        return "/index";
    }
}
