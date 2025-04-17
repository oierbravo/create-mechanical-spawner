package com.oierbravo.create_mechanical_spawner.content.components;

import com.simibubi.create.content.decoration.palettes.ConnectedGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ConnectedDarkGlassBlock extends ConnectedGlassBlock {
    public ConnectedDarkGlassBlock(Properties p_i48392_1_) {
        super(p_i48392_1_);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_309084_, BlockGetter p_309133_, BlockPos p_309097_) {
        return false;
    }
}
