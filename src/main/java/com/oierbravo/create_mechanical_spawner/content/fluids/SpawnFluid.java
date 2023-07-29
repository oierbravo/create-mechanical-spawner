package com.oierbravo.create_mechanical_spawner.content.fluids;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;

public class SpawnFluid extends VirtualFluid {
    public SpawnFluid(Properties properties) {
        super(properties);
    }

    @Override
    public Item getBucket() {
        return super.getBucket();

    }
    public static class SpawnFluidType extends AllFluids.TintedFluidType {


        public SpawnFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        public int getTintColor(FluidStack stack) {
            CompoundTag tag = stack.getOrCreateTag();
            int color = PotionUtils.getColor(PotionUtils.getAllEffects(tag)) | 0xff000000;
            return color;
        }

        @Override
        public String getDescriptionId(FluidStack stack) {
            CompoundTag tag = stack.getOrCreateTag();
            ItemLike itemFromBottleType =
                    PotionFluidHandler.itemFromBottleType(NBTHelper.readEnum(tag, "Bottle", PotionFluid.BottleType.class));
            return PotionUtils.getPotion(tag)
                    .getName(itemFromBottleType.asItem()
                            .getDescriptionId() + ".effect.");
        }

        @Override
        protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
            return NO_TINT;
        }

    }
}
