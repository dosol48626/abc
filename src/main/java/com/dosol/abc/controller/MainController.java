package com.dosol.abc.controller;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
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

//    @GetMapping("/")
//    public String mainP(HttpSession session, Model model) {
//
//        User user = (User) session.getAttribute("user");
//
//        if (user != null) {
//
//            model.addAttribute("user", user);
//        }
//        return "main";
//    }

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);

            // 프로필 이미지 조회 및 전달
            UserImage profileImage = userService.getProfileImageByUsername(user.getUsername());
            model.addAttribute("profileImage", profileImage);
        }
        return "main"; // main.html 페이지로 이동
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
