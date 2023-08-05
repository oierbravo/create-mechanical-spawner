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
    public static final FluidEntry<VirtualFluid> RANDOM = createSpawnFluid( "random", 0xb400ff);

    /* Hostile Mobs */
    public static final FluidEntry<VirtualFluid> BLAZE = createSpawnFluid("blaze", 0xff6c00);
    public static final FluidEntry<VirtualFluid> CREEPER = createSpawnFluid("creeper", 0x11c900);
    public static final FluidEntry<VirtualFluid> ENDERMAN = createSpawnFluid("enderman", 0x00ba88);
    public static final FluidEntry<VirtualFluid> MAGMA_CUBE = createSpawnFluid("magma_cube", 0x7d0000);
    public static final FluidEntry<VirtualFluid> SKELETON = createSpawnFluid("skeleton", 0x555555);
    public static final FluidEntry<VirtualFluid> SLIME = createSpawnFluid("slime", 0x16ff00);
    public static final FluidEntry<VirtualFluid> SPIDER = createSpawnFluid("spider", 0x220000);
    public static final FluidEntry<VirtualFluid> ZOMBIE = createSpawnFluid("zombie", 0x0a7300);

    private static FluidEntry<VirtualFluid> createSpawnFluid(String target, int color){
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
