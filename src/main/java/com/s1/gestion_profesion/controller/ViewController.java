package com.s1.gestion_profesion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/", "/app"})
    public String index() {
        return "forward:/app/index.html";
    }
}
