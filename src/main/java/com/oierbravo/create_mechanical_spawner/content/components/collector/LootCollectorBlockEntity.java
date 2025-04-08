package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class LootCollectorBlockEntity extends SmartBlockEntity {
    private final ItemStackHandler inventory = createInventory();
    private final Lazy<IItemHandler> capability = Lazy.of(() -> inventory);

    public LootCollectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public ItemStackHandler getInventory(){
        return inventory;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

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
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        };
    }
    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("Inventory", inventory.serializeNBT(registries));
        super.write(compound, registries, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        super.read(compound, registries, clientPacket);

    }
  }
