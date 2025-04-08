package com.oierbravo.create_mechanical_spawner.content.components;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class SpawnerConfigs extends ConfigBase {

    public final ConfigFloat minimumSpeed = f(100,1,"minimumSpeed", Comments.minimumSpeed);
    public final ConfigInt fluidCapacity = i(1000,1,"liquidCapacity", Comments.liquidCapacity);
    public final ConfigInt maxRange = i(2,1,16,"maxRange", Comments.maxRange);
    public final ConfigBool allowAnyContainerForLootCollector = b(false,"allowAnyContainerForLootCollector", Comments.allowAnyContainerForLootCollector);
    public final ConfigBool allowCreateItemVaultForLootCollector = b(true,"allowCreateItemVaultForLootCollector", Comments.allowCreateItemVaultForLootCollector);
    public final ConfigBool lootCollectorRequired = b(false,"lootCollectorRequired", Comments.lootCollectorRequired);
    public final ConfigInt lootCollectorCapacity = i(8,1,"lootCollectorCapacity", Comments.lootCollectorCapacity);
    public final ConfigBool customLootPerSpawnRecipeEnabled = b(true,"customLootPerSpawnRecipeEnabled", Comments.customLootPerSpawnRecipeEnabled);


    private static class Comments {
        static String minimumSpeed = "Minimum required speed.";
        static String liquidCapacity = "Input liquid capacity.";
        static String maxRange = "Max range";
        static String allowAnyContainerForLootCollector = "Allow any container as loot collector";
        static String allowCreateItemVaultForLootCollector = "Allow Create Item Vault as loot collector";
        static String lootCollectorRequired = "Forces to require a loot collector for the spawner to work.";
        static String lootCollectorCapacity = "Loot collector capacity in stacks";
        static String customLootPerSpawnRecipeEnabled = "Enables alternative loot for spawner recipes.";
    }

    @Override
    public @NotNull String getName() {
        return "Mechanical Spawner";
    }

}
