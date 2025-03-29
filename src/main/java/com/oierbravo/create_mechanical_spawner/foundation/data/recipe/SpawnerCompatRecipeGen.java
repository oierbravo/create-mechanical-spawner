package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.create_mechanical_spawner.ModConstants.MODID;

public class SpawnerCompatRecipeGen extends SpawnerRecipeGen {
    public SpawnerCompatRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                MODID,
                SpawnerRecipe.Type.ID,
                SpawnerRecipeBuilder::new,
                "Spawner Compat recipes"
        );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        /* Create Enchanment Industry */
        /*CreateMixingRecipeGen.createSpawnFluid("enchantment_industry_experience", ModFluids.RANDOM.get(),1000)

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
                .build(consumer);*/


    }
}
