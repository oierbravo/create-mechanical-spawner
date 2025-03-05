package com.oierbravo.create_mechanical_spawner.content.components;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

public class SpawnerVisual extends SingleAxisRotatingVisual<SpawnerBlockEntity> {
    public SpawnerVisual(VisualizationContext context, SpawnerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.NORTH, Models.partial(AllPartialModels.SHAFT_HALF));
    }





}
