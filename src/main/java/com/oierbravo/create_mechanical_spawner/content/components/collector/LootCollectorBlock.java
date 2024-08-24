package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LootCollectorBlock extends Block implements IBE<LootCollectorBlockEntity>, IWrenchable {

    public LootCollectorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<LootCollectorBlockEntity> getBlockEntityClass() {
        return LootCollectorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LootCollectorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.LOOT_COLLECTOR.get();
    }

}
