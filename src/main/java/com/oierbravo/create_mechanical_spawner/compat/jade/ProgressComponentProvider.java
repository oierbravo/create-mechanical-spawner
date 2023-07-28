package com.oierbravo.create_mechanical_spawner.compat.jade;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.foundation.utility.ModLang;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;

public class ProgressComponentProvider  implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("mechanical_spawner.progress")) {
            int progress = accessor.getServerData().getInt("mechanical_spawner.progress");

            if(progress > 0){
                IElementHelper helper = tooltip.getElementHelper();
                tooltip.add(helper.progress((float)progress / 100, ModLang.translate("spawner.tooltip.progress", progress).component(),helper.progressStyle(), BoxStyle.DEFAULT,true));
            }

        }

    }

    @Override
    public ResourceLocation getUid() {
        return MechanicalSpawnerPlugin.MECHANICAL_SPAWNER_DATA;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if(blockAccessor.getBlockEntity() instanceof SpawnerBlockEntity spawner){
            compoundTag.putInt("mechanical_spawner.progress",spawner.getProgressPercent());
        }
    }
}
