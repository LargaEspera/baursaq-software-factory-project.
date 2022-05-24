package com.sf.baursaq.repository;

import com.sf.baursaq.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    @Transactional
    Recipe findByTitle(String title);
}
