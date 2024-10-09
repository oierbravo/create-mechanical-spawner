package com.oierbravo.create_mechanical_spawner.compat.jade;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class MechanicalSpawnerPlugin implements IWailaPlugin {
    public static final ResourceLocation MECHANICAL_SPAWNER_DATA = new ResourceLocation("create_mechanical_spawner:spawner_data");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new ProgressComponentProvider(), SpawnerBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
      registration.registerBlockComponent(new ProgressComponentProvider(), SpawnerBlock.class);
    }
}
