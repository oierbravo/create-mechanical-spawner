package com.oierbravo.create_mechanical_spawner.compat.kubejs.recipe;


import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;

public class SpawnerKubeRecipe extends KubeRecipe {

    public static final KubeRecipeFactory FACTORY = new KubeRecipeFactory(
            ModConstants.asResource(SpawnerRecipe.Type.ID),
            SpawnerKubeRecipe.class,
            SpawnerKubeRecipe::new
    );
}
