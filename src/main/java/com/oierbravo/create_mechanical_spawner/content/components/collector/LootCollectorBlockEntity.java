package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.ModLang;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class LootCollectorBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    private final ItemStackHandler inventory = createInventory();
    private FilteringBehaviour filtering;
    private int lootingLevel;

    public LootCollectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public ItemStackHandler getInventory(){
        return inventory;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        filtering = new FilteringBehaviour(this, new FilterPositioning())
                .forRecipes();
        behaviours.add(filtering);

    }
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.LOOT_COLLECTOR.get(),
                (be, context) -> be.getInventory()

        );
    }
    protected ItemStackHandler createInventory(){
        return new ItemStackHandler(MConfigs.server().spawner.lootCollectorCapacity.get()) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                assert level != null;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return filtering.test(stack);
            }
        };
    }
    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("LootingLevel", lootingLevel);
        super.write(compound, registries, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        lootingLevel = compound.getInt("LootingLevel");
        super.read(compound, registries, clientPacket);

    }
    public static class FilterPositioning extends ValueBoxTransform.Sided {
        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction == Direction.UP;
        }

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8f, 8f,  15f);
        }

    }
    public int getLootingLevel()
    {
        return lootingLevel;
    }

    public void setLootingLevel(int lootingLevel)
    {
        this.lootingLevel = lootingLevel;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if(lootingLevel > 0){
            ModLang.translate("goggles.with_loot_enchantment", lootingLevel).forGoggles(tooltip);
            return true;
        }
        return false;
    }
}
