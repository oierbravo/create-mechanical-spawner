package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.material.Fluids;
import plus.dragons.createenchantmentindustry.entry.CeiFluids;

import java.util.function.Consumer;

public class CompatRecipeGen extends RecipeProvider {
    public CompatRecipeGen(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        /* Create Enchanment Industry */
        CreateMixingRecipeGen.createSpawnFluid("enchantment_industry_experience", ModFluids.RANDOM.get(),1000)

                .require(CeiFluids.EXPERIENCE.get().getSource(),500)
                .require(Fluids.WATER,500)
                .requiresHeat(HeatCondition.HEATED)
                .whenModLoaded("create_enchantment_industry")
                .build(consumer);

        CreateMixingRecipeGen.createSpawnFluid("enchantment_industry_hyper_experience",ModFluids.RANDOM.get(),1000)

                .require(CeiFluids.HYPER_EXPERIENCE.get().getSource(),10)
                .require(Fluids.WATER,1000)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .whenModLoaded("create_enchantment_industry")
                .build(consumer);


    }
}
