package com.alexrmn.todolistspringboot.controller;


import com.alexrmn.todolistspringboot.config.MyUserDetails;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class HomepageController {

    private final UserService userService;

    @GetMapping("/")
    public String showHomepage(Model model, Authentication authentication) {
        if(authentication != null) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }
        return "homepage";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registrationPage";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult) {
        //todo
        if (bindingResult.hasErrors()) {
            return "/tasks/taskValidationError";
        }
        userService.saveUser(user);
        return "redirect:/";
    }


}
