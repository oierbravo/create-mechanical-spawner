package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerConfig;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LootCollectorBlockEntity extends SmartBlockEntity {
    private final ItemStackHandler inventory = createInventory();
    private final LazyOptional<IItemHandler> capability = LazyOptional.of(() -> inventory);
    private int lootingLevel;
    private CompoundTag vanillaTag;

    public LootCollectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        vanillaTag = new CompoundTag();
    }

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
        compound.putInt("LootingLevel", lootingLevel);
        compound.put("VanillaTag", vanillaTag);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        lootingLevel = compound.getInt("LootingLevel");
        vanillaTag = compound.getCompound("VanillaTag");
    }

    public int getLootingLevel()
    {
        return lootingLevel;
    }

    public void setLootingLevel(int lootingLevel)
    {
        this.lootingLevel = lootingLevel;
    }

    public CompoundTag getVanillaTag()
    {
        return vanillaTag;
    }

    public void setTags(CompoundTag vanillaTag) {
        //Based on create backtank code
        this.vanillaTag = vanillaTag.copy();
        // Prevent nesting of the ctrl+pick block added tag
        vanillaTag.remove("BlockEntityTag");
    }
}
