package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlock;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerConfig;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MaterialColor;

import static com.simibubi.create.AllTags.pickaxeOnly;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {


    private static final CreateRegistrate REGISTRATE = CreateMechanicalSpawner.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);


    public static void register() {

        Create.REGISTRATE.addToSection(MECHANICAL_SPAWNER, AllSections.KINETICS);
    }
    public static final BlockEntry<SpawnerBlock> MECHANICAL_SPAWNER = REGISTRATE.block("mechanical_spawner", SpawnerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(SpawnerConfig.SPAWNER_STRESS_IMPACT.get()))
            .item()
            .transform(customItemModel())
            .register();
}
