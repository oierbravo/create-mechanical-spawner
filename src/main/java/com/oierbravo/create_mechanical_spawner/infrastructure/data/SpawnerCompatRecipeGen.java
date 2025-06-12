package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;

public class SpawnerCompatRecipeGen extends CreateMixingRecipeGen {
    public SpawnerCompatRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        /* Create Enchanment Industry */
        /*createSpawnFluid("enchantment_industry_experience", ModFluids.RANDOM.get(),1000)

                .require(CEIFluids.EXPERIENCE.get().getSource(),500)
                .require(new SimpleDatagenIngredient())
                .require(Fluids.WATER,500)
                .requiresHeat(HeatCondition.HEATED)
                .whenModLoaded("create_enchantment_industry")
                .build(recipeOutput);*/

        /*CreateMixingRecipeGen.createSpawnFluid("enchantment_industry_hyper_experience", ModFluids.RANDOM.get(),1000)

                .require(CEIFluids.HYPER_EXPERIENCE.get().getSource(),10)
                .require(Fluids.WATER,1000)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .whenModLoaded("create_enchantment_industry")
                .build(recipeOutput);*/


    }
    @Override
    public final String getName() {
        return "Mechanical Compat Spawner's mixer recipes.";
    }
}
