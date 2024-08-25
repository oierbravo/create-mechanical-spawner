package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.CraftingRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.CreateMixingRecipeGen;
import com.oierbravo.create_mechanical_spawner.foundation.data.recipe.SpawnerRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

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
