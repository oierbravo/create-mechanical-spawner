package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipeBuilder;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipeOutput;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class SpawnerRecipeGen extends RecipeProvider {
    public SpawnerRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        /* Random Spawner */
        create("random")
                .withFluid(ModFluids.SKELETON.get(),100)
                .withProcessingTime(1500)
                .save(pWriter);

        /* Hostile Spawner */
        create("blaze")
                .withMob(SpawnerRecipeOutput.of("minecraft:blaze"))
                .withFluid(ModFluids.BLAZE.get(),100)
                .withProcessingTime(5000)
                .save(pWriter);

        create("creeper")
                .withMob(SpawnerRecipeOutput.of("minecraft:creeper"))
                .withFluid(ModFluids.CREEPER.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("drowned")
                .withMob(SpawnerRecipeOutput.of("minecraft:drowned"))
                .withFluid(ModFluids.DROWNED.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("enderman")
                .withMob(SpawnerRecipeOutput.of("minecraft:enderman"))
                .withFluid(ModFluids.ENDERMAN.get(),100)
                .withProcessingTime(5000)
                .save(pWriter);

        create("evoker")
                .withMob(SpawnerRecipeOutput.of("minecraft:evoker"))
                .withFluid(ModFluids.EVOKER.get(),500)
                .withProcessingTime(5000)
                .save(pWriter);

        create("ghast")
                .withMob(SpawnerRecipeOutput.of("minecraft:ghast"))
                .withFluid(ModFluids.ENDERMAN.get(),100)
                .withProcessingTime(5000)
                .save(pWriter);

        create("magma_cube")
                .withMob(SpawnerRecipeOutput.of("minecraft:magma_cube"))
                .withFluid(ModFluids.MAGMA_CUBE.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("piglin")
                .withMob(SpawnerRecipeOutput.of("minecraft:piglin"))
                .withFluid(ModFluids.PIGLING.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("skeleton")
                .withMob(SpawnerRecipeOutput.of("minecraft:skeleton"))
                .withFluid(ModFluids.SKELETON.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("slime")
                .withMob(SpawnerRecipeOutput.of("minecraft:slime"))
                .withFluid(ModFluids.SLIME.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("spider")
                .withMob(SpawnerRecipeOutput.of("minecraft:spider"))
                .withFluid(ModFluids.SPIDER.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("witch")
                .withMob(SpawnerRecipeOutput.of("minecraft:witch"))
                .withFluid(ModFluids.WITCH.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);

        create("wither_skeleton")
                .withMob(SpawnerRecipeOutput.of("minecraft:wither_skeleton"))
                .withFluid(ModFluids.WITHER_SKELETON.get(),200)
                .withProcessingTime(5000)
                .save(pWriter);

        create("zombie")
                .withMob(SpawnerRecipeOutput.of("minecraft:zombie"))
                .withFluid(ModFluids.ZOMBIE.get(),100)
                .withProcessingTime(2500)
                .save(pWriter);
    }

    private SpawnerRecipeBuilder create(String id){
        return new SpawnerRecipeBuilder(CreateMechanicalSpawner.asResource("spawner/" + id));
    }

    @Override
    public final String getName() {
        return "Mechanical Spawner's spawner recipes.";
    }

}
