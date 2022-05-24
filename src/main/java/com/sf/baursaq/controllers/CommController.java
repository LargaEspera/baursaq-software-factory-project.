package com.sf.baursaq.controllers;

import com.sf.baursaq.entity.Comm;
import com.sf.baursaq.entity.Recipe;
import com.sf.baursaq.services.CommService;
import com.sf.baursaq.services.RecipeService;
import com.sf.baursaq.services.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm")
public class CommController {

    private UserService userService;
    private RecipeService recipeService;
    private CommService commService;

    @Autowired
    public CommController(UserService userService, RecipeService recipeService, CommService commService){
        this.userService = userService;
        this.recipeService = recipeService;
        this.commService = commService;
    }

    @GetMapping("/comm-form/{id}")
    public String createCommForm(@PathVariable("id") Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("comm",new Comm());
        return "comment-form";
    }

    @PostMapping("/comm-form/{id}")
    public String create(@PathVariable("id") Long id, Comm comm){
        boolean result = commService.create(comm, id);
        if (result) return "redirect:/recipe/recipe-page/" + id;
        else return "bad";
    }
}
