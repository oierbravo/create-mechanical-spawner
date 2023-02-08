package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeTypesEvent;
import net.minecraft.resources.ResourceLocation;

public class KubeJSCreateMechanicalSpawnerPlugin extends KubeJSPlugin {


    //@Override
    public void registerRecipeTypes(RegisterRecipeTypesEvent event) {
        event.register(new ResourceLocation("create_mechanical_spawner:spawner"), SpawnerRecipeJS::new);
    }
}