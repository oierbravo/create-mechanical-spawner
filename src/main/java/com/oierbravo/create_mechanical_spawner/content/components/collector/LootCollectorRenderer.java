package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LootCollectorRenderer extends SmartBlockEntityRenderer<LootCollectorBlockEntity> {

    public LootCollectorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

}
