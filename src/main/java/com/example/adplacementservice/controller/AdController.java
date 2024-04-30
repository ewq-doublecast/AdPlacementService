package com.example.adplacementservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdController {

    @GetMapping("/")
    public String ads() {
        return "ads";
    }

}
