package com.oierbravo.create_mechanical_spawner.content.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SpawnerRenderer extends KineticBlockEntityRenderer {
    public SpawnerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

     @Override
    protected void renderSafe(KineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        super.renderSafe(be,partialTicks,ms,buffer,light,overlay);

    }
}
