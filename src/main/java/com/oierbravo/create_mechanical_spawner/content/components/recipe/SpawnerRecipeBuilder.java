package com.oierbravo.create_mechanical_spawner.content.components.recipe;

import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.Optional;

public class SpawnerRecipeBuilder extends AbstractMechanicalRecipeBuilder<SpawnerRecipe, SpawnerRecipe.SpawnerRecipeParams, SpawnerRecipeBuilder> {


    public SpawnerRecipeBuilder create() {
        params = new SpawnerRecipe.SpawnerRecipeParams();
        return this;
    }
    public SpawnerRecipeBuilder(){
        params = new SpawnerRecipe.SpawnerRecipeParams();
    }

    public SpawnerRecipeBuilder require(SizedFluidIngredient ingredient) {
        params.fluidIngredient = ingredient;
        return this;
    }

    public SpawnerRecipeBuilder require(FluidStack fluidStack) {
        return require(SizedFluidIngredient.of(fluidStack));
    }
    public SpawnerRecipeBuilder require(BaseFlowingFluid.Flowing pFluid, int pAmount) {
        return require(new FluidStack(pFluid.getSource(), pAmount));
    }
    public SpawnerRecipeBuilder output(SpawnerRecipeOutput mob) {
        params.mob = mob;
        return this;
    }
    public SpawnerRecipeBuilder output(Optional<ResourceLocation> rl) {
        return output(SpawnerRecipeOutput.of(rl.get()));
    }
    public SpawnerRecipeBuilder output(ResourceLocation rl) {
        return output(SpawnerRecipeOutput.of(rl));
    }
    public SpawnerRecipeBuilder output(String id) {
        return output(SpawnerRecipeOutput.of(id));
    }
    public SpawnerRecipeBuilder processingTime(int processingTime) {
        params.processingTime = processingTime;
        return this;
    }
    public SpawnerRecipeBuilder withCustomLoot(NonNullList<ProcessingOutput> customLoot){
        params.customLoot.addAll(customLoot);
        return this;
    }
    public SpawnerRecipeBuilder withCustomLoot(ProcessingOutput output) {
        params.customLoot.add(output);
        return this;
    }
    public SpawnerRecipeBuilder withCustomLoot(float chance, ResourceLocation registryName, int amount) {
        return withCustomLoot(new ProcessingOutput(registryName, amount, chance));
    }

    @Override
    public SpawnerRecipe build() {
        return new SpawnerRecipe(this.params);
    }
}
