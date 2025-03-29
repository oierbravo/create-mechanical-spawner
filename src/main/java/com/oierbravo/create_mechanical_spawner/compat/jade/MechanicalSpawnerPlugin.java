package com.oierbravo.create_mechanical_spawner.compat.jade;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.mechanicals.jade.MechanicalProgressComponentProvider;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class MechanicalSpawnerPlugin implements IWailaPlugin {
    public static final ResourceLocation MECHANICAL_SPAWNER_DATA = ModConstants.asResource("spawner_data");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new MechanicalProgressComponentProvider(MECHANICAL_SPAWNER_DATA), SpawnerBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
      registration.registerBlockComponent(new MechanicalProgressComponentProvider(MECHANICAL_SPAWNER_DATA), SpawnerBlock.class);
    }
}
