package com.sf.baursaq.services;

import com.sf.baursaq.entity.Comm;
import com.sf.baursaq.entity.Recipe;
import com.sf.baursaq.repository.CommRepository;
import com.sf.baursaq.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommService {

    private final RecipeRepository recipeRepository;
    private final CommRepository commRepository;

    @Autowired
    public CommService(RecipeRepository recipeRepository, CommRepository commRepository){
        this.recipeRepository = recipeRepository;
        this.commRepository = commRepository;
    }
    public boolean create(Comm comm, Long recipeId){
        Comm comm1 = comm;
        comm1.setCommTimestamp((new Date()));
        commRepository.save(comm1);
        List<Long> comm_ids = new ArrayList<>();
        comm_ids.add(recipeId);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.orElse(null);
        List<Comm> comms = recipe.getComments();
        comms.add(comm);
        //note_ids.forEach(id -> noteRepository.findById(id).ifPresent(p -> notes.add(p)));
        recipe.setComments(comms);
        recipeRepository.save(recipe);
        return true;
    }
}
