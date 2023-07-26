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
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IProgressStyle;
import snownee.jade.util.Color;

public class ProgressComponentProvider  implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    int PROGRESS_COLOR = Color.fromHex("b400ff").toInt();; //purple
    int PROGRESS_TEXT_COLOR = Color.fromHex("ff0000").toInt();;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("mechanical_spawner.progress")) {
            int progress = accessor.getServerData().getInt("mechanical_spawner.progress");

            if(progress > 0){
                IElementHelper helper = tooltip.getElementHelper();
                IProgressStyle progressStyle = helper.progressStyle().color(PROGRESS_COLOR);//.textColor(PROGRESS_TEXT_COLOR);
                tooltip.add(helper.progress((float)progress / 100, Component.translatable("mechanical_spawner.tooltip.progress", progress),progressStyle, helper.borderStyle()));
            }

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
