package com.sf.baursaq.controllers;

import com.sf.baursaq.entity.Recipe;
import com.sf.baursaq.entity.User;
import com.sf.baursaq.services.CommService;
import com.sf.baursaq.services.RecipeService;
import com.sf.baursaq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private UserService userService;
    private RecipeService recipeService;
    private CommService commService;

    @Autowired
    public RecipeController(UserService userService, RecipeService recipeService, CommService commService){
        this.userService = userService;
        this.recipeService = recipeService;
        this.commService = commService;
    }

    @GetMapping("/guest-recipe-list")
    public String getRecipes(Model model){
        List<Recipe> recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        return "guest-recipe-list";
    }

    @GetMapping("/user-recipe-list/{id}")
    public String getRecipesUser(Model model, @PathVariable("id") Long userId){
        List<Recipe> recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("id", userId);
        return "user-recipe-list";
    }

    @GetMapping("/recipe-creation/{id}")
    public String createRecipeForm(@PathVariable("id") Long userId, Model model){
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("recipe", new Recipe());
        return "recipe-creation";
    }
    @PostMapping("/recipe-creation/{id}")
    public String createRecipeFormPost(@PathVariable("id") Long userId, Recipe recipe){
        boolean result = recipeService.create(recipe, userId);
        if (result) return "redirect:/user/cabinet/" + userId;
        else return "bad";
    }

    @GetMapping("/update-recipe/{id}/{userId}")
    public String updateRecipeForm(@PathVariable("id") Long id, @PathVariable("userId") Long userId, Model model){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("idd", id);
        model.addAttribute("id", userId);
        return "recipe-update-form";
    }
    @PostMapping("/update-recipe/{id}/{idd}")
    public String updateRecipe(Recipe recipe, @PathVariable("id") Long userId, @PathVariable("idd") Long idd){
        recipeService.updateRecipe(recipe, idd);
        return "redirect:/user/cabinet/" + userId;
    }

    @GetMapping("delete-recipe/{userId}/{title}")
    public String deleteRecipe(@PathVariable("userId") Long userId, @PathVariable("title") String title){
        recipeService.deleteRecipe(userId, title);
        return "redirect:/user/cabinet/" + userId;
    }

    @GetMapping("recipe-page/{id}")
    public String recipe(Model model, @PathVariable("id") Long recipeId){
        Recipe recipe = recipeService.findById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe-page";
    }

    @GetMapping("recipe-page-restricted/{id}")
    public String recipeRestricted(Model model, @PathVariable("id") Long recipeId){
        Recipe recipe = recipeService.findById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe-page-restricted";
    }

    @GetMapping("set-like/{id}")
    public String setLike(@PathVariable("id") Long id, HttpServletRequest request){
        recipeService.setLike(id);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping("/banner1")
    public String firstBanner(){
        return "banner1";
    }

    @GetMapping("/banner2")
    public String secondBanner(){
        return "banner2";
    }

    @GetMapping("/banner3")
    public String thirdBanner(){
        return "banner3";
    }

}
