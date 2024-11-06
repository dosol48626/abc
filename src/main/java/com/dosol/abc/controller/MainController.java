package com.dosol.abc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainP(){

        return "basic";
    }

    @GetMapping("/on")
    public String onP(){

        return "on";
    }

    @GetMapping("/mypage")
    public String mypage(){

        return "mypage";
    }
}
