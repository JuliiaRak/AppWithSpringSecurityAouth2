package com.oauth.oauthAuthorization.controller;

import com.oauth.oauthAuthorization.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.oauth.oauthAuthorization.dto.UserRegisteredDTO;
import com.oauth.oauthAuthorization.service.DefaultUserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private DefaultUserService userService;

    @Autowired
    UserRepository userRepo;

    public RegistrationController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto() {
        return new UserRegisteredDTO();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name")!= null ?user.getAttribute("name"):user.getAttribute("login"));
        }else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            com.oauth.oauthAuthorization.model.User users = userRepo.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
        }

        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegisteredDTO registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }
}