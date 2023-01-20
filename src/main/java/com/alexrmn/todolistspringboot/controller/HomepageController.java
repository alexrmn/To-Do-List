package com.alexrmn.todolistspringboot.controller;


import com.alexrmn.todolistspringboot.config.MyUserDetails;
import com.alexrmn.todolistspringboot.model.User;
import com.alexrmn.todolistspringboot.model.dto.CreateUserDto;
import com.alexrmn.todolistspringboot.service.UserService;
import com.alexrmn.todolistspringboot.util.AuthUtils;
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
            User user = new User(userDetails);
            model.addAttribute("user", user);
        }
        if (authentication != null && AuthUtils.isAdmin(authentication)) {
            return  "adminHomepage";
        }
        return "homepage";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        CreateUserDto createUserDto = new CreateUserDto();
        model.addAttribute("user", createUserDto);
        return "registrationPage";
    }

    @PostMapping("/register")
    public String registerUser(@Valid CreateUserDto createUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "userValidationError";
        }
        userService.saveUser(createUserDto);
        return "redirect:/";
    }


}
