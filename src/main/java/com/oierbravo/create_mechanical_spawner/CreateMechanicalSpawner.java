package com.oierbravo.create_mechanical_spawner;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipes;
import com.oierbravo.create_mechanical_spawner.registrate.ModTiles;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("create_mechanical_spawner")
public class CreateMechanicalSpawner
{
    // Directly reference a slf4j logger
    public static final String MODID = "create_mechanical_spawner";
    public static final String DISPLAY_NAME = "Create Mechanical Spawner";

    public static final NonNullSupplier<CreateRegistrate> registrate = CreateRegistrate.lazy(MODID);

    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMechanicalSpawner()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.register();
        //ModItems.register();
        ModTiles.register();
        ModRecipes.register(modEventBus);
        ModFluids.register();
    }

    public static CreateRegistrate registrate() {
        return registrate.get();
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
