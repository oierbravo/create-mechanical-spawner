package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerTile;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerInstance;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModTiles {
    public static final BlockEntityEntry<SpawnerTile> MECHANICAL_SPAWNER = CreateMechanicalSpawner.registrate()
            .tileEntity("mechanical_spawner", SpawnerTile::new)
            .instance(() -> SpawnerInstance::new)
            .validBlocks(ModBlocks.MECHANICAL_SPAWNER)
            .renderer(() -> SpawnerRenderer::new)
            .register();

    public static void register() {}
}
