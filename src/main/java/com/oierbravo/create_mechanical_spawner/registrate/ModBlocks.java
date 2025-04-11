package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.ModStress;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassBlock;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
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



    public static final BlockEntry<CasingBlock> BRASS_CASING = REGISTRATE.block("reinforced_brass_casing", CasingBlock::new)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_BROWN))
            .transform(BuilderTransformers.casing(() -> ModSpriteShifts.REINFORCED_BRASS_CASING))
            .tag(BlockTags.WITHER_IMMUNE)
            .register();

    public static final BlockEntry<ConnectedGlassBlock> FRAMED_GLASS =
            framedDarkGlass("framed_dark_glass", () -> new SimpleCTBehaviour(ModSpriteShifts.FRAMED_DARK_GLASS)),
            HORIZONTAL_FRAMED_GLASS = framedDarkGlass("horizontal_framed_dark_glass",
                    () -> new HorizontalCTBehaviour(ModSpriteShifts.HORIZONTAL_FRAMED_DARK_GLASS, ModSpriteShifts.FRAMED_DARK_GLASS)),
            VERTICAL_FRAMED_GLASS = framedDarkGlass("vertical_framed_dark_glass",
                    () -> new HorizontalCTBehaviour(ModSpriteShifts.VERTICAL_FRAMED_DARK_GLASS));


    @SuppressWarnings("removal")
    public static BlockEntry<ConnectedGlassBlock> framedDarkGlass(String name,
                                                              Supplier<ConnectedTextureBehaviour> behaviour) {
        return REGISTRATE.block(name, ConnectedGlassBlock::new)
                .onRegister(connectedTextures(behaviour))
                .addLayer(() -> RenderType::translucent)
                .initialProperties(() -> Blocks.GLASS)
                .properties(ModBlocks::glassProperties)
                .loot((t, g) -> t.dropWhenSilkTouch(g))
                //.recipe((c, p) -> p.stonecutting(DataIngredient.tag(net.neoforged.neoforge.common.Tags.Items.GLASS_BLOCKS_COLORLESS),
                //        RecipeCategory.BUILDING_BLOCKS, c::get))
                .blockstate((c, p) -> BlockStateGen.cubeAll(c, p, "", "framed_dark_glass"))
                .tag(net.neoforged.neoforge.common.Tags.Blocks.GLASS_BLOCKS_COLORLESS, BlockTags.IMPERMEABLE, BlockTags.WITHER_IMMUNE)
                .item()
                .tag(net.neoforged.neoforge.common.Tags.Items.GLASS_BLOCKS_COLORLESS)
                .model((c, p) -> p.cubeColumn(c.getName(), p.modLoc("block/" + c.getName()),
                        p.modLoc("block/framed_dark_glass")))
                .build()
                .register();
    }
    private static BlockBehaviour.Properties glassProperties(BlockBehaviour.Properties p) {
        return p.isValidSpawn(ModBlocks::never)
                .isRedstoneConductor(ModBlocks::never)
                .isSuffocating(ModBlocks::never)
                .isViewBlocking(ModBlocks::never);
    }

    private static boolean never(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    private static Boolean never(BlockState p_235427_0_, BlockGetter p_235427_1_, BlockPos p_235427_2_,
                                 EntityType<?> p_235427_3_) {
        return false;
    }
}
