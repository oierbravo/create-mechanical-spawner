package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static com.simibubi.create.Create.REGISTRATE;

public class ModFluids {
    public static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate()
            .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

   // FluidEntry<SpawnFluid> =CreateRegistrate.defaultFluidType()
   static ResourceLocation spawnFlowingRL = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_flow");
   static ResourceLocation spawnStillRL = new ResourceLocation(CreateMechanicalSpawner.MODID,"fluid/spawn_fluid_still");

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SPAWN_FLUID =
           REGISTRATE.fluid("spawn_fluid",spawnStillRL,spawnFlowingRL)
                   .lang("Spawn fluid")

                   .properties(b -> b.viscosity(1500)
                           .density(1400))
                   .fluidProperties(p -> p.levelDecreasePerBlock(2)
                           .tickRate(25)
                           .slopeFindDistance(3)
                           .explosionResistance(100f))
                   //.tag(AllTags.AllFluidTags.HONEY.tag)
                   .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
                   .bucket()
                   //.tag(AllTags.forgeItemTag("buckets/spawm_fluid"))
                   .build()
                   .register();
    public static void register() {}
    private static class NoColorFluidAttributes extends AllFluids.TintedFluidType {

        public NoColorFluidAttributes(Properties properties, ResourceLocation stillTexture,
                                      ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }
}
