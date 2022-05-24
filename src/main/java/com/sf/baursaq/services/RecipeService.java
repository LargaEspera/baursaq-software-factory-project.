package com.sf.baursaq.services;

import com.sf.baursaq.entity.Recipe;
import com.sf.baursaq.entity.User;
import com.sf.baursaq.repository.RecipeRepository;
import com.sf.baursaq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository){
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public List<Recipe> getRecipes(){
        return (List<Recipe>) recipeRepository.findAll();
    }

    public boolean create(Recipe recipe, Long userId){
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            User user = userOptional.orElse(null);
            Recipe recipe1 = recipe;
            recipe1.setLikes(0L);
            recipe1.setAuthor(user.getName() + " " + user.getSurname());
            recipe1.setTimestamp((new Date()));
            recipeRepository.save(recipe1);
            List<Long> recipes_ids = new ArrayList<>();
            recipes_ids.add(userId);
            List<Recipe> recipes = user.getRecipes();
            recipes.add(recipe);
            user.setRecipes(recipes);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Recipe findById(Long id){
        return recipeRepository.findById(id).orElse(null);
    }

    public void updateRecipe(Recipe recipe, Long idd){
        Recipe recipe1 = recipeRepository.findById(idd).orElse(null);
        recipe1.setTimestamp(new Date());
        recipe1.setContent(recipe.getContent());
        recipe1.setInst(recipe.getInst());
        recipeRepository.save(recipe1);
    }

    public void setLike(Long id){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        recipe.setLikes(recipe.getLikes() + 1L);
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long userId, String title){
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);
        Recipe recipe = recipeRepository.findByTitle(title);
        user.getRecipes().remove(recipe);
        recipeRepository.delete(recipe);
    }

}
