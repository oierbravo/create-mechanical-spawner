package com.oierbravo.create_mechanical_spawner.compat.jade;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.impl.ui.ProgressArrowElement;

public class ProgressComponentProvider  implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("mechanical_spawner.progress")) {
            int progress = accessor.getServerData().getInt("mechanical_spawner.progress");
            if(progress > 0)
                tooltip.add(Component.translatable("mechanical_spawner.tooltip.progress", progress));
        }

    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        if(blockEntity instanceof SpawnerBlockEntity){
            SpawnerBlockEntity spawner = (SpawnerBlockEntity) blockEntity;
            compoundTag.putInt("mechanical_spawner.progress",spawner.getProgressPercent());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MechanicalSpawnerPlugin.MECHANICAL_SPAWNER_DATA;
    }
}
