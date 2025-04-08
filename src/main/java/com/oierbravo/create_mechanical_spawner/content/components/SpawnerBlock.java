package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpawnerBlock extends HorizontalKineticBlock implements IBE<SpawnerBlockEntity>, IWrenchable {
    public static final VoxelShape SHAPE =new AllShapes.Builder(
                Block.box(0, 0, 0, 16, 1, 16)
            )
            .add(
                    new AllShapes.Builder(
                            Block.box(1, 2, 1, 15, 15, 15)
                    ).build()
            )
            .add(
                    new AllShapes.Builder(
                            Block.box(0, 16, 0, 16, 16, 16)
                    ).build()
            )
            .build();

    public SpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }
    @Override
    public Class<SpawnerBlockEntity> getBlockEntityClass() {
        return SpawnerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SpawnerBlockEntity> getBlockEntityType() {
        return ModBlockEntities.MECHANICAL_SPAWNER.get();
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction prefferedSide = getPreferredHorizontalFacing(context);
        if (prefferedSide == null)
            prefferedSide = context.getHorizontalDirection();
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown() ? prefferedSide : prefferedSide.getOpposite());
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.of(MConfigs.server().spawner.minimumSpeed.getF());
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            withBlockEntityDo(worldIn, pos, SpawnerBlockEntity::setRemoved);

            worldIn.removeBlockEntity(pos);
        }
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof ServerPlayer)
            withBlockEntityDo(worldIn, pos, dbe -> dbe.owner = placer.getUUID());
    }


}
