package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

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
            //.transform(BlockStressDefaults.setImpact(SpawnerConfig.SPAWNER_STRESS_IMPACT.get()))
            .simpleItem()
            .register();

    public static final BlockEntry<LootCollectorBlock> LOOT_COLLECTOR = REGISTRATE.block("loot_collector", LootCollectorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .item(LootCollectorItem::new)
            .build()
            .loot((lt, block) ->
            {
                //Based on create backtank code
                LootTable.Builder         builder           = LootTable.lootTable();
                LootItemCondition.Builder survivesExplosion = ExplosionCondition.survivesExplosion();

                lt.add(block, builder.withPool(LootPool.lootPool()
                                .when(survivesExplosion)
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(block.asItem())
                                        .apply(
                                                CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                        .copy("VanillaTag", "{}", CopyNbtFunction.MergeStrategy.MERGE)
                                        )
                                )
                        )
                );
            })
            .register();


}
