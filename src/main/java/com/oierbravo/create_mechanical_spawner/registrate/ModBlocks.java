package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerConfig;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {


    private static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate();


    public static void register() {
    }
    public static final BlockEntry<SpawnerBlock> MECHANICAL_SPAWNER = REGISTRATE.block("mechanical_spawner", SpawnerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(SpawnerConfig.SPAWNER_STRESS_IMPACT.get()))
            .item()
            .transform(customItemModel())
            .register();
}
