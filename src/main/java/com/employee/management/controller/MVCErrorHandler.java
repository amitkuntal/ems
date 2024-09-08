package com.employee.management.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MVCErrorHandler implements ErrorController {

    @RequestMapping("/error")
    private String getErrorPage(Model model){
        return "error";
    }
}
