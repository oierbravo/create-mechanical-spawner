package com.oierbravo.create_mechanical_spawner.compat.jei.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.minecraft.client.gui.GuiGraphics;

public class AnimatedSpawner  extends AnimatedKinetics {
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(guiGraphics, -16, 13);
        poseStack.translate(-2, 18, 0);
        int scale = 22;

        blockElement(ModBlocks.MECHANICAL_SPAWNER.getDefaultState())
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(guiGraphics);

        poseStack.popPose();
    }

}
