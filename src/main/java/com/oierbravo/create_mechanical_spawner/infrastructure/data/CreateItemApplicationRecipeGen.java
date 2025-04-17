package com.oierbravo.create_mechanical_spawner.infrastructure.data;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.oierbravo.mechanicals.foundation.data.AbstractCreateRecipeGen;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CreateItemApplicationRecipeGen extends AbstractCreateRecipeGen {

    public CreateItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ModConstants::asResource);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        createItemApplication("reinforced_casing")
                .require(AllBlocks.BRASS_CASING)
                .require(Items.IRON_BLOCK)
                .output(ModBlocks.REINFORCED_BRASS_CASING)
                .build(recipeOutput);
        createItemApplication("framed_glass")
                .require(AllPaletteBlocks.FRAMED_GLASS)
                .require(Items.COAL)
                .output(ModBlocks.FRAMED_DARK_GLASS)
                .build(recipeOutput);

        createItemApplication("horizontal_framed_glass")
                .require(AllPaletteBlocks.HORIZONTAL_FRAMED_GLASS)
                .require(Items.COAL)
                .output(ModBlocks.HORIZONTAL_FRAMED_DARK_GLASS)
                .build(recipeOutput);

        createItemApplication("vertical_framed_glass")
                .require(AllPaletteBlocks.VERTICAL_FRAMED_GLASS)
                .require(Items.COAL)
                .output(ModBlocks.VERTICAL_FRAMED_DARK__GLASS)
                .build(recipeOutput);

    }

    @Override
    public String getName() {
        return "Mechanical Spawner's item application recipes.";
    }
}
