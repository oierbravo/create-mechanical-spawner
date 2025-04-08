package com.oierbravo.create_mechanical_spawner.compat.kubejs.recipe;

import com.oierbravo.mechanicals.compat.kubejs.components.CreateFluidIngredientComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.ProcessingOutputComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.RecipeRequirementsComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.ResourceLocationComponent;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface SpawnerRecipeSchema {
    //RecipeKey<List<ProcessingOutput>> OUTPUTS = ProcessingOutputComponent.UNWRAPPED_INGREDIENT_LIST
    RecipeKey<ResourceLocation> OUTPUT = ResourceLocationComponent.RESOURCE_LOCATION
            .key("output", ComponentRole.OUTPUT)
            .noFunctions();
    RecipeKey<FluidIngredient> INPUT = CreateFluidIngredientComponent.FLUID_INGREDIENT.key("input", ComponentRole.INPUT).noFunctions();
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime", ComponentRole.OTHER).optional(1000).alwaysWrite();
    RecipeKey<List<ProcessingOutput>> CUSTOM_LOOT = ProcessingOutputComponent.OUTPUT.asList()
            .key("customLoot", ComponentRole.OUTPUT)
            .optional(List.of()).allowEmpty();
    RecipeKey<List<IRecipeRequirement>> RECIPE_REQUIREMENTS = RecipeRequirementsComponent.RECIPE_REQUIREMENT.asList().key("requirements", ComponentRole.OTHER).optional(List.of()).allowEmpty();


    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INPUT, PROCESSING_TIME, CUSTOM_LOOT, RECIPE_REQUIREMENTS).factory(SpawnerKubeRecipe.FACTORY);

}
