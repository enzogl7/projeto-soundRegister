package com.ogl.soundregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PrincipalController {

    @GetMapping("/")
    public String home() {
        return "principal/principal";
    }

}