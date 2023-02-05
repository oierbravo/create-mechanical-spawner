package com.oierbravo.create_mechanical_spawner.content.components;

import com.simibubi.create.foundation.config.AllConfigs;
import net.minecraftforge.common.ForgeConfigSpec;

public class SpawnerConfig {
    public static ForgeConfigSpec.DoubleValue SPAWNER_STRESS_IMPACT;
    public static ForgeConfigSpec.DoubleValue SPAWNER_MINIMUM_SPEED;
    public static ForgeConfigSpec.IntValue SPAWNER_LIQUID_CAPACITY;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for the mechanical spawner").push("mechanical_spawner");
        SPAWNER_STRESS_IMPACT = COMMON_BUILDER
                .comment("Stress impact")
                .defineInRange("stressImpact", 16.0, 0.0, 64.0);
        SPAWNER_MINIMUM_SPEED = COMMON_BUILDER
                .comment("Minimum required speed")
                .defineInRange("minimumSpeed", 100.0, 0.0, Integer.MAX_VALUE);
        SPAWNER_LIQUID_CAPACITY = COMMON_BUILDER
                .comment("Liquid capacity")
                .defineInRange("liquidCapacity", 1000, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
    }
}
