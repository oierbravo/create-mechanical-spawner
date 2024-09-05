package com.oierbravo.create_mechanical_spawner.content.components;

import com.google.gson.JsonObject;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipeTypes;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public SpawnerRecipeBuilder withFluid(FluidStack fluidStack) {
        return withFluid(FluidIngredient.fromFluidStack(fluidStack));
    }
    public SpawnerRecipeBuilder withFluid(ForgeFlowingFluid.Flowing pFluid, int pAmount) {
        return withFluid(FluidIngredient.fromFluid(pFluid,pAmount));
    }
    public SpawnerRecipeBuilder withMob(SpawnerRecipeOutput mob) {
        params.mob = mob;
        return this;
    }
    public SpawnerRecipeBuilder withProcessingTime(int processingTime) {
        params.processingTime = processingTime;
        return this;
    }
    public SpawnerRecipe save(){
        return new SpawnerRecipe(params);
    }
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        pFinishedRecipeConsumer.accept(buildFinishedRecipe());
    }

    private FinishedRecipe buildFinishedRecipe() {
        return new FinishedSpawnerRecipe(save());
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
    protected static class FinishedSpawnerRecipe implements FinishedRecipe{
        protected ResourceLocation id;
        protected SpawnerRecipe recipe;

        protected FinishedSpawnerRecipe(SpawnerRecipe pRecipe){
            this.recipe = pRecipe;
            this.id = pRecipe.getId();
        }
        @Override
        public void serializeRecipeData(JsonObject pJson) {
            SpawnerRecipe.Serializer.INSTANCE.toJson(pJson, recipe);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeTypes.EXTRUDING_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
