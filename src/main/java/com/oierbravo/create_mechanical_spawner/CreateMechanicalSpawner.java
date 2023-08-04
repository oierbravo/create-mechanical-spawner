package com.oierbravo.create_mechanical_spawner;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_mechanical_spawner.foundation.data.ModLangPartials;
import com.oierbravo.create_mechanical_spawner.foundation.utility.LangMerger;
import com.oierbravo.create_mechanical_spawner.registrate.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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


        modEventBus.addListener(EventPriority.LOWEST, CreateMechanicalSpawner::gatherData);


    }
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        if (event.includeClient()) {
            gen.addProvider(true, new LangMerger(output, MODID, DISPLAY_NAME, ModLangPartials.values()));

        }
        if (event.includeServer()) {
        }

    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
