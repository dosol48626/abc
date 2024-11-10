package com.dosol.abc.controller.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import com.dosol.abc.dto.user.UserDTO;
import com.dosol.abc.repository.user.UserImageRepository;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserImageRepository userImageRepository;

    @GetMapping("/login")
    public void login() {
    }

    @PostMapping("/login")
    public String loginPost(
            String username,
            String password,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (userService.login(username, password).isPresent()) {
            session.setAttribute("user", userService.findByUserName(username));
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String registerPost(UserDTO userDTO,
                               @RequestParam("profileImage") MultipartFile profileImage,
                               RedirectAttributes redirectAttributes) {
        // 회원가입 시 프로필 이미지도 함께 저장
        userService.register(userDTO, profileImage);

        // 회원가입 완료 후 로그인 페이지로 리다이렉트
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);

            // 프로필 이미지 목록에서 첫 번째 이미지를 가져옴
            List<UserImage> profileImages = userImageRepository.findByUser_UserId(user.getUserId());
            if (!profileImages.isEmpty()) {
                model.addAttribute("profileImage", profileImages.get(0)); // 첫 번째 이미지
            }

            return "user/mypage";
        } else {
            return "redirect:/user/login";
        }
    }
}