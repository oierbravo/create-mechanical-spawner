package com.oierbravo.create_mechanical_spawner.content.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerRenderer extends KineticBlockEntityRenderer<SpawnerBlockEntity> {
    public SpawnerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

     @Override
    protected void renderSafe(SpawnerBlockEntity pBlockEntity, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffer, int light, int overlay) {

         if (VisualizationManager.supportsVisualization(pBlockEntity.getLevel()))
             return;

         VertexConsumer vb = buffer.getBuffer(RenderType.solid());
         BlockState blockState = pBlockEntity.getBlockState();

         SuperByteBuffer superBuffer = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, blockState, Direction.DOWN);
         standardKineticRotationTransform(superBuffer, pBlockEntity, light).renderInto(pPoseStack, vb);

     }
}
