package com.oierbravo.create_mechanical_spawner.infrastructure.config;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerConfigs;
import net.createmod.catnip.config.ConfigBase;

public class ModConfigServer extends ConfigBase {
    public final SpawnerConfigs spawner = nested(0, SpawnerConfigs::new, "Mechanical Spawner");

    public final ModStress stressValues = nested(0, ModStress::new, "Stress values");

    @Override
    public String getName() {
        return "server";
    }
}
