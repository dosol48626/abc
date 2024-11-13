package com.dosol.abc.controller;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import com.dosol.abc.domain.wise.Wise;
import com.dosol.abc.dto.wise.WiseDTO;
import com.dosol.abc.service.user.UserService;
import com.dosol.abc.service.wise.WiseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final UserService userService;
    private final HttpSession session;
    private final ModelMapper modelMapper;
    private final WiseService wiseService;


    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            UserImage profileImage = userService.getProfileImageByUsername(user.getUsername());
            model.addAttribute("profileImage", profileImage);
        }

        // 명언 조회
        WiseDTO wiseDTO = wiseService.getRandomWise();
        log.info("@@@@@@@@@!!!!!!!!!!!!!!명어어어어ㅓ어어어어어어어ㅓ어어어어어어ㅓㄴ"+wiseDTO);

        model.addAttribute("wise", wiseDTO);

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
