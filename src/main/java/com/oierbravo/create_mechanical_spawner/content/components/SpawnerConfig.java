package com.oierbravo.create_mechanical_spawner.content.components;

import net.minecraftforge.common.ForgeConfigSpec;

public class SpawnerConfig {
    public static ForgeConfigSpec.DoubleValue SPAWNER_STRESS_IMPACT;
    public static ForgeConfigSpec.DoubleValue SPAWNER_MINIMUM_SPEED;
    public static ForgeConfigSpec.IntValue SPAWNER_LIQUID_CAPACITY;
    public static ForgeConfigSpec.IntValue SPAWNER_MAX_RANGE;
    public static ForgeConfigSpec.BooleanValue ALLOW_ANY_CONTAINER_FOR_LOOT_COLLECTOR;
    public static ForgeConfigSpec.BooleanValue ALLOW_CREATE_ITEM_VAULT_FOR_LOOT_COLLECTOR;
    public static ForgeConfigSpec.BooleanValue LOOT_COLLECTOR_REQUIRED;
    public static ForgeConfigSpec.IntValue LOOT_COLLECTOR_CAPACITY;
    public static ForgeConfigSpec.BooleanValue CUSTOM_LOOT_PER_SPAWN_RECIPE_ENABLED;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for the mechanical spawner").push("mechanical_spawner");
        SPAWNER_STRESS_IMPACT = COMMON_BUILDER
                .comment("Stress impact")
                .defineInRange("stressImpact", 16.0, 0.0, Double.MAX_VALUE);
        SPAWNER_MINIMUM_SPEED = COMMON_BUILDER
                .comment("Minimum required speed")
                .defineInRange("minimumSpeed", 100.0, 0.0, Double.MAX_VALUE);
        SPAWNER_LIQUID_CAPACITY = COMMON_BUILDER
                .comment("Liquid capacity")
                .defineInRange("liquidCapacity", 1000, 1, Integer.MAX_VALUE);
        SPAWNER_MAX_RANGE = COMMON_BUILDER
                .comment("Max range")
                .defineInRange("maxRange", 2, 1, 6);
        ALLOW_ANY_CONTAINER_FOR_LOOT_COLLECTOR = COMMON_BUILDER
                .comment("Allow any container as loot collector")
                .define("allowAnyContainerLootCollector", false);
        ALLOW_CREATE_ITEM_VAULT_FOR_LOOT_COLLECTOR = COMMON_BUILDER
                .comment("Allow Create Item Vault as loot collector")
                .define("allowAnyContainerLootCollector", false);
        LOOT_COLLECTOR_REQUIRED = COMMON_BUILDER
                .comment("Forces to require a loot collector for the spawner to work.")
                .define("lootCollectorRequired", false);
        LOOT_COLLECTOR_CAPACITY = COMMON_BUILDER
                .comment("Loot collector capacity in stacks")
                .defineInRange("lootCollectorCapacity", 8, 1, 16);
        CUSTOM_LOOT_PER_SPAWN_RECIPE_ENABLED = COMMON_BUILDER
                .comment("Enables alternative loot for spawner recipes.")
                .define("customLootPerSpawnRecipeEnabled", true);

        COMMON_BUILDER.pop();
    }
}
