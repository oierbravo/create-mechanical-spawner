package com.oierbravo.create_mechanical_spawner.registrate;

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
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluids {
    public static String PREFIX = "spawn_fluid";
    public static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> RANDOM = createSpawnFluid( "random",0xb400ff);

    /* Hostile Mobs */
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BLAZE = createSpawnFluid("blaze",0xff6c00);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREEPER = createSpawnFluid("creeper",0x11c900);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> DROWNED = createSpawnFluid("drowned",0x00ffd7);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> ENDERMAN = createSpawnFluid("enderman",0x006d50);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> EVOKER = createSpawnFluid("evoker",0x868686);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> GHAST = createSpawnFluid("ghast",0xdadada);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MAGMA_CUBE = createSpawnFluid("magma_cube",0x7d0000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> PIGLING = createSpawnFluid("pigling",0xffa8e3);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SKELETON = createSpawnFluid("skeleton",0x555555);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SLIME = createSpawnFluid("slime",0x16ff00);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SPIDER = createSpawnFluid("spider",0x220000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> WITCH = createSpawnFluid("witch",0x095000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> WITHER_SKELETON = createSpawnFluid("wither_skeleton",0x393939);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> ZOMBIE = createSpawnFluid("zombie",0x0a7300);

    /* Friendly Mobs */
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BAT = createSpawnFluid("bat",0xff9acd);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BEE = createSpawnFluid("bee",0xffe600);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> COW = createSpawnFluid("cow",0x382417);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHICKEN = createSpawnFluid("chicken",0xf7f7f7);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> FOX = createSpawnFluid("fox",0xff9700);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> HORSE = createSpawnFluid("horse",0x804c00);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> PANDA = createSpawnFluid("panda",0xe5e5e5);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> PARROT = createSpawnFluid("parrot",0xff0000);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> PIG = createSpawnFluid("pig",0xff9acd);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> RABBIT = createSpawnFluid("rabbit",0xff9acd);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> VILLAGER = createSpawnFluid("villager",0x503600);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> WOLF = createSpawnFluid("wolf",0xff9acd);


    private static FluidEntry<ForgeFlowingFluid.Flowing> createSpawnFluid(String target, int fogColor){
        ResourceLocation flow = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_" + target + "_flow");
        ResourceLocation still = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_" + target + "_still");

        //return REGISTRATE.standardFluid(PREFIX + "_" + target,still, flow)
        return REGISTRATE.standardFluid(PREFIX + "_" + target, SolidRenderedPlaceableFluidType.create(fogColor,
                () -> 1f / 8f * AllConfigs.client().honeyTransparencyMultiplier.getF()))
                .lang("Spawn fluid " + target)
                .properties(b -> b.viscosity(2000)
                        .density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2)
                        .tickRate(25)
                        .slopeFindDistance(3)
                        .explosionResistance(100f))
                .tag(AllTags.forgeFluidTag("spawn_fluid"))
                .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
                .bucket()
                .tag(AllTags.forgeItemTag("buckets/spawn_fluid"))
                .build()
                .register();
    }

    public static void register() {}

    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(FluidType.Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
