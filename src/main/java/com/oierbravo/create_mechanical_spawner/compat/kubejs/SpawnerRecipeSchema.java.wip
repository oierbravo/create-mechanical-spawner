package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipeOutput;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;

public interface SpawnerRecipeSchema {
    RecipeKey<InputFluid> INGREDIENT = FluidComponents.INPUT.key("fluid");
    RecipeKey<String> MOB = new StringComponent("not a valid living entity!", s -> {
        if(s == "random")
            return true;
        return ResourceLocation.isValidResourceLocation(s);
    }).key("mob").optional(SpawnerRecipeOutput.EMPTY.toString());
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime").optional(500);


    public class SpawnerRecipeJS extends RecipeJS{

    }
    RecipeSchema SCHEMA = new RecipeSchema(SpawnerRecipeJS.class, SpawnerRecipeJS::new, MOB, INGREDIENT, PROCESSING_TIME);

}
