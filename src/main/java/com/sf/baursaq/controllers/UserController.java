package com.sf.baursaq.controllers;

import com.sf.baursaq.entity.User;
import com.sf.baursaq.services.CommService;
import com.sf.baursaq.services.RecipeService;
import com.sf.baursaq.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RecipeService recipeService;
    private CommService commService;

    @Autowired
    public UserController(UserService userService, RecipeService recipeService, CommService commService){
        this.userService = userService;
        this.recipeService = recipeService;
        this.commService = commService;
    }

    @GetMapping("/start-page")
    public String startPage(){
        return "start-page";
    }

    @GetMapping("/sign-in-form")
    public String signInForm(){
        return "sign-in-form";
    }
    @PostMapping("/sign-in-form")
    public String signInFormPost(User user){
        boolean result = userService.login(user);
        if (!result) return "bad";
        User userRequest = userService.returnUser(user);
        return "redirect:/user/cabinet/" + userRequest.getId();
    }

    @GetMapping("/sign-up-form")
    public String signUpForm(){
        return "sign-up-form";
    }
    @PostMapping("/sign-up-form")
    public String signUpFormPost(User user){
        boolean result = userService.createUser(user);
        if (!result) return "bad";
        return "redirect:/user/cabinet/" + user.getId();
    }

    @GetMapping("/cabinet/{id}")
    public String cabinet(@PathVariable("id") Long userId, Model model){
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "cabinet";
    }

    @GetMapping("delete/{username}")
    public String deleteUserByUsername(@PathVariable("username") String username){
        userService.deleteUser(username);
        return "redirect:/user/start-page";
    }

    @GetMapping("/contacts")
    public String contactPage(){
        return "contacts";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "FAQ";
    }



}
