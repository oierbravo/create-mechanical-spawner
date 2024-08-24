package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerInstance;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRenderer;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {
    public static final BlockEntityEntry<SpawnerBlockEntity> MECHANICAL_SPAWNER = CreateMechanicalSpawner.registrate()
            .blockEntity("mechanical_spawner", SpawnerBlockEntity::new)
            .instance(() -> SpawnerInstance::new)
            .validBlocks(ModBlocks.MECHANICAL_SPAWNER)
            .renderer(() -> SpawnerRenderer::new)
            .register();

    public static final BlockEntityEntry<LootCollectorBlockEntity> LOOT_COLLECTOR = CreateMechanicalSpawner.registrate()
            .blockEntity("loot_collector", LootCollectorBlockEntity::new)
            .validBlocks(ModBlocks.LOOT_COLLECTOR)
            .register();

    public static void register() {}
}
