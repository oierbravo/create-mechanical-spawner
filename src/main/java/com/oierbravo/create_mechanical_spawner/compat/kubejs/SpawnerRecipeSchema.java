package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.google.gson.JsonObject;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipeOutput;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
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
    RecipeKey<OutputItem[]> CUSTOM_LOOT = ItemComponents.OUTPUT_ARRAY.key("customLoot").optional(new OutputItem[0]);


    public class SpawnerRecipeJS extends RecipeJS {
        @Override
        public OutputItem readOutputItem(Object from) {
            if (from instanceof ProcessingOutput output) {
                return OutputItem.of(output.getStack(), output.getChance());
            } else {
                var outputItem = super.readOutputItem(from);
                if (from instanceof JsonObject j && j.has("chance")) {
                    return outputItem.withChance(j.get("chance").getAsFloat());
                }
                return outputItem;
            }
        }
    }
    RecipeSchema SCHEMA = new RecipeSchema(SpawnerRecipeJS.class, SpawnerRecipeJS::new,  INGREDIENT,MOB, PROCESSING_TIME,CUSTOM_LOOT);

}
