package com.dosol.abc.controller.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.user.UserDTO;
import com.dosol.abc.repository.user.UserRepository;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public void login() {
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (userService.login(username, password).isPresent()) {
            // 로그인 성공 시 메인 페이지로 리다이렉트
            User user = userService.findByUserName(username);
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            // 로그인 실패 시 에러 메시지와 함께 로그인 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String registerPost(UserDTO userDTO) {
        Long userId = userService.register(userDTO);

        return "redirect:/user/login";
    }


}
