package com.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/login")
    private String login() {
        return "/login";
    }

    @GetMapping("/index")
    private String index() {
        return "/index";
    }
}
