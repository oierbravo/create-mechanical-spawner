package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.ModStress;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.neoforged.neoforge.common.Tags.Items.BARRELS_WOODEN;

public class ModBlocks {


    private static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate();


    public static void register() {
    }

    @SuppressWarnings("removal")
    public static final BlockEntry<SpawnerBlock> MECHANICAL_SPAWNER = REGISTRATE.block("mechanical_spawner", SpawnerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(false))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(ModStress.setImpact(16.0))
            .simpleItem()
            .recipe((blockSpawnerBlockDataGenContext, registrateRecipeProvider) ->
                    MechanicalCraftingRecipeBuilder.shapedRecipe(blockSpawnerBlockDataGenContext.get())
                            .key('I', Ingredient.of(Blocks.IRON_BARS))
                            .key('E', Ingredient.of(Items.EMERALD))
                            .key('B', Ingredient.of(AllTags.commonItemTag("ingots/brass")))
                            .key('S', Ingredient.of(AllTags.commonItemTag("plates/brass")))
                            .key('H', Ingredient.of(AllBlocks.SHAFT))
                            .patternLine(  "BSSSB")
                            .patternLine( "BIIIB")
                            .patternLine( "BIEIB")
                            .patternLine( "BIIIB")
                            .patternLine( "BSHSB")
                            .build(registrateRecipeProvider))
            .register();

    public static final BlockEntry<LootCollectorBlock> LOOT_COLLECTOR = REGISTRATE.block("loot_collector", LootCollectorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .simpleItem()
            .recipe((blockLootCollectorBlockDataGenContext, registrateRecipeProvider) ->
                    MechanicalCraftingRecipeBuilder.shapedRecipe(blockLootCollectorBlockDataGenContext.get())
                            .key('I', Ingredient.of(Blocks.IRON_BARS))
                            .key('C', Ingredient.of(BARRELS_WOODEN))
                            .key('B', Ingredient.of(AllTags.commonItemTag("ingots/brass")))
                            .key('S', Ingredient.of(AllTags.commonItemTag("plates/brass")))
                            .patternLine(  "BSSSB")
                            .patternLine( "BIIIB")
                            .patternLine( "BICIB")
                            .patternLine( "BIIIB")
                            .patternLine( "BSSSB")
                            .build(registrateRecipeProvider))
            .register();
}
