package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipe;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;

public class KubeJSCreateMechanicalSpawnerPlugin extends KubeJSPlugin {

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.register(SpawnerRecipe.Serializer.ID, SpawnerRecipeSchema.SCHEMA);
    }
}