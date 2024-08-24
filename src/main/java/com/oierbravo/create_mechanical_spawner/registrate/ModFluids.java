package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;

public class ModFluids {
    public static String PREFIX = "spawn_fluid";
    public static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate();
    public static final FluidEntry<VirtualFluid> RANDOM = createSpawnFluid( "random");

    /* Hostile Mobs */
    public static final FluidEntry<VirtualFluid> BLAZE = createSpawnFluid("blaze");
    public static final FluidEntry<VirtualFluid> CREEPER = createSpawnFluid("creeper");
    public static final FluidEntry<VirtualFluid> DROWNED = createSpawnFluid("drowned");
    public static final FluidEntry<VirtualFluid> ENDERMAN = createSpawnFluid("enderman");
    public static final FluidEntry<VirtualFluid> EVOKER = createSpawnFluid("evoker");
    public static final FluidEntry<VirtualFluid> GHAST = createSpawnFluid("ghast");
    public static final FluidEntry<VirtualFluid> MAGMA_CUBE = createSpawnFluid("magma_cube");
    public static final FluidEntry<VirtualFluid> PIGLING = createSpawnFluid("pigling");
    public static final FluidEntry<VirtualFluid> SKELETON = createSpawnFluid("skeleton");
    public static final FluidEntry<VirtualFluid> SLIME = createSpawnFluid("slime");
    public static final FluidEntry<VirtualFluid> SPIDER = createSpawnFluid("spider");
    public static final FluidEntry<VirtualFluid> WITCH = createSpawnFluid("witch");
    public static final FluidEntry<VirtualFluid> WITHER_SKELETON = createSpawnFluid("wither_skeleton");
    public static final FluidEntry<VirtualFluid> ZOMBIE = createSpawnFluid("zombie");

    /* Friendly Mobs */
    public static final FluidEntry<VirtualFluid> BAT = createSpawnFluid("bat");
    public static final FluidEntry<VirtualFluid> BEE = createSpawnFluid("bee");
    public static final FluidEntry<VirtualFluid> COW = createSpawnFluid("cow");
    public static final FluidEntry<VirtualFluid> CHICKEN = createSpawnFluid("chicken");
    public static final FluidEntry<VirtualFluid> FOX = createSpawnFluid("fox");
    public static final FluidEntry<VirtualFluid> HORSE = createSpawnFluid("horse");
    public static final FluidEntry<VirtualFluid> PANDA = createSpawnFluid("panda");
    public static final FluidEntry<VirtualFluid> PIG = createSpawnFluid("pig");
    public static final FluidEntry<VirtualFluid> RABBIT = createSpawnFluid("rabbit");
    public static final FluidEntry<VirtualFluid> VILLAGER = createSpawnFluid("villager");
    public static final FluidEntry<VirtualFluid> WOLF = createSpawnFluid("wolf");


    private static FluidEntry<VirtualFluid> createSpawnFluid(String target){
        ResourceLocation flow = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_" + target + "_flow");
        ResourceLocation still = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_" + target + "_still");
        return REGISTRATE.virtualFluid(PREFIX + "_" + target,still, flow)
                .lang("Spawn fluid " + target)

                .tag(AllTags.forgeFluidTag("spawn_fluid"))
                .bucket()
                .build()

                .register();
    }

    public static void register() {}
}
