package com.oierbravo.create_mechanical_spawner.content.components;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

public class SpawnerRecipeBuilder {
    protected SpawnerRecipeBuilder.SpawnerRecipeParams params;
    protected List<ICondition> recipeConditions;
    public SpawnerRecipeBuilder(ResourceLocation recipeId) {
        params = new SpawnerRecipeBuilder.SpawnerRecipeParams(recipeId);
        recipeConditions = new ArrayList<>();
    }
    public SpawnerRecipeBuilder withFluid(FluidIngredient fluidIngredient) {
        params.fluidIngredient = fluidIngredient;
        return this;
    }
    public SpawnerRecipeBuilder withMob(SpawnerRecipeOutput mob) {
        params.mob = mob;
        return this;
    }
    public SpawnerRecipeBuilder withProcessingTime(int processingTime) {
        params.processingTime = processingTime;
        return this;
    }
    public SpawnerRecipe build(){
        return new SpawnerRecipe(params);
    }
    public static class SpawnerRecipeParams {

        protected ResourceLocation id;
        protected FluidIngredient fluidIngredient;
        protected SpawnerRecipeOutput mob;
        protected int processingTime;
        protected SpawnerRecipeParams(ResourceLocation id) {
            this.id = id;
            mob = null;
            fluidIngredient = FluidIngredient.EMPTY;
            processingTime = 200;
        }

    }
}
