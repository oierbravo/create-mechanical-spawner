package com.oierbravo.create_mechanical_spawner.content.components;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

public class HalfShaftVisual<T extends KineticBlockEntity> extends SingleAxisRotatingVisual<T> {
    public HalfShaftVisual(VisualizationContext context, T blockEntity, float partialTick, Direction direction) {
        super(context, blockEntity, partialTick, direction, Models.partial(AllPartialModels.SHAFT_HALF));
    }
    public static <T extends KineticBlockEntity> HalfShaftVisual<T> bottomHalfShaft(VisualizationContext context, T blockEntity, float partialTick) {
        return new HalfShaftVisual<>(context, blockEntity, partialTick, Direction.NORTH);
    }
}