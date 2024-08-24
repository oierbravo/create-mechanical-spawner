package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerConfig;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LootCollectorBlockEntity extends SmartBlockEntity {
    private final ItemStackHandler inventory = createInventory();
    private final LazyOptional<IItemHandler> capability = LazyOptional.of(() -> inventory);

    public LootCollectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

   /* public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean notFastEnough = !this.isSpeedRequirementFulfilled() && this.getSpeed() != 0.0F;
        MutableComponent hint;
        List cutString;
        int i;
        if (this.overStressed && (Boolean) AllConfigs.client().enableOverstressedTooltip.get()) {
            Lang.translate("gui.stressometer.overstressed", new Object[0]).style(ChatFormatting.GOLD).forGoggles(tooltip);
            hint = Lang.translateDirect("gui.contraptions.network_overstressed", new Object[0]);
            cutString = TooltipHelper.cutTextComponent(hint, TooltipHelper.Palette.GRAY_AND_WHITE);

            for(i = 0; i < cutString.size(); ++i) {
                Lang.builder().add(((Component)cutString.get(i)).copy()).forGoggles(tooltip);
            }

            return true;
        } else if (!notFastEnough) {
            return false;
        } else {
            Lang.translate("tooltip.speedRequirement", new Object[0]).style(ChatFormatting.GOLD).forGoggles(tooltip);
            hint = Lang.translateDirect("gui.contraptions.not_fast_enough", new Object[]{I18n.get(this.getBlockState().getBlock().getDescriptionId(), new Object[0])});
            cutString = TooltipHelper.cutTextComponent(hint, TooltipHelper.Palette.GRAY_AND_WHITE);

            for(i = 0; i < cutString.size(); ++i) {
                Lang.builder().add(((Component)cutString.get(i)).copy()).forGoggles(tooltip);
            }

            return true;
        }
    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = false;
        if (!IRotate.StressImpact.isEnabled()) {
            return added;
        } else {
            float stressAtBase = this.calculateStressApplied();
            if (Mth.equal(stressAtBase, 0.0F)) {
                return added;
            } else {
                Lang.translate("gui.goggles.kinetic_stats", new Object[0]).forGoggles(tooltip);
                this.addStressImpactStats(tooltip, stressAtBase);
                return true;
            }
        }
    }*/
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    protected ItemStackHandler createInventory(){
        return new ItemStackHandler(SpawnerConfig.LOOT_COLLECTOR_CAPACITY.get()) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        };
    }
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isItemHandlerCap(cap))
            return capability.cast();
        return super.getCapability(cap, side);
    }
    @Override
    public void invalidateCaps() {
        capability.invalidate();
        super.invalidateCaps();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }
  }
