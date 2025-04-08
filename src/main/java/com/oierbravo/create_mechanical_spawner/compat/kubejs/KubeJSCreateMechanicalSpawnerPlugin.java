package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.compat.kubejs.recipe.SpawnerKubeRecipe;
import com.oierbravo.create_mechanical_spawner.compat.kubejs.recipe.SpawnerRecipeSchema;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;

public class KubeJSCreateMechanicalSpawnerPlugin implements KubeJSPlugin {

    @Override
    public void registerRecipeFactories(RecipeFactoryRegistry registry) {
        registry.register(SpawnerKubeRecipe.FACTORY);
    }
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(ModConstants.asResource(SpawnerRecipe.Type.ID), SpawnerRecipeSchema.SCHEMA);
    }

}