package com.oierbravo.create_mechanical_spawner.compat.kubejs.recipe;

import com.oierbravo.mechanicals.compat.kubejs.components.ProcessingOutputComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.RecipeRequirementsComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.ResourceLocationComponent;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public interface SpawnerRecipeSchema {
    RecipeKey<ResourceLocation> OUTPUT = ResourceLocationComponent.RESOURCE_LOCATION
            .key("output", ComponentRole.OUTPUT)
            .noFunctions();
    RecipeKey<SizedFluidIngredient> INPUT = SizedFluidIngredientComponent.FLAT.key("input", ComponentRole.INPUT).noFunctions();
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime", ComponentRole.OTHER).optional(1000).alwaysWrite();
    RecipeKey<List<ProcessingOutput>> CUSTOM_LOOT = ProcessingOutputComponent.PROCESSING_OUTPUT.instance().asListOrSelf()
            .withBounds(IntBounds.OPTIONAL)
            .key("customLoot", ComponentRole.OUTPUT)
            .optional(List.of());
    RecipeKey<List<IRecipeRequirement>> RECIPE_REQUIREMENTS = RecipeRequirementsComponent.REQUIREMENT.instance().asListOrSelf().withBounds(IntBounds.OPTIONAL).key("requirements", ComponentRole.OTHER).optional(List.of());


    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INPUT, PROCESSING_TIME, CUSTOM_LOOT, RECIPE_REQUIREMENTS).factory(SpawnerKubeRecipe.FACTORY);

}
