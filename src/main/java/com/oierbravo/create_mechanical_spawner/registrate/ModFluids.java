package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.mechanicals.register.fluid.MechanicalSolidRenderedPlaceableFluidType;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class ModFluids {
    public static String PREFIX = "spawn_fluid";
    public static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate();
    public static final FluidEntry<BaseFlowingFluid.Flowing> RANDOM = createSpawnFluid( "random",0xb400ff);

    /* Hostile Mobs */
    public static final FluidEntry<BaseFlowingFluid.Flowing> BLAZE = createSpawnFluid("blaze",0xff6c00);
    public static final FluidEntry<BaseFlowingFluid.Flowing> CREEPER = createSpawnFluid("creeper",0x11c900);
    public static final FluidEntry<BaseFlowingFluid.Flowing> DROWNED = createSpawnFluid("drowned",0x00ffd7);
    public static final FluidEntry<BaseFlowingFluid.Flowing> ENDERMAN = createSpawnFluid("enderman",0x006d50);
    public static final FluidEntry<BaseFlowingFluid.Flowing> EVOKER = createSpawnFluid("evoker",0x868686);
    public static final FluidEntry<BaseFlowingFluid.Flowing> GHAST = createSpawnFluid("ghast",0xdadada);
    public static final FluidEntry<BaseFlowingFluid.Flowing> MAGMA_CUBE = createSpawnFluid("magma_cube",0x7d0000);
    public static final FluidEntry<BaseFlowingFluid.Flowing> PIGLING = createSpawnFluid("pigling",0xffa8e3);
    public static final FluidEntry<BaseFlowingFluid.Flowing> SKELETON = createSpawnFluid("skeleton",0x555555);
    public static final FluidEntry<BaseFlowingFluid.Flowing> SLIME = createSpawnFluid("slime",0x16ff00);
    public static final FluidEntry<BaseFlowingFluid.Flowing> SPIDER = createSpawnFluid("spider",0x220000);
    public static final FluidEntry<BaseFlowingFluid.Flowing> WITCH = createSpawnFluid("witch",0x095000);
    public static final FluidEntry<BaseFlowingFluid.Flowing> WITHER_SKELETON = createSpawnFluid("wither_skeleton",0x393939);
    public static final FluidEntry<BaseFlowingFluid.Flowing> ZOMBIE = createSpawnFluid("zombie",0x0a7300);

    /* Friendly Mobs */
    public static final FluidEntry<BaseFlowingFluid.Flowing> BAT = createSpawnFluid("bat",0xff9acd);
    public static final FluidEntry<BaseFlowingFluid.Flowing> BEE = createSpawnFluid("bee",0xffe600);
    public static final FluidEntry<BaseFlowingFluid.Flowing> COW = createSpawnFluid("cow",0x382417);
    public static final FluidEntry<BaseFlowingFluid.Flowing> CHICKEN = createSpawnFluid("chicken",0xf7f7f7);
    public static final FluidEntry<BaseFlowingFluid.Flowing> FOX = createSpawnFluid("fox",0xff9700);
    public static final FluidEntry<BaseFlowingFluid.Flowing> HORSE = createSpawnFluid("horse",0x804c00);
    public static final FluidEntry<BaseFlowingFluid.Flowing> PANDA = createSpawnFluid("panda",0xe5e5e5);
    public static final FluidEntry<BaseFlowingFluid.Flowing> PARROT = createSpawnFluid("parrot",0xff0000);
    public static final FluidEntry<BaseFlowingFluid.Flowing> PIG = createSpawnFluid("pig",0xff9acd);
    public static final FluidEntry<BaseFlowingFluid.Flowing> RABBIT = createSpawnFluid("rabbit",0xff9acd);
    public static final FluidEntry<BaseFlowingFluid.Flowing> VILLAGER = createSpawnFluid("villager",0x503600);
    public static final FluidEntry<BaseFlowingFluid.Flowing> WOLF = createSpawnFluid("wolf",0xff9acd);


    private static FluidEntry<BaseFlowingFluid.Flowing> createSpawnFluid(String target, int fogColor){

        return REGISTRATE.standardFluid(PREFIX + "_" + target, MechanicalSolidRenderedPlaceableFluidType.create(fogColor,
                () -> 1f / 8f * AllConfigs.client().honeyTransparencyMultiplier.getF()))
                .lang("Spawn fluid " + target)
                .properties(b -> b.viscosity(2000)
                        .density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2)
                        .tickRate(25)
                        .slopeFindDistance(3)
                        .explosionResistance(100f))
                .tag(AllTags.commonFluidTag("spawn_fluid"))
                .source(BaseFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
                .bucket()
                .tag(AllTags.commonItemTag("buckets/spawn_fluid"))
                .build()
                .register();
    }

    public static void register() {}

}
