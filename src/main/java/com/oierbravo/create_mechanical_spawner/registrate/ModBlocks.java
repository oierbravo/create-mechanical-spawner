package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.content.components.ConnectedDarkGlassBlock;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.ModStress;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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
            .blockstate(BlockStateGen.horizontalBlockProvider(false))
            .transform(pickaxeOnly())
            .loot((lt, block) -> {
                LootTable.Builder builder = LootTable.lootTable();
                LootItemCondition.Builder survivesExplosion = ExplosionCondition.survivesExplosion();
                lt.add(block, builder.withPool(LootPool.lootPool()
                        .when(survivesExplosion)
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(DataComponents.ENCHANTMENTS)))));
            })
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



    public static final BlockEntry<CasingBlock> REINFORCED_BRASS_CASING = REGISTRATE.block("reinforced_brass_casing", CasingBlock::new)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_BROWN))
            .transform(BuilderTransformers.casing(() -> ModSpriteShifts.REINFORCED_BRASS_CASING))
            .properties((ctx)-> ctx.explosionResistance(3_600_000.0f))
            .properties(ModBlocks::glassProperties)
            .tag(BlockTags.WITHER_IMMUNE)
            .register();

    public static final BlockEntry<ConnectedDarkGlassBlock> FRAMED_DARK_GLASS =
            framedDarkGlass("framed_dark_glass", () -> new SimpleCTBehaviour(ModSpriteShifts.FRAMED_DARK_GLASS), AllPaletteBlocks.FRAMED_GLASS),
            HORIZONTAL_FRAMED_DARK_GLASS = framedDarkGlass("horizontal_framed_dark_glass",
                    () -> new HorizontalCTBehaviour(ModSpriteShifts.HORIZONTAL_FRAMED_DARK_GLASS, ModSpriteShifts.FRAMED_DARK_GLASS), AllPaletteBlocks.HORIZONTAL_FRAMED_GLASS),
            VERTICAL_FRAMED_DARK__GLASS = framedDarkGlass("vertical_framed_dark_glass",
                    () -> new HorizontalCTBehaviour(ModSpriteShifts.VERTICAL_FRAMED_DARK_GLASS), AllPaletteBlocks.VERTICAL_FRAMED_GLASS);


    @SuppressWarnings("removal")
    public static BlockEntry<ConnectedDarkGlassBlock> framedDarkGlass(String name,
                                                                      Supplier<ConnectedTextureBehaviour> behaviour, ItemLike sourceBlock ) {
        return REGISTRATE.block(name, ConnectedDarkGlassBlock::new)
                .onRegister(connectedTextures(behaviour))
                .addLayer(() -> RenderType::translucent)
                .initialProperties(() -> Blocks.GLASS)
                .properties(ModBlocks::glassProperties)
                .properties((ctx)-> ctx.explosionResistance(3_600_000.0f))

                .loot((t, g) -> t.dropWhenSilkTouch(g))
                .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,c.get())
                            .requires(sourceBlock)
                            .requires(Items.COAL_BLOCK)
                            .unlockedBy("has_framed_glass", RegistrateRecipeProvider.has(AllPaletteBlocks.FRAMED_GLASS))
                        .save(p, ModConstants.asResource("crafting/" + c.getName())))

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
                .noOcclusion()
                .isViewBlocking(ModBlocks::never);
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos,
                                 EntityType<?> entityType) {
        return false;
    }
}
