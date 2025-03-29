package com.oierbravo.create_mechanical_spawner.ponders;

import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(ModBlocks.MECHANICAL_SPAWNER)
                .addStoryBoard("spawner_full", SpawnerPonderScenes::spawner);

    }
}
