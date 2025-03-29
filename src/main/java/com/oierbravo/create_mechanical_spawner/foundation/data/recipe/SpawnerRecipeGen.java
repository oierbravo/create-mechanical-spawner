package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipeBuilder;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipeOutput;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.oierbravo.mechanicals.foundation.data.AbstractMechanicalRecipeGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.oierbravo.create_mechanical_spawner.ModConstants.MODID;

public class SpawnerRecipeGen extends AbstractMechanicalRecipeGenerator<SpawnerRecipeBuilder> {
    public SpawnerRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String namespace, String recipeTypeId, Supplier<SpawnerRecipeBuilder> builderSupplier, String displayName) {
        super(output, registries, namespace, recipeTypeId, builderSupplier,displayName);
    }

    public SpawnerRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this(output, registries,
                MODID,
                SpawnerRecipe.Type.ID,
                SpawnerRecipeBuilder::new,
                "Mechanical Spawner recipes"
        );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        /* Random Spawner */
       create("random")
                .require(ModFluids.RANDOM.get(),100)
                .processingTime(1500)
                .save(recipeOutput);

        /* Hostile Spawner */
        create("blaze")
                .output(SpawnerRecipeOutput.of("minecraft:blaze"))
                .require(ModFluids.BLAZE.get(),100)
                .processingTime(5000)
                .save(recipeOutput);

        create("creeper")
                .output(SpawnerRecipeOutput.of("minecraft:creeper"))
                .require(ModFluids.CREEPER.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("drowned")
                .output(SpawnerRecipeOutput.of("minecraft:drowned"))
                .require(ModFluids.DROWNED.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("enderman")
                .output(SpawnerRecipeOutput.of("minecraft:enderman"))
                .require(ModFluids.ENDERMAN.get(),100)
                .processingTime(5000)
                .save(recipeOutput);

        create("evoker")
                .output(SpawnerRecipeOutput.of("minecraft:evoker"))
                .require(ModFluids.EVOKER.get(),500)
                .processingTime(5000)
                .save(recipeOutput);

        create("ghast")
                .output(SpawnerRecipeOutput.of("minecraft:ghast"))
                .require(ModFluids.GHAST.get(),100)
                .processingTime(5000)
                .save(recipeOutput);

        create("magma_cube")
                .output(SpawnerRecipeOutput.of("minecraft:magma_cube"))
                .require(ModFluids.MAGMA_CUBE.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("pigling")
                .output(SpawnerRecipeOutput.of("minecraft:pigling"))
                .require(ModFluids.PIGLING.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("skeleton")
                .output(SpawnerRecipeOutput.of("minecraft:skeleton"))
                .require(ModFluids.SKELETON.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("slime")
                .output(SpawnerRecipeOutput.of("minecraft:slime"))
                .require(ModFluids.SLIME.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("spider")
                .output(SpawnerRecipeOutput.of("minecraft:spider"))
                .require(ModFluids.SPIDER.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("witch")
                .output(SpawnerRecipeOutput.of("minecraft:witch"))
                .require(ModFluids.WITCH.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("wither_skeleton")
                .output(SpawnerRecipeOutput.of("minecraft:wither_skeleton"))
                .require(ModFluids.WITHER_SKELETON.get(),200)
                .processingTime(5000)
                .save(recipeOutput);

        create("zombie")
                .output(SpawnerRecipeOutput.of("minecraft:zombie"))
                .require(ModFluids.ZOMBIE.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        /* Friendly Spawner */
        create("bat")
                .output(SpawnerRecipeOutput.of("minecraft:bat"))
                .require(ModFluids.BAT.get(),100)
                .processingTime(1000)
                .save(recipeOutput);

        create("bee")
                .output(SpawnerRecipeOutput.of("minecraft:bee"))
                .require(ModFluids.BEE.get(),100)
                .processingTime(2000)
                .save(recipeOutput);

        create("chicken")
                .output(SpawnerRecipeOutput.of("minecraft:chicken"))
                .require(ModFluids.CHICKEN.get(),100)
                .processingTime(1000)
                .save(recipeOutput);

        create("cow")
                .output(SpawnerRecipeOutput.of("minecraft:cow"))
                .require(ModFluids.COW.get(),100)
                .processingTime(2500)
                .save(recipeOutput);

        create("fox")
                .output(SpawnerRecipeOutput.of("minecraft:fox"))
                .require(ModFluids.FOX.get(),100)
                .processingTime(3000)
                .save(recipeOutput);

        create("horse")
                .output(SpawnerRecipeOutput.of("minecraft:horse"))
                .require(ModFluids.HORSE.get(),100)
                .processingTime(2000)
                .save(recipeOutput);

        create("panda")
                .output(SpawnerRecipeOutput.of("minecraft:panda"))
                .require(ModFluids.PANDA.get(),100)
                .processingTime(4000)
                .save(recipeOutput);

        create("parrot")
                .output(SpawnerRecipeOutput.of("minecraft:parrot"))
                .require(ModFluids.PARROT.get(),100)
                .processingTime(1500)
                .save(recipeOutput);

        create("pig")
                .output(SpawnerRecipeOutput.of("minecraft:pig"))
                .require(ModFluids.PIG.get(),100)
                .processingTime(1500)
                .save(recipeOutput);

        create("rabbit")
                .output(SpawnerRecipeOutput.of("minecraft:rabbit"))
                .require(ModFluids.RABBIT.get(),100)
                .processingTime(1000)
                .save(recipeOutput);

        create("villager")
                .output(SpawnerRecipeOutput.of("minecraft:villager"))
                .require(ModFluids.VILLAGER.get(),100)
                .processingTime(5000)
                .save(recipeOutput);

        create("wolf")
                .output(SpawnerRecipeOutput.of("minecraft:wolf"))
                .require(ModFluids.WOLF.get(),100)
                .processingTime(1500)
                .save(recipeOutput);
    }
}
