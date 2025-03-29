package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.CreateMixingRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.SpawnerCompatRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.SpawnerRecipeGen;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.create_mechanical_spawner.ModConstants.MODID;

public class ModDataGen {
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        if (event.includeServer()) {
            generator.addProvider(true, new SpawnerRecipeGen(output, lookupProvider));
            generator.addProvider(true, new CreateMixingRecipeGen(output, lookupProvider));
            generator.addProvider(true, new SpawnerCompatRecipeGen(output, lookupProvider));
        }
        event.getGenerator().addProvider(true, CreateMechanicalSpawner.registrate().setDataProvider(new RegistrateDataProvider(CreateMechanicalSpawner.registrate(), MODID, event)));

    }
}
