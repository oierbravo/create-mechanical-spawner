package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateMechanicalSpawner.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_mechanical_spawner:main"))
                    .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getId())
                    .icon(ModBlocks.MECHANICAL_SPAWNER::asStack)
                    .displayItems((pParameters, pOutput) -> {
                        for (RegistryEntry<Block> entry : CreateMechanicalSpawner.REGISTRATE.getAll(Registries.BLOCK)) {
                            pOutput.accept(entry.get());
                        }
                        for (RegistryEntry<Fluid> entry : CreateMechanicalSpawner.REGISTRATE.getAll(Registries.FLUID)) {
                            Fluid fluid = entry.get();
                            Item bucket = fluid.getBucket();
                            //pOutput.accept(bucket);
                        }
                        for (RegistryEntry<Item> entry : CreateMechanicalSpawner.REGISTRATE.getAll(Registries.ITEM)) {
                            //Item bucket = fluid.getBucket();
                            pOutput.accept(entry.get());
                        }
                    })
                    .build());

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.get();
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
