package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.CraftingRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.CreateMixingRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.SpawnerRecipeGen;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.data.DamageTypeTagGen;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.foundation.data.recipe.StandardRecipeGen;
import com.simibubi.create.infrastructure.data.CreateRecipeSerializerTagsProvider;
import com.simibubi.create.infrastructure.data.GeneratedEntriesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGen {
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        if (event.includeServer()) {
            generator.addProvider(true, new SpawnerRecipeGen(output));
            generator.addProvider(true, new CraftingRecipeGen(output));
            generator.addProvider(true, new CreateMixingRecipeGen(output));
        }
    }
}
