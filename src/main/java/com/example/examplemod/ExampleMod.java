package com.example.examplemod;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    // Directly reference a slf4j logger
    public static final String MODID = "examplemod";
    public static final String DISPLAY_NAME = "Example MOD";

    public static final NonNullSupplier<CreateRegistrate> registrate = CreateRegistrate.lazy(MODID);

    private static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static CreateRegistrate registrate() {
        return registrate.get();
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
