package com.dosol.abc.controller;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final HttpSession session;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String mainP(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user != null) {

            model.addAttribute("user", user);
        }
        return "main";
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
