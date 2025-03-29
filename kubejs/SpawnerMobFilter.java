package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipe;
import dev.latvian.mods.kubejs.core.RecipeKJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;

public record SpawnerMobFilter(String mob) implements RecipeFilter {

    @Override
    public boolean test(RecipeKJS recipe) {
        return recipe instanceof RecipeJS recipeJs && recipeJs.getOriginalRecipe() instanceof SpawnerRecipe spawnerRecipe && mob.contains(spawnerRecipe.getOutput().serialize());
    }

    public String toString() {
        return "SpawnerMob{mob='" + this.mob + "'}";
    }

}
