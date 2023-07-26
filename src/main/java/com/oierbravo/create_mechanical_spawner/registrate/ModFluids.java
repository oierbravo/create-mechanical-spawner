package com.oierbravo.create_mechanical_spawner.registrate;

import com.mojang.math.Vector3f;
import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

import static com.simibubi.create.Create.REGISTRATE;

public class ModFluids {
    public static String PREFIX = "spawn_fluid";
    private static float FOG_DISTANCE = 0.5f;
    public static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);

   static ResourceLocation spawnFlowingRL = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/base_spawn_fluid_flow");
   static ResourceLocation spawnStillRL = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/base_spawn_fluid_still");

    public static final FluidEntry<ForgeFlowingFluid.Flowing> RANDOM = createSpawnFluid( "random", 0xb400ff);

    /* Hostile Mobs */
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BLAZE = createSpawnFluid("blaze", 0xff6c00);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREEPER = createSpawnFluid("creeper", 0x11c900);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> ENDERMAN = createSpawnFluid("enderman", 0x00ba88);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MAGMA_CUBE = createSpawnFluid("magma_cube", 0x7d0000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SKELETON = createSpawnFluid("skeleton", 0x555555);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SLIME = createSpawnFluid("slime", 0x16ff00);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SPIDER = createSpawnFluid("spider", 0x220000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> ZOMBIE = createSpawnFluid("zombie", 0x0a7300);

    /* Friendly Mobs */

    private static FluidEntry<ForgeFlowingFluid.Flowing> createSpawnFluid(String target, int color){

        return REGISTRATE.fluid(PREFIX + "_" + target,spawnStillRL,spawnFlowingRL,ModFluidAttributes.create(
                        color))
                .lang("Spawn fluid " + target)

                .properties(b -> b.viscosity(1500)
                        .density(1400))

                .fluidProperties(p -> p.levelDecreasePerBlock(2)
                        .tickRate(25)
                        .slopeFindDistance(3)
                        .explosionResistance(100f))

                .tag(ModTags.ModFluidTags.SPANW_LIQUID.tag)
                .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
                .bucket()
                .build()
                .register();
    }

    public static void register() {
    }

    private static class ModFluidAttributes extends AllFluids.TintedFluidType {
        private Vector3f fogColor;
        private Float fogDistance;
        private int color;

        public ModFluidAttributes(Properties p, ResourceLocation s, ResourceLocation f) {
            super(p, spawnStillRL, spawnFlowingRL);
        }

        public static FluidBuilder.FluidTypeFactory create(int color) {
            return (p, s, f) -> {
                ModFluidAttributes fluidType = new ModFluidAttributes(p, s, f);
                fluidType.fogColor = new Color(color, false).asVectorF();
                fluidType.fogDistance = FOG_DISTANCE;
                fluidType.color = color;
                return fluidType;
            };
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return color;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return color;
        }
        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance;
        }
    }
}
