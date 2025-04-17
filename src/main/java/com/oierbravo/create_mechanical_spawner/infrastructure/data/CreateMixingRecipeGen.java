package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.oierbravo.mechanicals.foundation.data.AbstractCreateRecipeGen;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.item.Items.*;
import static net.neoforged.neoforge.common.Tags.Items.*;

public class CreateMixingRecipeGen extends AbstractCreateRecipeGen {

    public CreateMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ModConstants::asResource);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        /* Base Spawn Fluid */
        createSpawnFluid("random_legacy",ModFluids.RANDOM.get(),250)
                .require(Fluids.WATER,250)
                .require(RODS_BLAZE)
                .require(ENDER_PEARLS)
                .requiresHeat(HeatCondition.HEATED)
                .build(recipeOutput);

        /* Hostile Spawn Fluid */
        createSpawnFluid("blaze",ModFluids.BLAZE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(RODS_BLAZE)
                .requiresHeat(HeatCondition.HEATED)
                .build(recipeOutput);

        createSpawnFluid("creeper",ModFluids.CREEPER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(GUNPOWDER)
                .build(recipeOutput);

        createSpawnFluid("drowned",ModFluids.DROWNED.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.ROTTEN_FLESH)
                .require(Fluids.WATER,100)
                .build(recipeOutput);

        createSpawnFluid("enderman",ModFluids.ENDERMAN.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(ENDER_PEARLS)
                .requiresHeat(HeatCondition.HEATED)
                .build(recipeOutput);

        createSpawnFluid("evoker",ModFluids.EVOKER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(GEMS_EMERALD)
                .require(Items.BOOK)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .build(recipeOutput);

        createSpawnFluid("ghast",ModFluids.GHAST.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.GHAST_TEAR)
                .build(recipeOutput);

        createSpawnFluid("magma_cube",ModFluids.MAGMA_CUBE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.MAGMA_CREAM)
                .build(recipeOutput);

        createSpawnFluid("pigling",ModFluids.PIGLING.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(INGOTS_GOLD)
                .build(recipeOutput);

        createSpawnFluid("skeleton",ModFluids.SKELETON.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(BONES)
                .build(recipeOutput);

        createSpawnFluid("slime",ModFluids.SLIME.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(SLIMEBALLS)
                .build(recipeOutput);

        createSpawnFluid("spider",ModFluids.SPIDER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(SPIDER_EYE)
                .build(recipeOutput);

        createSpawnFluid("witch",ModFluids.WITCH.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.GLASS_BOTTLE)
                .require(DUSTS_REDSTONE)
                .require(DUSTS_GLOWSTONE)
                .build(recipeOutput);

        createSpawnFluid("wither_skeleton",ModFluids.WITHER_SKELETON.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.COAL)
                .require(BONES)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .duration(500)
                .build(recipeOutput);

        createSpawnFluid("zombie",ModFluids.ZOMBIE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.ROTTEN_FLESH)
                .build(recipeOutput);

        /* Friendly Spawn Fluid */
        createSpawnFluid("bat",ModFluids.BAT.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(FEATHERS)
                .build(recipeOutput);

        createSpawnFluid("bee",ModFluids.BEE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.HONEYCOMB)
                .build(recipeOutput);

        createSpawnFluid("cow",ModFluids.COW.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(LEATHER)
                .require(CROPS_WHEAT)
                .build(recipeOutput);

        createSpawnFluid("chicken",ModFluids.CHICKEN.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(SEEDS_WHEAT)
                .build(recipeOutput);

        createSpawnFluid("fox",ModFluids.FOX.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(SWEET_BERRIES)
                .build(recipeOutput);

        createSpawnFluid("horse",ModFluids.HORSE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(LEATHER)
                .require(Items.LEAD)
                .require(Items.APPLE)
                .build(recipeOutput);

        createSpawnFluid("panda",ModFluids.PANDA.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Blocks.SNOW_BLOCK)
                .build(recipeOutput);

        createSpawnFluid("parrot",ModFluids.PARROT.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.COOKIE)
                .build(recipeOutput);

        createSpawnFluid("pig",ModFluids.PIG.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.PORKCHOP)
                .require(Items.CARROT)
                .build(recipeOutput);

        createSpawnFluid("rabbit",ModFluids.RABBIT.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.CARROT)
                .build(recipeOutput);

        createSpawnFluid("villager",ModFluids.VILLAGER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(GEMS_EMERALD)
                .build(recipeOutput);

        createSpawnFluid("wolf",ModFluids.WOLF.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(BONES)
                .require(Items.PORKCHOP)
                .build(recipeOutput);

        createSpawnFluid("wither",ModFluids.WITHER.get(),100)
                .require(ModFluids.RANDOM.get(),100)
                .require(ModFluids.WITHER_SKELETON.get(),100)
                .require(Items.WITHER_SKELETON_SKULL)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .duration(700)
                .build(recipeOutput);
    }
    protected ProcessingRecipeBuilder<MixingRecipe> createSpawnFluid(String id, BaseFlowingFluid flowingFluid, int pAmount){
        return createMixing("spawn_fluid_" + id)
                .duration(250)
                .output(new FluidStack(flowingFluid.getSource(), pAmount));
    }
    @Override
    public String getName() {
        return "Mechanical Spawner's mixer recipes.";
    }
}
