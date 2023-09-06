package com.oierbravo.create_mechanical_spawner.content.components;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerRenderer extends KineticBlockEntityRenderer {
    public SpawnerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

     @Override
    protected void renderSafe(KineticBlockEntity pBlockEntity, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffer, int light, int overlay) {

         if (Backend.canUseInstancing(pBlockEntity.getLevel()))
             return;
         VertexConsumer vb = buffer.getBuffer(RenderType.solid());
         BlockState blockState = pBlockEntity.getBlockState();

         SuperByteBuffer superBuffer = CachedBufferer.partial(AllPartialModels.SHAFT_HALF, blockState);
         standardKineticRotationTransform(superBuffer, pBlockEntity, light).renderInto(pPoseStack, vb);
    }
}
