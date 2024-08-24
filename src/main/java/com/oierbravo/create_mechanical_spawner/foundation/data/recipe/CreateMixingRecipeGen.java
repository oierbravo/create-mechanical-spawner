package com.oierbravo.create_mechanical_spawner.foundation.data.recipe;

import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class MixingRecipeGen extends RecipeProvider {
    public MixingRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        MechanicalCraftingRecipeBuilder.shapedRecipe(ModBlocks.MECHANICAL_SPAWNER.get())
                .key('I', Ingredient.of(Blocks.IRON_BARS))
                .key('E', Ingredient.of(Items.EMERALD))
                .key('B', Ingredient.of(AllTags.forgeItemTag("ingots/brass")))
                .key('S', Ingredient.of(AllTags.forgeItemTag("plates/brass")))
                .key('H', Ingredient.of(AllBlocks.SHAFT))
                .patternLine(  "BSSSB")
                .patternLine( "BIIIB")
                .patternLine( "BIEIB")
                .patternLine( "BIIIB")
                .patternLine( "BSHSB")
                .build(pWriter);

        MechanicalCraftingRecipeBuilder.shapedRecipe(ModBlocks.MECHANICAL_SPAWNER.get())
                .key('I', Ingredient.of(Blocks.IRON_BARS))
                .key('C', Ingredient.of(Tags.Items.BARRELS_WOODEN))
                .key('B', Ingredient.of(AllTags.forgeItemTag("ingots/brass")))
                .key('S', Ingredient.of(AllTags.forgeItemTag("plates/brass")))
                .patternLine(  "BSSSB")
                .patternLine( "BIIIB")
                .patternLine( "BICIB")
                .patternLine( "BIIIB")
                .patternLine( "BSSSB")
                .build(pWriter);

    }
    @Override
    public final String getName() {
        return "Mechanical Spawner's crafting recipes.";
    }
}
