package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGen {
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        if (event.includeServer()) {
            generator.addProvider(true, new SpawnerRecipeGen(output, lookupProvider));
            generator.addProvider(true, new CreateMixingRecipeGen(output, lookupProvider));
            generator.addProvider(true, new SpawnerCompatRecipeGen(output, lookupProvider));
            generator.addProvider(true, new CreateItemApplicationRecipeGen(output, lookupProvider));
        }

    }
}
