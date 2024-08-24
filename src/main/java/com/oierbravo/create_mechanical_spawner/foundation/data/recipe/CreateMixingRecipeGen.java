package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class CreateMixingRecipeGen extends RecipeProvider {

    public CreateMixingRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        /* Base Spawn Fluid */
        createSpawnFluid("random_legacy",ModFluids.BLAZE.get(),250)
                .require(Fluids.WATER,250)
                .require(Tags.Items.RODS_BLAZE)
                .require(Tags.Items.ENDER_PEARLS)
                .requiresHeat(HeatCondition.HEATED)
                .build(pWriter);

        /* Hostile Spawn Fluid */
        createSpawnFluid("blaze",ModFluids.BLAZE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.RODS_BLAZE)
                .requiresHeat(HeatCondition.HEATED)
                .build(pWriter);

        createSpawnFluid("creeper",ModFluids.CREEPER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.GUNPOWDER)
                .build(pWriter);

        createSpawnFluid("drowned",ModFluids.DROWNED.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.ROTTEN_FLESH)
                .require(Fluids.WATER,100)
                .build(pWriter);

        createSpawnFluid("enderman",ModFluids.ENDERMAN.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.ENDER_PEARLS)
                .requiresHeat(HeatCondition.HEATED)
                .build(pWriter);

        createSpawnFluid("evoker",ModFluids.EVOKER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.GEMS_EMERALD)
                .require(Items.BOOK)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .build(pWriter);

        createSpawnFluid("ghast",ModFluids.GHAST.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.GHAST_TEAR)
                .build(pWriter);

        createSpawnFluid("magma_cube",ModFluids.MAGMA_CUBE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.MAGMA_CREAM)
                .build(pWriter);

        createSpawnFluid("pigling",ModFluids.PIGLING.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.INGOTS_GOLD)
                .build(pWriter);

        createSpawnFluid("skeleton",ModFluids.SKELETON.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.BONES)
                .build(pWriter);

        createSpawnFluid("slime",ModFluids.SLIME.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.SLIMEBALLS)
                .build(pWriter);

        createSpawnFluid("spider",ModFluids.SPIDER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.SPIDER_EYE)
                .build(pWriter);

        createSpawnFluid("witch",ModFluids.WITCH.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.GLASS_BOTTLE)
                .require(Tags.Items.DUSTS_REDSTONE)
                .require(Tags.Items.DUSTS_GLOWSTONE)
                .build(pWriter);

        createSpawnFluid("wither_skeleton",ModFluids.WITHER_SKELETON.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.ORES_COAL)
                .require(Tags.Items.BONES)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .duration(500)
                .build(pWriter);

        createSpawnFluid("zombie",ModFluids.ZOMBIE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.ROTTEN_FLESH)
                .build(pWriter);

        /* Friendly Spawn Fluid */
        createSpawnFluid("bat",ModFluids.BAT.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.FEATHERS)
                .build(pWriter);

        createSpawnFluid("bee",ModFluids.BEE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.HONEYCOMB)
                .build(pWriter);

        createSpawnFluid("cow",ModFluids.COW.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.LEATHER)
                .require(Tags.Items.CROPS_WHEAT)
                .build(pWriter);

        createSpawnFluid("chicken",ModFluids.CHICKEN.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.SEEDS_WHEAT)
                .build(pWriter);

        createSpawnFluid("fox",ModFluids.FOX.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.SWEET_BERRIES)
                .build(pWriter);

        createSpawnFluid("horse",ModFluids.HORSE.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.LEATHER)
                .require(Items.LEAD)
                .require(Items.APPLE)
                .build(pWriter);

        createSpawnFluid("panda",ModFluids.PANDA.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Blocks.SNOW_BLOCK)
                .build(pWriter);

        createSpawnFluid("pig",ModFluids.PIG.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.PORKCHOP)
                .require(Items.CARROT)
                .build(pWriter);

        createSpawnFluid("rabbit",ModFluids.RABBIT.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Items.CARROT)
                .build(pWriter);

        createSpawnFluid("villager",ModFluids.VILLAGER.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.GEMS_EMERALD)
                .build(pWriter);

        createSpawnFluid("wolf",ModFluids.WOLF.get(),250)
                .require(ModFluids.RANDOM.get(),100)
                .require(Tags.Items.BONES)
                .build(pWriter);
    }
    private ProcessingRecipeBuilder<MixingRecipe> createSpawnFluid(String id, VirtualFluid virtualFluid, int pAmount){
        ResourceLocation recipeId = CreateMechanicalSpawner.asResource("spawn_fluid_" + id);

        FluidStack fluidStack = new FluidStack(virtualFluid.getSource(), pAmount);
        return new ProcessingRecipeBuilder<>(MixingRecipe::new,recipeId).duration(250).output(fluidStack);

    }
    @Override
    public final String getName() {
        return "Mechanical Spawner's mixer recipes.";
    }
}
