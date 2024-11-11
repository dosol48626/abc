package com.dosol.abc.controller;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice

public class GlobalControllerAdvice {

    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addUserInfoToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
            UserImage profileImage = userService.getProfileImageByUsername(user.getUsername());
            model.addAttribute("profileImage", profileImage != null ? profileImage : new UserImage());
        }
    }
}
