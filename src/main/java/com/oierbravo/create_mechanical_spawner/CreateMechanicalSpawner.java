package com.oierbravo.create_mechanical_spawner;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlockEntity;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.infrastructure.data.ModDataGen;
import com.oierbravo.create_mechanical_spawner.ponders.ModPonderPlugin;
import com.oierbravo.create_mechanical_spawner.registrate.*;
import com.oierbravo.mechanicals.utility.RegistrateLangBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import static com.oierbravo.create_mechanical_spawner.ModConstants.MODID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MODID)
public class CreateMechanicalSpawner
{
    // Directly reference a slf4j logger


    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID).defaultCreativeTab(ModCreativeTabs.MAIN_TAB.getKey());
    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMechanicalSpawner(IEventBus modEventBus, ModContainer modContainer){
        REGISTRATE.registerEventListeners(modEventBus);

        ModCreativeTabs.register(modEventBus);


        ModBlocks.register();
        ModBlockEntities.register();
        ModFluids.register();


        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        MConfigs.register(modLoadingContext,modContainer);

        ModRecipes.register(modEventBus);


        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::registerCapabilities);

        modEventBus.addListener(ModDataGen::gatherData);

        generateLangEntries();
    }

    @net.neoforged.bus.api.SubscribeEvent
    public void registerCapabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event) {
        SpawnerBlockEntity.registerCapabilities(event);
        LootCollectorBlockEntity.registerCapabilities(event);
    }

    private void generateLangEntries(){
        new RegistrateLangBuilder<>(MODID, registrate())
            .addCreativeTab("Create Mechanical Spawner")
            .addJade("Mechanical spawner data")
            .add("recipe.spawner", "Spawner recipe")
            .add("generic.biome_dependant", "Biome dependant")
            .add("generic.with_custom_loot", "Custom loot with loot collector")
            .add("spawner.tooltip.with_loot_collector", "Loot collector found!")
            .add("spawner.tooltip.progress", "Progress: %d%%")
            .add("spawner.scrollValue.label", "Spawn at height (in blocks)")
            .addBlockTooltipSummary("mechanical_spawner", "Spawns _Mobs_ with spawn liquid.")
            .addPonderHeader("spawner","Spawning living entities")
            .addPonderText(1,"spawner","The Spawner uses rotational force and special fluids to spawn entities")
            .addPonderText(2,"spawner","Its powered from the bottom")
            .addPonderText(3,"spawner","Fluid input can go in from any horizontal side")
            .addPonderText(4,"spawner","Spawn point can be configured")
            .addPonderText(5,"spawner","A loot collector can be placed in the spawn point to automatically collect loot without spawning the entity");

    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new ModPonderPlugin());
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

}
