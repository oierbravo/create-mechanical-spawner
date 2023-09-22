package com.oierbravo.create_mechanical_spawner;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_mechanical_spawner.registrate.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateMechanicalSpawner.MODID)
public class CreateMechanicalSpawner
{
    // Directly reference a slf4j logger
    public static final String MODID = "create_mechanical_spawner";
    public static final String DISPLAY_NAME = "Create Mechanical Spawner";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMechanicalSpawner()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModConfigs.register();


        ModBlocks.register();
        ModBlockEntities.register();
        ModRecipes.register(modEventBus);
        ModFluids.register();
        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::doClientStuff);


        generateLangEntries();
    }
    private void generateLangEntries(){
        registrate().addRawLang("itemGroup.create_mechanical_spawner:main", "Create Mechanical Spawner");
        registrate().addRawLang("config.jade.plugin_create_mechanical_spawner.spawner_data", "Mechanical spawner data");

        registrate().addRawLang("create_mechanical_spawner.recipe.spawner", "Spawner recipe");
        registrate().addRawLang("create_mechanical_spawner.generic.biome_dependant", "Biome dependant");
        registrate().addRawLang("create_mechanical_spawner.spawner.tooltip.progress", "Progress: %d%%");
        registrate().addRawLang("create_mechanical_spawner.spawner.scrollValue.label", "Spawn at height (in blocks)");
        registrate().addRawLang("block.create_mechanical_spawner.mechanical_spawner.tooltip", "MECHANICAL SPAWNER");
        registrate().addRawLang("block.create_mechanical_spawner.mechanical_spawner.tooltip.summary", "Spawns _Mobs_ with spawn liquid.");

    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(ModPonders::register);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
