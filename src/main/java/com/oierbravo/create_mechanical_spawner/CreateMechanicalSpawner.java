package com.oierbravo.create_mechanical_spawner;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_mechanical_spawner.registrate.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMechanicalSpawner()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModConfigs.register();
        new ModGroup("main");
        ModBlocks.register();
        ModBlockEntities.register();
        ModRecipes.register(modEventBus);
        ModFluids.register();
        generateLangEntries();

    }
    private void generateLangEntries(){
        registrate().addRawLang("create.recipe.spawner", "Spawner recipe");
        //registrate().addRawLang("create_mechanical_spawner.recipe.spawner", "Spawner recipe");
        registrate().addRawLang("itemGroup.create_mechanical_spawner:main", DISPLAY_NAME);
    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
